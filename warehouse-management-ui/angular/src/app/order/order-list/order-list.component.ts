import {
  Component,
  OnInit,
  Inject,
  ViewChild,
  ChangeDetectorRef
} from "@angular/core";
import {
  MatTable,
  MatSort,
  MatPaginator,
  MatTableDataSource,
  MatSnackBar
} from "@angular/material";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { CdkTableModule } from "@angular/cdk/table";
import {
  trigger,
  state,
  style,
  animate,
  transition
} from "@angular/animations";

import { OrderInfoElement } from "../order";
import { ProductElement } from "../../product/product";
import { OrderAddComponent } from "../order-add/order-add.component";

import { OrderService } from "../order.service";
import { Observable } from "rxjs";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { map, startWith } from "rxjs/operators";

@Component({
  selector: "app-order-list",
  templateUrl: "./order-list.component.html",
  styleUrls: ["./order-list.component.scss"]
})
export class OrderListComponent implements OnInit {
  inputOrders: OrderInfoElement[] = [];
  listOrders: OrderInfoElement[] = [];
  filteredInputOrders: Observable<OrderInfoElement[]>;

  displayedColumns: string[] = [
    "id",
    "orderNo",
    "customerCode",
    "customerName",
    "startDate",
    "endDate",
    "operators"
  ];
  dataSource = new MatTableDataSource<OrderInfoElement>();
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;

  inputOrderForm = this.fb.group({
    inputOrder: [""]
  });

  constructor(
    public orderService: OrderService,
    public ref: ChangeDetectorRef,
    public dialog: MatDialog,
    public fb: FormBuilder,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.getOrders();
    this.filteredInputOrders = this.inputOrderForm.controls.inputOrder.valueChanges.pipe(
      startWith<string | OrderInfoElement>(""),
      map(value => (typeof value === "string" ? value : value.orderNo)),
      map(name =>
        name ? this._filterInputOrder(name) : this.inputOrders.slice()
      )
    );
  }

  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  displayFn(inputOrder?): string | undefined {
    return inputOrder ? inputOrder.orderNo : undefined;
  }

  private _filterInputOrder(name: string) {
    return this.inputOrders.filter(
      inputOrder => inputOrder.orderNo.indexOf(name) === 0
    );
  }

  getOrders(): void {
    this.orderService.getOrders().subscribe(
      (data: OrderInfoElement[]) => {
        this.listOrders = data;
        console.log("listOrders");
        console.log(this.listOrders);
        this.inputOrders = this.listOrders;
        this.dataSource = new MatTableDataSource<OrderInfoElement>(
          this.listOrders
        );
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  searchList(): void {
    const name = this.inputOrderForm.value.inputOrder.orderNo;
    let tableData: OrderInfoElement[] = [];
    if (name && name.length > 0) {
      tableData = this.listOrders.filter(
        listOrder => listOrder.orderNo.indexOf(name) === 0
      );
    } else {
      tableData = this.listOrders;
    }
    this.dataSource = new MatTableDataSource<OrderInfoElement>(tableData);
    this.initMatTable();
  }

  edit(data: OrderInfoElement): void {
    // data.isEdit = true;
  }
  getNowFormatDate(): string {
    let date = new Date();
    let year = date.getFullYear() + "";
    let month =
      date.getMonth() + 1 <= 9
        ? "0" + date.getMonth() + 1
        : date.getMonth() + 1;
    let dateOfMonth =
      date.getDate() <= 9 ? "0" + date.getDate() : date.getDate();
    let hours = date.getHours() <= 9 ? "0" + date.getHours() : date.getHours();
    let minutes =
      date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes();
    let seconds =
      date.getSeconds() <= 9 ? "0" + date.getSeconds() : date.getSeconds();
    let currentdate = year + month + dateOfMonth + hours + minutes + seconds;
    return currentdate;
  }
  addOrder(): void {
    let mytime = "O" + this.getNowFormatDate();
    this.openEditOrderDialog(
      new OrderInfoElement(0, mytime, null, null, null, null, 0, null)
    );
  }

  updateOrder(data: OrderInfoElement): void {
    this.openEditOrderDialog(data);
  }
  // 删除订单
  deleteOrder(orderInfo: OrderInfoElement): void {
    let flag = false;
    if (orderInfo && orderInfo.id > 0) {
      this.orderService.deleteOrder(orderInfo.id).subscribe(
        (record: number) => {
          if (record > 0) {
            this.getOrders();
            flag = true;
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
      this.snackBar.open("订单数据", "删除成功", { duration: 3000 });
      // this.dialogRef.close(this.user);
    } else {
      this.snackBar.open("订单数据", "删除失败", { duration: 3000 });
    }
  }

  private openEditOrderDialog(data: OrderInfoElement): void {
    const dialogRef = this.dialog.open(OrderAddComponent, {
      width: "60%",
      data: data
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getOrders();
    });
  }
  // 当输入框中有输入信息时，列出相应的订单信息
  getSearchOrder(): void {
    let tableData = [];
    const name = this.inputOrderForm.value.inputOrder;
    console.log(this.listOrders);
    //获取input输入的值
    //在订单列表中找到关键值的数据，遍历出来
    for (let i = 0; i < this.listOrders.length; i++) {
      if (this.listOrders[i].orderNo.indexOf(name) != -1) {
        tableData.push(this.listOrders[i]);
      }
    }
    this.dataSource = new MatTableDataSource<OrderInfoElement>(tableData);
    this.initMatTable();
  }
}
