import {
  Component,
  OnInit,
  ChangeDetectorRef,
  Inject,
  ViewChild
} from "@angular/core";
import { MaterialService } from "../material.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import {
  MaterialElement,
  SubMaterialElement,
  SubMaterialFormError,
  Error
} from "../material";
// import { MatTableDataSource } from "@angular/material/table";
// import { MatPaginator } from "@angular/material/paginator";
// import { MatSort } from "@angular/material";
import { MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";

@Component({
  selector: "app-sub-material-add",
  templateUrl: "./sub-material-add.component.html",
  styleUrls: ["./sub-material-add.component.css"]
})
export class SubMaterialAddComponent implements OnInit {
  approveVerify: boolean = false;
  isUpdate: boolean = false;
  listMaterial: MaterialElement[] = [];
  inputMaterials: MaterialElement[] = [];
  subMaterial: SubMaterialElement;
  subMaterialFormError: SubMaterialFormError;
  filteredInputMaterials: Observable<MaterialElement[]>;
  subMaterialForm: FormGroup;

  constructor(
    public materialService: MaterialService,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public ref: ChangeDetectorRef,
    public dialogRef: MatDialogRef<SubMaterialAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SubMaterialElement
  ) {
    this.subMaterial = this.data;
  }
  ngOnInit() {
    this.subMaterialForm = this.fb.group({
      material: [
        {
          materialCode: this.subMaterial.materialCode,
          materialName: this.subMaterial.materialName
        },
        Validators.required
      ],
      subMaterialCode: [this.subMaterial.subMaterialCode, Validators.required],
      subMaterialName: [this.subMaterial.subMaterialName, Validators.required],
      cost: [this.subMaterial.cost, Validators.required],
      stock: [this.subMaterial.stock, Validators.required],
      unit: [this.subMaterial.unit, Validators.required]
    });
    this.subMaterialFormError = new SubMaterialFormError(
      new Error(false, "请输入"),
      new Error(false, "请输入"),
      new Error(false, "请输入"),
      new Error(false, "请输入"),
      new Error(false, "请输入"),
      new Error(false, "请输入")
    );
    this.isUpdate =
      this.subMaterial && this.subMaterial.id && this.subMaterial.id > 0;
    if (this.isUpdate) {
      //如果是更新，禁止修改subMaterialCode
      this.subMaterialForm.controls["subMaterialCode"].disable();
    }
    this.getMaterial();
    this.displayMaterialList();
  }

  updateSubmatetiral() {
    const SUBMATERIAL_VALUE = this.subMaterialForm.value;
    if (!this.isUpdate) {
      //如果是更新，禁止修改materialCode，保持materialCode不变，不要从表单里取值
      this.subMaterial.subMaterialCode = SUBMATERIAL_VALUE.subMaterialCode;
    }

    this.subMaterial.subMaterialName = SUBMATERIAL_VALUE.subMaterialName;
    this.subMaterial.stock = SUBMATERIAL_VALUE.stock;
    this.subMaterial.unit = SUBMATERIAL_VALUE.unit;
    this.subMaterial.cost = SUBMATERIAL_VALUE.cost;
    this.subMaterial.materialCode = SUBMATERIAL_VALUE.material.materialCode;
    for (let i = 0; i < this.listMaterial.length; i++) {
      if (this.listMaterial[i].materialCode == this.subMaterial.materialCode) {
        this.subMaterial.materialName = this.listMaterial[i].materialCode;
      }
    }
  }

  // 获取物料列表
  getMaterial(): void {
    this.materialService.getMaterial().subscribe(
      (data: MaterialElement[]) => {
        this.listMaterial = data;
        this.inputMaterials = this.listMaterial;
        // this.dataSource = new MatTableDataSource<MaterialElement>(
        //   this.listMaterial
        // );
        // this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  //显示选中的一级物料名称
  displayFn(inputMaterialObj?): string | undefined {
    return inputMaterialObj ? inputMaterialObj.materialName : undefined;
  }
  //显示一级物料列表
  private filteredInputMaterial(name: string) {
    return this.inputMaterials.filter(
      inputMaterial => inputMaterial.materialName.indexOf(name) === 0
    );
  }

  displayMaterialList() {
    this.filteredInputMaterials = this.subMaterialForm.controls.material.valueChanges.pipe(
      startWith<string | MaterialElement>(""),
      map(value => (typeof value === "string" ? value : value.materialName)),
      map(name =>
        name ? this.filteredInputMaterial(name) : this.inputMaterials.slice()
      )
    );
    console.log(this.filteredInputMaterials);
  }

  //增加二级物料提示信息
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("二级物料", "保存成功", { duration: 3000 });
      this.dialogRef.close(this.subMaterial);
    } else {
      this.snackBar.open("二级物料", "保存失败", { duration: 3000 });
    }
  }

  // 验证
  approved(): void {
    this.updateSubmatetiral();
    if (
      !this.subMaterial ||
      !this.subMaterial.materialCode ||
      this.subMaterial.materialCode.length < 1
    ) {
      this.subMaterialFormError.materialCode.show = true;
      this.subMaterialFormError.materialCode.msg = "请选择一级物料";
    } else {
      this.subMaterialFormError.materialCode.show = false;
      this.subMaterialFormError.materialCode.msg = "";
    }
    // 二级物料编码
    if (
      !this.subMaterial ||
      !this.subMaterial.subMaterialCode ||
      this.subMaterial.subMaterialCode.length < 1
    ) {
      this.subMaterialFormError.subMaterialCode.show = true;
      this.subMaterialFormError.subMaterialCode.msg = "请输入二级物料编码";
    } else {
      this.subMaterialFormError.subMaterialCode.show = false;
      this.subMaterialFormError.subMaterialCode.msg = "";
    }
    // 二级物料名称
    if (
      !this.subMaterial ||
      !this.subMaterial.subMaterialName ||
      this.subMaterial.subMaterialName.length < 1
    ) {
      this.subMaterialFormError.subMaterialName.show = true;
      this.subMaterialFormError.subMaterialName.msg = "请输入二级物料名称";
    } else {
      this.subMaterialFormError.subMaterialName.show = false;
      this.subMaterialFormError.subMaterialName.msg = "";
    }
    // 二级物料库存
    if (
      !this.subMaterial ||
      !this.subMaterial.stock ||
      this.subMaterial.stock == 0
    ) {
      this.subMaterialFormError.stock.show = true;
      this.subMaterialFormError.stock.msg = "请输入库存";
    } else {
      this.subMaterialFormError.stock.show = false;
      this.subMaterialFormError.stock.msg = "";
    }

    // 二级物料计量单位
    if (!this.subMaterial || !this.subMaterial.unit) {
      this.subMaterialFormError.unit.show = true;
      this.subMaterialFormError.unit.msg = "请输入计量单位";
    } else {
      this.subMaterialFormError.unit.show = false;
      this.subMaterialFormError.unit.msg = "";
    }
    if (
      this.subMaterialFormError.materialCode.show ||
      this.subMaterialFormError.subMaterialName.show ||
      this.subMaterialFormError.subMaterialCode.show ||
      this.subMaterialFormError.stock.show ||
      this.subMaterialFormError.unit.show
    ) {
      this.approveVerify = false;
    } else {
      this.approveVerify = true;
    }
  }
  //save
  saveSubMaterial() {
    let sucessful = false;
    this.approved();
    console.log("saveSubMaterial");
    console.log(this.subMaterial);
    if (this.approveVerify) {
      if (!this.isUpdate) {
        //如果是新增调用addUser接口
        this.materialService
          .addSubMaterial(this.subMaterial)
          .subscribe((id: number) => {
            if (id > 0) {
              this.subMaterial.id = id;
              sucessful = true;
              this.openSnackBar(sucessful);
            } else {
              this.openSnackBar(sucessful);
            }
          });
      } else {
        //如果是更新调用updateUser接口
        this.materialService
          .updateSubMaterial(this.subMaterial)
          .subscribe((id: number) => {
            if (id > 0) {
              this.subMaterial.id = id;
              sucessful = true;
              this.openSnackBar(sucessful);
            } else {
              this.openSnackBar(sucessful);
            }
          });
      }
    } else {
      this.openSnackBar(sucessful);
    }
  }
}
