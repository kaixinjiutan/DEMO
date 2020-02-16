import { Component, OnInit, ChangeDetectorRef, Inject } from "@angular/core";
import { CustomerElement, CustomerFormError, Error } from "../customer";
import { CustomerService } from "../customer.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

@Component({
  selector: "app-customer-add",
  templateUrl: "./customer-add.component.html",
  styleUrls: ["./customer-add.component.scss"]
})
export class CustomerAddComponent implements OnInit {
  customer: CustomerElement;
  customerForm: FormGroup;
  customerFormError: CustomerFormError;
  approveVerify: boolean = false;
  isUpdate: boolean = false;

  constructor(
    public customerService: CustomerService,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public ref: ChangeDetectorRef,
    public dialogRef: MatDialogRef<CustomerAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CustomerElement
  ) {
    this.customer = this.data;
  }

  ngOnInit() {
    this.customerForm = this.fb.group({
      customerCode: [this.customer.customerCode, Validators.required],
      customerName: [this.customer.customerName, Validators.required]
    });

    this.customerFormError = new CustomerFormError(
      new Error(false, "请输入一个客户"),
      new Error(false, "请输入客户名")
    );

    this.isUpdate = this.customer && this.customer.id && this.customer.id > 0;
    if (this.isUpdate) {
      //如果是更新，禁止修改userCode
      this.customerForm.controls["customerCode"].disable();
    }
  }

  updateCustomer() {
    const USER_VALUE = this.customerForm.value;
    if (!this.isUpdate) {
      //如果是更新，禁止修改userCode，保持userCode不变，不要从表单里取值
      this.customer.customerCode = USER_VALUE.customerCode;
    }
    this.customer.customerName = USER_VALUE.customerName;
  }

  // 验证
  approved(): void {
    this.updateCustomer();
    if (
      !this.customer ||
      !this.customer.customerCode ||
      this.customer.customerCode.length < 1
    ) {
      this.customerFormError.customerCode.show = true;
      this.customerFormError.customerCode.msg = "请输入客户编码";
    } else {
      this.customerFormError.customerCode.show = false;
      this.customerFormError.customerCode.msg = "";
    }
    if (
      !this.customer ||
      !this.customer.customerName ||
      this.customer.customerName.length < 1
    ) {
      this.customerFormError.customerName.show = true;
      this.customerFormError.customerName.msg = "请输入客户名称";
    } else {
      this.customerFormError.customerName.show = false;
      this.customerFormError.customerName.msg = "";
    }
    if (
      this.customerFormError.customerName.show ||
      this.customerFormError.customerCode.show
    ) {
      this.approveVerify = false;
    } else {
      this.approveVerify = true;
    }
  }

  // 提示
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("客户数据", "保存成功", { duration: 3000 });
      this.dialogRef.close(this.customer);
    } else {
      this.snackBar.open("客户数据", "保存失败", { duration: 3000 });
    }
  }
  // 填加
  saveCustomer() {
    let sucessful = false;
    this.approved();
    if (this.approveVerify) {
      if (!this.isUpdate) {
        //如果是新增调用addUser接口
        this.customerService
          .addCustomer(this.customer)
          .subscribe((id: number) => {
            if (id > 0) {
              this.customer.id = id;
              sucessful = true;
              this.openSnackBar(sucessful);
            } else {
              this.openSnackBar(sucessful);
            }
          });
      } else {
        //如果是更新调用updateUser接口
        this.customerService
          .updateCustomer(this.customer)
          .subscribe((id: number) => {
            if (id > 0) {
              this.customer.id = id;
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
