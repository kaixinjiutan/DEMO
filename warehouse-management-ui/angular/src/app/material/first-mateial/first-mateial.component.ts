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
import { MaterialElement } from "../material";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MaterialService } from "../material.service";
import { MatSort, MatSnackBar } from "@angular/material";
import { MaterialAddComponent } from "../material-add/material-add.component";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";

@Component({
  selector: "app-first-mateial",
  templateUrl: "./first-mateial.component.html",
  styleUrls: ["./first-mateial.component.css"]
})
@NgModule({
  declarations: [],
  imports: [MMaterialModule, SharedModule],
  bootstrap: [],
  exports: [MMaterialModule, SharedModule]
})
export class FirstMateialComponent implements OnInit {
  listMaterial: MaterialElement[] = [];
  inputMaterial: MaterialElement[] = [];
  filteredInputMaterials: Observable<MaterialElement[]>;
  dataSource = new MatTableDataSource<MaterialElement>();
  inputMaterialForm: FormGroup = this.fb.group({
    inputMaterial: [""]
  });
  displayedColumns: string[] = [
    "id",
    "materialCode",
    "materialName",
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
  ) {}

  ngOnInit() {
    this.getMaterial();
    this.displayMaterialList();
  }

  displayMaterialList() {
    console.log("displayMaterialList");
    this.filteredInputMaterials = this.inputMaterialForm.controls.inputMaterial.valueChanges.pipe(
      startWith<string | MaterialElement>(""),
      map(value => (typeof value === "string" ? value : value.materialCode)),
      map(name =>
        name ? this.filteredInputMaterial(name) : this.inputMaterial.slice()
      )
    );
    console.log("this.filteredInputMaterials");
    console.log(this.filteredInputMaterials);
  }
  // 更新
  updateMaterial(material: MaterialElement): void {
    this.openEditUserDialog(material);
  }

  // 查询
  searchList(): void {
    const name = this.inputMaterialForm.value.inputMaterial.materialCode;
    let tableData: MaterialElement[] = [];
    if (name && name.length > 0) {
      tableData = this.listMaterial.filter(
        listMaterial => listMaterial.materialCode.indexOf(name) === 0
      );
    } else {
      tableData = this.listMaterial;
    }
    this.dataSource = new MatTableDataSource<MaterialElement>(tableData);
    this.initMatTable();
  }
  private filteredInputMaterial(name: string) {
    return this.inputMaterial.filter(
      inputMaterial => inputMaterial.materialName.indexOf(name) === 0
    );
  }

  displayFn(inputMaterial?): string | undefined {
    return inputMaterial ? inputMaterial.materialName : undefined;
  }

  // 获取物料列表
  getMaterial(): void {
    this.materialService.getMaterial().subscribe(
      (data: MaterialElement[]) => {
        this.listMaterial = data;
        console.log("data");
        console.log(data);
        this.inputMaterial = this.listMaterial;
        this.dataSource = new MatTableDataSource<MaterialElement>(
          this.listMaterial
        );
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  addMateial(): void {
    this.openEditUserDialog(new MaterialElement(0, "", "", "", 0));
  }
  // 弹窗
  private openEditUserDialog(data: MaterialElement): void {
    const dialogRef = this.dialog.open(MaterialAddComponent, {
      width: "60%",
      data: data
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getMaterial();
    });
  }

  // 删除数据
  deleteMaterial(material: MaterialElement) {
    let flag = false;
    if (material && material.id > 0) {
      this.materialService.deleteMaterial(material.id).subscribe(
        (record: number) => {
          if (record > 0) {
            flag = true;
            this.getMaterial();
            this.openSnackBar(flag);
          }
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("一级物料数据", "删除成功", { duration: 3000 });
      // this.dialogRef.close(this.user);
    } else {
      this.snackBar.open("一级物料数据", "删除失败", { duration: 3000 });
    }
  }

  // 当输入框中有输入信息时，列出相应的一级物料信息
  getSearchFirstMaterial(): void {
    let tableData = [];
    const name = this.inputMaterialForm.value.inputMaterial;
    //获取input输入的值
    //在一级物料列表中找到关键值的数据，遍历出来
    console.log(this.listMaterial);
    for (let i = 0; i < this.listMaterial.length; i++) {
      if (this.listMaterial[i].materialName.indexOf(name) != -1) {
        tableData.push(this.listMaterial[i]);
      }
    }
    this.dataSource = new MatTableDataSource<MaterialElement>(tableData);
    this.initMatTable();
  }
}
