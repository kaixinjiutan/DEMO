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
import { CustomerService } from "../customer.service";
import { CustomerElement } from "../customer";
import { CustomerAddComponent } from "../customer-add/customer-add.component";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatTable, MatSort,  MatSnackBar, } from "@angular/material";
import { Router } from "@angular/router";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { map, startWith } from "rxjs/operators";
import { Observable } from "rxjs";

@Component({
  selector: "app-customer-list",
  templateUrl: "./customer-list.component.html",
  styleUrls: ["./customer-list.component.scss"]
})
@NgModule({
  declarations: [],
  imports: [MMaterialModule, SharedModule],
  bootstrap: [],
  exports: [MMaterialModule, SharedModule]
})
export class CustomerListComponent implements OnInit {
  listCustomer: CustomerElement[] = [];
  inputCustomer: CustomerElement[] = [];
  filteredInputCustomers: Observable<CustomerElement[]>;
  dataSource = new MatTableDataSource<CustomerElement>();
  displayedColumns: string[] = [
    "id",
    "customerName",
    "customerCode",
    "status",
    "operator"
  ];

  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;

  constructor(
    private customerService: CustomerService,
    private router: Router,
    public snackBar: MatSnackBar,
    private dialog: MatDialog,
    public fb: FormBuilder,
    private ref: ChangeDetectorRef
  ) { }

  inputCustomerForm: FormGroup = this.fb.group({
    inputCustomer: [""]
  });

  ngOnInit() {
    this.getCustomer();
    this.displayCustomerList();
  }
  //查询显示客户列表
  displayCustomerList() {
    console.log("显示客户列表");
    this.filteredInputCustomers = this.inputCustomerForm.controls.inputCustomer.valueChanges.pipe(
      startWith<string | CustomerElement>(""),
      map(value => (typeof value === "string" ? value : value.customerCode)),
      map(name =>
        name ? this.filteredInputCustomer(name) : this.inputCustomer.slice()
      )
    );
  }
  private filteredInputCustomer(name: string) {
    return this.inputCustomer.filter(
      inputUser => inputUser.customerName.indexOf(name) === 0
    );
  }

  displayFn(inputCustomer?): string | undefined {
    return inputCustomer ? inputCustomer.customerName : undefined;
  }
  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  // search
  searchList(): void {
    const name = this.inputCustomerForm.value.inputCustomer.customerCode;
    let tableData: CustomerElement[] = [];
    if (name && name.length > 0) {
      tableData = this.listCustomer.filter(
        listCustomer => listCustomer.customerCode.indexOf(name) === 0
      );
    } else {
      tableData = this.listCustomer;
    }
    this.dataSource = new MatTableDataSource<CustomerElement>(tableData);
    this.initMatTable();
  }
  // 删除数据
  deleteCustomer(customer: CustomerElement) {
    if (customer && customer.id > 0 && this.listCustomer.length > 1) {
      this.customerService.deleteCustomer(customer.id).subscribe(
        (record: number) => {
          if (record > 0) {
            this.getCustomer();
          }
          this.snackBar.open("客户数据", "删除成功", { duration: 3000 });
        },
        (error: any) => {
          console.log(error);
          this.snackBar.open("客户数据", "删除失败", { duration: 3000 });
        }
      );
    }
  }
  // 获取客户列表
  getCustomer(): void {
    this.customerService.getCustomer().subscribe(
      (data: CustomerElement[]) => {
        console.log("data");
        console.log(data);
        this.listCustomer = data;
        this.inputCustomer = this.listCustomer;
        this.dataSource = new MatTableDataSource<CustomerElement>(
          this.listCustomer
        );
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  add() {
    this.customerService.showAdd = true;
  }
  addCustomer() {
    this.openEditCustomerDialog(new CustomerElement(0, "", "", 0));
  }

  private openEditCustomerDialog(data: CustomerElement): void {
    const dialogRef = this.dialog.open(CustomerAddComponent, {
      width: "60%",
      data: data
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getCustomer();
    });
  }
  // 修改
  updateCustomer(customer: CustomerElement): void {
    this.openEditCustomerDialog(customer);
  }
  // 当输入框中有输入信息时，列出相应的客户信息
  getSearchCustomer() {
    let tableData = [];
    const name = this.inputCustomerForm.value.inputCustomer;
    //获取input输入的值
    //在客户列表中找到关键值的数据，遍历出来
    console.log("name");
    console.log(name);
    console.log(this.listCustomer);
    for (let i = 0; i < this.listCustomer.length; i++) {
      if (this.listCustomer[i].customerName.indexOf(name) != -1) {
        tableData.push(this.listCustomer[i]);
      }
    }
    this.dataSource = new MatTableDataSource<CustomerElement>(tableData);
    this.initMatTable();
  }
}
