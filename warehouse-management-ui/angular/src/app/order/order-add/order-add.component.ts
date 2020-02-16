import {
  Component,
  OnInit,
  Inject,
  ViewChild, ChangeDetectorRef
} from "@angular/core";
import { CdkTableModule } from "@angular/cdk/table";
import { OrderService } from "../order.service";
import { ProductService } from "../../product/product.service";
import { CustomerService } from "../../customer/customer.service";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";



import {
  OrderFamilyElement,
  OrderInfoElement,
  OrderProductElement,
  OrderFormError,
  OrderProductFormError,
  Error
} from "../order";
import { ProductElement } from "../../product/product";
import { CustomerElement } from "../../customer/customer";
import {
  MatTable,
  MatSort,
  MatPaginator,
  MatTableDataSource
} from "@angular/material";
import { MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";


@Component({
  selector: "app-order-add",
  templateUrl: "./order-add.component.html",
  styleUrls: ["./order-add.component.scss"],

})
export class OrderAddComponent implements OnInit {
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;


  startDate = new FormControl(new Date());
  endDate = new FormControl(new Date());

  displayedColumns: string[] = ["id", "productCode", "productName", "quantity"];
  dataSource = new MatTableDataSource<OrderProductElement>();
  products: ProductElement[];
  customers: CustomerElement[];
  order: OrderInfoElement;
  orderProduct: OrderProductElement = new OrderProductElement(
    0,
    null,
    null,
    null,
    null,
    0,
    null
  );
  orderProducts: OrderProductElement[] = [];
  orderFormError: OrderFormError = new OrderFormError(
    new Error(false, ""),
    new Error(false, ""),
    new Error(false, ""),
    new Error(false, ""),
    new Error(false, "")
  );
  orderProductFormError: OrderProductFormError = new OrderProductFormError(
    new Error(false, ""),
    new Error(false, "")
  );

  approveOrderVerify: boolean = false;
  approveProductVerify: boolean = false;
  isUpdate: boolean = false;

  //设置表单时间
  orderForm: FormGroup = this.fb.group({
  });

  constructor(
    public orderService: OrderService,
    public productService: ProductService,
    public customerService: CustomerService,
    public ref: ChangeDetectorRef,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<OrderAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OrderInfoElement
  ) {
    this.order = data;
  }
  // orderForm: FormGroup;
  orderProductForm: FormGroup;
  ngOnInit() {
    this.getProducts();
    this.getCustomers();
    if (this.order && this.order.orderNo && this.order.orderNo.length > 0) {
      this.getParts(this.order.orderNo);
    }
    this.orderForm = this.fb.group({
      orderNo: [this.order.orderNo, Validators.required],
      customerCode: [this.order.customerCode, Validators.required],
      startDate: [this.order.startDate, Validators.required],
      endDate: [this.order.endDate, Validators.required],

    });
    this.orderProductForm = this.fb.group({
      productCode: ["", Validators.required],
      productQuantity: ["", Validators.required]
    });
    this.isUpdate = this && this.order.id && this.order.id > 0;
    //订单号自动生成
    this.orderForm.controls["orderNo"].disable();
    if (this.isUpdate) {
      //如果是更新，禁止修改customerCode
      this.orderForm.controls["customerCode"].disable();
    }
  }
  //input获取焦点的时候列出客户名称
  displayUserList() {
    // this.filteredInputUsers = this.inputUserForm.controls.inputUser.valueChanges.pipe(
    //   startWith<string | UserElement>(""),
    //   map(value => (typeof value === "string" ? value : value.userCode)),
    //   map(name =>
    //     name ? this.filteredInputUser(name) : this.inputUsers.slice()
    //   )
    // );
    console.log("列出所有的用户名")
  }
  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getParts(orderNo: string): void {
    this.orderService
      .getProductsOfOrder(orderNo)
      .subscribe((data: OrderProductElement[]) => {
        this.orderProducts = data;
        this.dataSource = new MatTableDataSource<OrderProductElement>(
          this.orderProducts
        );
        this.initMatTable();
      });
  }

  refreshParts(): void {
    this.dataSource = new MatTableDataSource<OrderProductElement>(
      this.orderProducts
    );
    console.log("this.dataSource");
    console.log(this.dataSource);
    this.initMatTable();
  }

  isShow(): boolean {
    return this.orderProducts && this.orderProducts.length > 0 ? true : false;
  }

  getCustomers(): void {
    this.customerService.getCustomer().subscribe(
      (data: CustomerElement[]) => {
        this.customers = data;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
  getProducts(): void {
    this.productService.getProducts().subscribe(
      (data: ProductElement[]) => {
        this.products = data;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  // resetAddOrderForm() {
  //   this.orderForm.reset();
  // }
  approvedOrderProduct(): void {
    this.updateOrderProduct();
    if (
      !this.orderProduct ||
      !this.orderProduct.productCode ||
      this.orderProduct.productCode.length < 1
    ) {
      this.orderProductFormError.productCode.show = true;
      this.orderProductFormError.productCode.msg = "请选择订单";
    } else {
      this.orderProductFormError.productCode.show = false;
      this.orderProductFormError.productCode.msg = "";
    }
    if (
      !this.orderProduct ||
      !this.orderProduct.quantity ||
      this.orderProduct.quantity < 1
    ) {
      this.orderProductFormError.productQuantity.show = true;
      this.orderProductFormError.productQuantity.msg = "请输入订单数量";
    } else {
      this.orderProductFormError.productQuantity.show = false;
      this.orderProductFormError.productQuantity.msg = "";
    }
    if (
      this.orderProductFormError.productCode.show ||
      this.orderProductFormError.productQuantity.show
    ) {
      this.approveProductVerify = false;
    } else {
      this.approveProductVerify = true;
    }
  }

  approvedOrder(): void {
    this.updateOrder();
    if (!this.order || !this.order.orderNo || this.order.orderNo.length < 1) {
      this.orderFormError.orderNo.show = true;
      this.orderFormError.orderNo.msg = "请输入订单编码";
    } else {
      this.orderFormError.orderNo.show = false;
      this.orderFormError.orderNo.msg = "";
    }
    if (
      !this.order ||
      !this.order.customerCode ||
      this.order.customerCode.length < 1
    ) {
      this.orderFormError.customerCode.show = true;
      this.orderFormError.customerCode.msg = "请输入客户编码";
    } else {
      this.orderFormError.customerCode.show = false;
      this.orderFormError.customerCode.msg = "";
    }
    if (!this.orderProducts || this.orderProducts.length < 1) {
      this.orderFormError.orderProducts.show = true;
      this.orderFormError.orderProducts.msg = "请添加订单信息";
    } else {
      this.orderFormError.orderProducts.show = false;
      this.orderFormError.orderProducts.msg = "";
    }
    if (
      this.orderFormError.orderNo.show ||
      this.orderFormError.customerCode.show ||
      this.orderFormError.orderProducts.show
    ) {
      this.approveOrderVerify = false;
    } else {
      this.approveOrderVerify = true;
    }
  }

  updateOrderProduct() {
    const ORDER_VALUE = this.orderForm.value;
    const ORDER_PRODUCT_VALUE = this.orderProductForm.value;
    this.orderProduct.productCode = ORDER_PRODUCT_VALUE.productCode;
    this.orderProduct.quantity = ORDER_PRODUCT_VALUE.productQuantity;
  }

  updateOrder() {
    const ORDER_VALUE = this.orderForm.value;
    //无论是新增还是更新都不允许修改订单编号
    if (!this.isUpdate) {
      //如果是更新，禁止修改customerCode，保持customerCode不变，不要从表单里取值
      this.order.customerCode = ORDER_VALUE.customerCode;
    }
  }

  saveOrder() {
    //获取开始日期
    let formatStartDate = this.startDate.value;
    let syear = formatStartDate.getFullYear();
    let smonth = formatStartDate.getMonth() + 1;
    let sdate = formatStartDate.getDate();
    let startDate = smonth + '/' + sdate + '/' + syear;

    //获取结束日期
    let formatEndtDate = this.endDate.value;
    let eyear = formatEndtDate.getFullYear();
    let emonth = formatEndtDate.getMonth() + 1;
    let edate = formatEndtDate.getDate();
    let endDate = emonth + '/' + edate + '/' + eyear;
    // console.log(startDate);
    // console.log("startDate");
    // console.log(endDate);
    // console.log("endDate");
    this.order.startDate = startDate;
    this.order.endDate = endDate;

    let sucessful = false;
    this.approvedOrder();
    if (this.approveOrderVerify) {
      if (!this.isUpdate) {
        const param = new OrderFamilyElement(
          this.order,
          this.orderProducts,
          null,
          null
        );
        this.orderService
          .addOrder(param)
          .subscribe((data: OrderFamilyElement) => {
            console.log(param);
            console.log("param");
            let id = data.order.id;
            if (id > 0) {
              this.order.id = id;
              sucessful = true;
              this.openSnackBar(sucessful);
            } else {
              this.openSnackBar(sucessful);
            }
          });
      } else {
        const param = new OrderFamilyElement(
          this.order,
          this.orderProducts,
          null,
          null
        );
        this.orderService
          .updateOrder(param)
          .subscribe((data: OrderFamilyElement) => {
            let id = data.order.id;
            if (id > 0) {
              this.order.id = id;
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


  removeOrderProduct(ratio: OrderProductElement) { }

  addOrderProduct() {
    console.log(this.orderProduct);
    console.log("this.orderProduct");
    this.approvedOrderProduct();
    if (this.approveProductVerify) {
      this.orderProducts.push(
        new OrderProductElement(
          this.orderProduct.id,
          this.orderProduct.orderNo,
          this.orderProduct.productCode,
          this.orderProduct.productName,
          this.orderProduct.quantity,
          this.orderProduct.status,
          this.orderProduct.enteredBy
        )
      );
      this.refreshParts();
      // this.ref.detectChanges();
    } else {
      // this.ref.detectChanges();
      this.snackBar.open("订单", "添加失败", { duration: 3000 });
    }
  }

  remove(data: OrderInfoElement): void {
    // data.isEdit = false;
    //找到要移除的元素下标，把0换成目标元素的下表
    // this.listOrders.splice(0, 1);
    // this.dataSource = new MatTableDataSource<OrderElement>(this.listOrders);
    // this.initMatTable();
    // this.ref.detectChanges();
    // this.ref.tick();
  }

  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("订单", "保存成功", { duration: 3000 });
      this.dialogRef.close(this.order);
    } else {
      this.snackBar.open("订单", "保存失败", { duration: 3000 });
    }
  }

  closedStream() {
    alert(1);
  }

}
