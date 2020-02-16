import {
  Component,
  OnInit,
  NgModule,
  ViewChild,
  ChangeDetectorRef
} from "@angular/core";
import { MMaterialModule } from "../../home/material-module";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { SharedModule } from "../../shared/shared.module";
import { SubMaterialElement } from "../material";
import { SubMaterialAddComponent } from "../sub-material-add/sub-material-add.component";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MaterialService } from "../material.service";
import { MatTable, MatSort, MatSnackBar } from "@angular/material";
import { Router } from "@angular/router";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";

@Component({
  selector: "app-second-material",
  templateUrl: "./second-material.component.html",
  styleUrls: ["./second-material.component.css"]
})
@NgModule({
  declarations: [SubMaterialAddComponent],
  imports: [MMaterialModule, SharedModule],
  bootstrap: [],
  exports: [MMaterialModule, SharedModule]
})
export class SecondMaterialComponent implements OnInit {
  listSubMaterial: SubMaterialElement[] = [];
  inputSubMaterial: SubMaterialElement[] = [];
  filteredInputSubMaterials: Observable<SubMaterialElement[]>;
  dataSource = new MatTableDataSource<SubMaterialElement>();
  inputSubMaterialForm: FormGroup = this.fb.group({
    inputSubMaterial: [""]
  });
  displayedColumns: string[] = [
    "id",
    "materialName",
    "subMaterialName",
    "subMaterialCode",
    "cost",
    "status",
    "stock",
    "unit",
    "enteredBy",
    "operator"
  ];

  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;

  constructor(
    private ref: ChangeDetectorRef,
    private dialog: MatDialog,
    private materialService: MaterialService,
    private router: Router,
    public fb: FormBuilder,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.getSubMaterial();
    this.displaySubMaterialList();
  }

  //显示二级物料查询列表
  displaySubMaterialList() {
    this.filteredInputSubMaterials = this.inputSubMaterialForm.controls.inputSubMaterial.valueChanges.pipe(
      startWith<string | SubMaterialElement>(""),
      map(value => (typeof value === "string" ? value : value.materialCode)),
      map(name =>
        name
          ? this.filteredInputSubMaterial(name)
          : this.inputSubMaterial.slice()
      )
    );
  }
  private filteredInputSubMaterial(name: string) {
    return this.inputSubMaterial.filter(
      inputSubMaterial => inputSubMaterial.subMaterialName.indexOf(name) === 0
    );
  }

  displayFn(inputSubMaterial?): string | undefined {
    return inputSubMaterial ? inputSubMaterial.subMaterialName : undefined;
  }

  // 初始化表格
  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  // 删除数据
  deleteSubMaterial(subMaterial: SubMaterialElement) {
    let flag = false;
    if (subMaterial && subMaterial.id > 0) {
      this.materialService.deleteSubMaterial(subMaterial.id).subscribe(
        (record: number) => {
          if (record > 0) {
            flag = true;
            this.getSubMaterial();
            this.openSnackBar(flag);
          }
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }
  // 删除提示
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("二级物料数据", "删除成功", { duration: 3000 });
      // this.dialogRef.close(this.user);
    } else {
      this.snackBar.open("二级物料数据", "删除失败", { duration: 3000 });
    }
  }
  // 查询
  searchList(): void {
    const name = this.inputSubMaterialForm.value.inputSubMaterial
      .subMaterialName;
    let tableData: SubMaterialElement[] = [];
    if (name && name.length > 0) {
      tableData = this.listSubMaterial.filter(
        listSubMaterial => listSubMaterial.subMaterialName.indexOf(name) === 0
      );
    } else {
      tableData = this.listSubMaterial;
    }
    this.dataSource = new MatTableDataSource<SubMaterialElement>(tableData);
    this.initMatTable();
  }
  // 增加物料
  addSubMaterial(): void {
    this.openEditSubMaterialDialog(
      new SubMaterialElement(0, "", "", "", "", "", 0, 0, "", 0)
    );
  }

  // 获取子物料列表;
  getSubMaterial(): void {
    this.materialService.getSubMaterial().subscribe(
      (data: SubMaterialElement[]) => {
        this.listSubMaterial = data;
        this.inputSubMaterial = this.listSubMaterial;
        this.dataSource = new MatTableDataSource<SubMaterialElement>(
          this.listSubMaterial
        );
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
  //  更新
  updateSubMaterial(subMaterial: SubMaterialElement): void {
    this.openEditSubMaterialDialog(subMaterial);
  }

  // 弹窗
  private openEditSubMaterialDialog(data: SubMaterialElement): void {
    const dialogRef = this.dialog.open(SubMaterialAddComponent, {
      width: "60%",
      data: data
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getSubMaterial();
    });
  }

  // 当输入框中有输入信息时，列出相应的一级物料信息
  getSearchSecondMaterial(): void {
    let tableData = [];
    const name = this.inputSubMaterialForm.value.inputSubMaterial;
    //获取input输入的值
    //在一级物料列表中找到关键值的数据，遍历出来
    console.log(name)
    console.log(this.listSubMaterial)
    for (let i = 0; i < this.listSubMaterial.length; i++) {
      if (this.listSubMaterial[i].subMaterialName.indexOf(name) != -1) {
        tableData.push(this.listSubMaterial[i]);
      }
    }
    this.dataSource = new MatTableDataSource<SubMaterialElement>(tableData);
    this.initMatTable();
  }
}
