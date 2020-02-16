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

import { ProductElement, MaterialRatioElement } from "../product";
import { ProductAddComponent } from "../product-add/product-add.component";

import { ProductService } from "../product.service";
import { Observable } from "rxjs";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { map, startWith } from "rxjs/operators";

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"]
})
export class ProductListComponent implements OnInit {
  inputProducts: ProductElement[] = [];
  listProducts: ProductElement[] = [];
  filteredInputProducts: Observable<ProductElement[]>;

  displayedColumns: string[] = ["id", "code", "name", "operators"];
  dataSource = new MatTableDataSource<ProductElement>();
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;

  inputProductForm = this.fb.group({
    inputProduct: [""]
  });

  constructor(
    public productService: ProductService,
    public ref: ChangeDetectorRef,
    public dialog: MatDialog,
    public fb: FormBuilder,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.getProducts();
    this.filteredInputProducts = this.inputProductForm.controls.inputProduct.valueChanges.pipe(
      startWith<string | ProductElement>(""),
      map(value => (typeof value === "string" ? value : value.productName)),
      map(name =>
        name ? this._filterInputProduct(name) : this.inputProducts.slice()
      )
    );
  }

  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  displayFn(inputProduct?): string | undefined {
    return inputProduct ? inputProduct.productName : undefined;
  }

  private _filterInputProduct(name: string) {
    return this.inputProducts.filter(
      inputProduct => inputProduct.productName.indexOf(name) === 0
    );
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(
      (data: ProductElement[]) => {
        this.listProducts = data;
        this.inputProducts = this.listProducts;
        this.dataSource = new MatTableDataSource<ProductElement>(
        this.listProducts
        );
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  searchList(): void {
    const name = this.inputProductForm.value.inputProduct.productName;
    let tableData: ProductElement[] = [];
    if (name && name.length > 0) {
      tableData = this.listProducts.filter(
        listProduct => listProduct.productName.indexOf(name) === 0
      );
    } else {
      tableData = this.listProducts;
    }
    this.dataSource = new MatTableDataSource<ProductElement>(tableData);
    this.initMatTable();
  }

  edit(data: ProductElement): void {
    // data.isEdit = true;
  }

  // save(data: ProductElement): void {
  //   // data.isEdit = false;
  //   alert('调用后端服务更新当前记录');
  //   // this.ref.detectChanges();
  // }
  addProduct(): void {
    this.openEditProductDialog(new ProductElement(0, null, null, 0, 0, 0));
  }

  updateProduct(data: ProductElement): void {
    this.openEditProductDialog(data);
  }

  private openEditProductDialog(data: ProductElement): void {
    const dialogRef = this.dialog.open(ProductAddComponent, {
      width: "60%",
      data: data
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getProducts();
    });
  }
   // 删除产品
   deleteProduct(product: ProductElement) {
    if (product && product.id > 0) {
      let sucessful = false;
      this.productService.deleteProduct(product.id).subscribe(
        (record: number) => {
          if (record > 0) {
            sucessful = true;
            this.getProducts();
          }
          this.openSnackBar(sucessful);
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }




  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("产品数据", "删除成功", { duration: 3000 });
      // this.dialogRef.close(this.user);
    } else {
      this.snackBar.open("产品数据", "删除失败", { duration: 3000 });
    }
  }
  // 当输入框中有输入信息时，列出相应的产品信息
  getSearchProduct(): void {
    let tableData = [];
    const name = this.inputProductForm.value.inputProduct;
    console.log(this.listProducts);
    //获取input输入的值
    //在产品列表中找到关键值的数据，遍历出来
    for (let i = 0; i < this.listProducts.length; i++) {
      if (this.listProducts[i].productName.indexOf(name) != -1) {
        tableData.push(this.listProducts[i]);
      }
    }
    this.dataSource = new MatTableDataSource<ProductElement>(tableData);
    this.initMatTable();
  }
}
