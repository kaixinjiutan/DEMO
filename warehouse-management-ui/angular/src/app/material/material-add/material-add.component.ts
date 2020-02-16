import { Component, OnInit, ChangeDetectorRef, Inject } from "@angular/core";

import { MaterialService } from "../material.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MaterialElement, MaterialFormError, Error } from "../material";
import { MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

@Component({
  selector: "app-material-add",
  templateUrl: "./material-add.component.html",
  styleUrls: ["./material-add.component.css"]
})
export class MaterialAddComponent implements OnInit {
  material: MaterialElement;
  approveVerify: boolean = false;
  materialForm: FormGroup;
  materialFormError: MaterialFormError;
  isUpdate: boolean = false;
  constructor(
    public materialService: MaterialService,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public ref: ChangeDetectorRef,
    public dialogRef: MatDialogRef<MaterialAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: MaterialElement
  ) {
    this.material = this.data;
  }

  ngOnInit() {
    this.materialForm = this.fb.group({
      materialCode: [this.material.materialCode, Validators.required],
      materialName: [this.material.materialName, Validators.required]
    });
    this.materialFormError = new MaterialFormError(
      new Error(false, "请输入物料编码"),
      new Error(false, "请输入物料名称")
    );
    this.isUpdate = this.material && this.material.id && this.material.id > 0;
    if (this.isUpdate) {
      //如果是更新，禁止修改userCode
      this.materialForm.controls["materialCode"].disable();
    }
  }

  updateMaterial() {
    const MATERIAL_VALUE = this.materialForm.value;
    if (!this.isUpdate) {
      //如果是更新，禁止修改userCode，保持userCode不变，不要从表单里取值
      this.material.materialCode = MATERIAL_VALUE.materialCode;
    }
    this.material.materialName = MATERIAL_VALUE.materialName;
  }
  // 验证
  approved(): void {
    this.updateMaterial();
    if (
      !this.material ||
      !this.material.materialCode ||
      this.material.materialCode.length < 1
    ) {
      this.materialFormError.materialCode.show = true;
      this.materialFormError.materialCode.msg = "请输入物料编码";
    } else {
      this.materialFormError.materialCode.show = false;
      this.materialFormError.materialCode.msg = "";
    }
    if (
      !this.material ||
      !this.material.materialName ||
      this.material.materialName.length < 1
    ) {
      this.materialFormError.materialName.show = true;
      this.materialFormError.materialName.msg = "请输入物料名称";
    } else {
      this.materialFormError.materialName.show = false;
      this.materialFormError.materialName.msg = "";
    }
    if (
      this.materialFormError.materialName.show ||
      this.materialFormError.materialCode.show
    ) {
      this.approveVerify = false;
    } else {
      this.approveVerify = true;
    }
  }
  // 保存
  saveMaterial() {
    let sucessful = false;
    this.approved();
    if (this.approveVerify) {
      if (!this.isUpdate) {
        //如果是新增调用addMaterial接口
        this.materialService
          .addMaterial(this.material)
          .subscribe((id: number) => {
            if (id > 0) {
              this.material.id = id;
              sucessful = true;
              this.openSnackBar(sucessful);
            } else {
              this.openSnackBar(sucessful);
            }
          });
      } else {
        //如果是更新调用updateMaterial接口
        this.materialService
          .updateMaterial(this.material)
          .subscribe((id: number) => {
            if (id > 0) {
              this.material.id = id;
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
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("一级物料", "保存成功", { duration: 3000 });
      this.dialogRef.close(this.material);
    } else {
      this.snackBar.open("一级物料", "保存失败", { duration: 3000 });
    }
  }
}
