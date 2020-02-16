import { Component, OnInit, ChangeDetectorRef, Inject } from "@angular/core";

import { UserService } from "../user.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { UserElement, UserFormError, Error } from "../user";
import { MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

@Component({
  selector: "app-user-add",
  templateUrl: "./user-add.component.html",
  styleUrls: ["./user-add.component.scss"]
})
export class UserAddComponent implements OnInit {
  user: UserElement;
  approveVerify: boolean = false;
  userForm: FormGroup;
  userFormError: UserFormError;
  isUpdate: boolean = false;
  constructor(
    public userService: UserService,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public ref: ChangeDetectorRef,
    public dialogRef: MatDialogRef<UserAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserElement
  ) {
    this.user = this.data;
  }

  ngOnInit() {
    this.userForm = this.fb.group({
      userCode: [this.user.userCode, Validators.required],
      password: [this.user.password, Validators.required]
    });
    this.userFormError = new UserFormError(
      new Error(false, "请输入一个用户名"),
      new Error(false, "请输入密码")
    );
    this.isUpdate = this.user && this.user.id && this.user.id > 0;
    if (this.isUpdate) {
      //如果是更新，禁止修改userCode
      this.userForm.controls["userCode"].disable();
    }
  }

  updateUser() {
    const USER_VALUE = this.userForm.value;
    if (!this.isUpdate) {
      //如果是更新，禁止修改userCode，保持userCode不变，不要从表单里取值
      this.user.userCode = USER_VALUE.userCode;
    }
    this.user.password = USER_VALUE.password;
  }
  // 验证
  approved(): void {
    this.updateUser();
    if (!this.user || !this.user.userCode || this.user.userCode.length < 1) {
      this.userFormError.userCode.show = true;
      this.userFormError.userCode.msg = "请输入用户编码";
    } else {
      this.userFormError.userCode.show = false;
      this.userFormError.userCode.msg = "";
    }
    if (!this.user || !this.user.password || this.user.password.length < 1) {
      this.userFormError.password.show = true;
      this.userFormError.password.msg = "请输入用户密码";
    } else {
      this.userFormError.password.show = false;
      this.userFormError.password.msg = "";
    }
    if (this.userFormError.password.show || this.userFormError.userCode.show) {
      this.approveVerify = false;
    } else {
      this.approveVerify = true;
    }
  }
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("用户", "保存成功", { duration: 3000 });
      this.dialogRef.close(this.user);
    } else {
      this.snackBar.open("用户", "保存失败", { duration: 3000 });
    }
  }
  saveUser() {
    let sucessful = false;
    this.approved();
    if (this.approveVerify) {
      if (!this.isUpdate) {
        //如果是新增调用addUser接口
        this.userService.addUser(this.user).subscribe((id: number) => {
          if (id > 0) {
            this.user.id = id;
            sucessful = true;
            this.openSnackBar(sucessful);
          } else {
            this.openSnackBar(sucessful);
          }
        });
      } else {
        //如果是更新调用updateUser接口
        this.userService.updateUser(this.user).subscribe((id: number) => {
          if (id > 0) {
            this.user.id = id;
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

    console.log("userInfo");
    console.log(this.user);
  }
}
