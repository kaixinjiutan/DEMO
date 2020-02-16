import {
  Component,
  OnInit,
  Inject,
  ViewChild,
  Input,
  Output,
  EventEmitter,
  ChangeDetectorRef
} from "@angular/core";
import { CdkTableModule } from "@angular/cdk/table";
import { ProductService } from "../product.service";
import { MaterialService } from "../../material/material.service";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import {
  ProductFamilyElement,
  ProductElement,
  MaterialRatioElement,
  ProductFormError,
  MaterialRatioFormError,
  Error
} from "../product";
import { MaterialElement, SubMaterialElement } from "../../material/material";
import {
  MatTable,
  MatSort,
  MatPaginator,
  MatTableDataSource
} from "@angular/material";
import { MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

@Component({
  selector: "app-product-add",
  templateUrl: "./product-add.component.html",
  styleUrls: ["./product-add.component.scss"]
})
export class ProductAddComponent implements OnInit {
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;

  displayedColumns: string[] = [
    "id",
    "materialCode",
    "materialName",
    "subMaterialCode",
    "subMaterialName",
    "radio",
    "operators"
  ];
  dataSource = new MatTableDataSource<MaterialRatioElement>();
  product: ProductElement;
  firstMaterials: MaterialElement[];
  secondMaterials: SubMaterialElement[];
  filterSecondMaterials: SubMaterialElement[];
  materialRatio: MaterialRatioElement = new MaterialRatioElement(
    0,
    null,
    null,
    null,
    null,
    null,
    null,
    0,
    null
  );
  materialRatioes: MaterialRatioElement[] = [];
  productFormError: ProductFormError = new ProductFormError(
    new Error(false, ""),
    new Error(false, ""),
    new Error(false, ""),
    new Error(false, "")
  );
  materialRatioFormError: MaterialRatioFormError = new MaterialRatioFormError(
    new Error(false, ""),
    new Error(false, ""),
    new Error(false, "")
  );

  approveProductVerify: boolean = false;
  approveMaterialsVerify: boolean = false;
  isUpdate: boolean = false;

  constructor(
    public productService: ProductService,
    public materialService: MaterialService,
    public ref: ChangeDetectorRef,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<ProductAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductElement
  ) {
    this.product = data;
  }
  productForm: FormGroup;
  materialRatioForm: FormGroup;
  ngOnInit() {
    this.getMaterials();
    this.getSubMaterials();
    if (
      this.product &&
      this.product.productCode &&
      this.product.productCode.length > 0
    ) {
      this.getParts(this.product.productCode);
    }
    this.productForm = this.fb.group({
      productCode: [this.product.productCode, Validators.required],
      productName: [this.product.productName, Validators.required],
      markup: [this.product.markup, Validators.required]

    });
    this.materialRatioForm = this.fb.group({
      materialCode: ["", Validators.required],
      subMaterialCode: ["", Validators.required],
      ratio: ["", Validators.required]
    });
    this.isUpdate = this && this.product.id && this.product.id > 0;
    if (this.isUpdate) {
      //如果是更新，禁止修改productCode
      this.productForm.controls["productCode"].disable();
    }
  }

  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getParts(productCode: string): void {
    this.productService
      .getMaterialsOfProduct(productCode)
      .subscribe((data: MaterialRatioElement[]) => {
        this.materialRatioes = data;
        this.dataSource = new MatTableDataSource<MaterialRatioElement>(
          this.materialRatioes
        );
        this.initMatTable();
      });
  }

  refreshParts(): void {
    this.dataSource = new MatTableDataSource<MaterialRatioElement>(
      this.materialRatioes
    );
    this.initMatTable();
  }

  isShow(): boolean {
    return this.materialRatioes && this.materialRatioes.length > 0
      ? true
      : false;
  }
  // 获取一级物料
  getMaterials(): void {
    this.materialService.getMaterial().subscribe(
      (data: MaterialElement[]) => {
        this.firstMaterials = data;
        console.log("this.firstMaterials");
        console.log(this.firstMaterials);
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  // 获取二级物料
  getSubMaterials(): void {
    this.materialService.getSubMaterial().subscribe(
      (data: SubMaterialElement[]) => {
        this.secondMaterials = data;
        console.log("this.secondMaterials");
        console.log(this.secondMaterials);
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  // 获取物料配比表
  // getMaterialRatio(): void {
  //   this.productService.getMaterialRatio().subscribe(
  //     (data: MaterialRatioElement[]) => {
  //       this.materialRatioes = data;
  //       this.initMatTable();
  //     },
  //     (error: any) => {
  //       console.log(error);
  //     }
  //   );
  // }







  filterSencondMateria(): void {
    const MATERIAL_VALUE = this.materialRatioForm.value;
    let materialCode = MATERIAL_VALUE.materialCode;
    this.filterSecondMaterials = this.secondMaterials.filter(function (item) {
      return item.materialCode == materialCode;
    });
  }

  approvedMaterialRatio(): void {
    this.updateMaterialRatio();
    if (
      !this.materialRatio ||
      !this.materialRatio.subMaterialCode ||
      this.materialRatio.subMaterialCode.length < 1
    ) {
      this.materialRatioFormError.subMaterialCode.show = true;
      this.materialRatioFormError.subMaterialCode.msg = "请选择二级物料";
    } else {
      this.materialRatioFormError.subMaterialCode.show = false;
      this.materialRatioFormError.subMaterialCode.msg = "";
    }
    if (
      !this.materialRatio ||
      !this.materialRatio.subMaterialRatio ||
      this.materialRatio.subMaterialRatio < 1
    ) {
      this.materialRatioFormError.ratio.show = true;
      this.materialRatioFormError.ratio.msg = "请输入物料配比";
    } else {
      this.materialRatioFormError.ratio.show = false;
      this.materialRatioFormError.ratio.msg = "";
    }
    if (
      this.materialRatioFormError.materialCode.show ||
      this.materialRatioFormError.subMaterialCode.show ||
      this.materialRatioFormError.ratio.show
    ) {
      this.approveMaterialsVerify = false;
    } else {
      this.approveMaterialsVerify = true;
    }
  }

  approvedProduct(): void {
    this.updateProduct();
    if (
      !this.product ||
      !this.product.productCode ||
      this.product.productCode.length < 1
    ) {
      this.productFormError.productCode.show = true;
      this.productFormError.productCode.msg = "请输入产品编码";
    } else {
      this.productFormError.productCode.show = false;
      this.productFormError.productCode.msg = "";
    }
    if (
      !this.product ||
      !this.product.productName ||
      this.product.productName.length < 1
    ) {
      this.productFormError.productName.show = true;
      this.productFormError.productName.msg = "请输入产品名称";
    } else {
      this.productFormError.productName.show = false;
      this.productFormError.productName.msg = "";
    }
    if (!this.materialRatioes || this.materialRatioes.length < 1) {
      this.productFormError.materialRatioes.show = true;
      this.productFormError.materialRatioes.msg = "请添加物料信息";
    } else {
      this.productFormError.materialRatioes.show = false;
      this.productFormError.materialRatioes.msg = "";
    }
    if (
      this.productFormError.productCode.show ||
      this.productFormError.productName.show ||
      this.productFormError.materialRatioes.show
    ) {
      this.approveProductVerify = false;
    } else {
      this.approveProductVerify = true;
    }
    // alert('productFormError.productName.show='+this.productFormError.productName.show);
    // this.ref.detectChanges();
  }

  updateMaterialRatio() {
    const PRODUCT_VALUE = this.productForm.value;
    const MATERIAL_VALUE = this.materialRatioForm.value;
    this.materialRatio.productCode = PRODUCT_VALUE.productCode;
    this.product.markup = PRODUCT_VALUE.markup;
    this.materialRatio.subMaterialCode = MATERIAL_VALUE.subMaterialCode;
    this.materialRatio.subMaterialRatio = MATERIAL_VALUE.ratio;
  }

  updateProduct() {
    const PRODUCT_VALUE = this.productForm.value;
    console.log('PRODUCT_VALUE');
    console.log(PRODUCT_VALUE);
    if (!this.isUpdate) {
      //如果是更新，禁止修改userCode，保持userCode不变，不要从表单里取值
      this.product.productCode = PRODUCT_VALUE.productCode;
      this.product.markup = PRODUCT_VALUE.markup;
    }
    this.product.productName = PRODUCT_VALUE.productName;
  }

  saveProduct() {
    let sucessful = false;
    this.approvedProduct();
    if (this.approveProductVerify) {
      if (!this.isUpdate) {
        const param = new ProductFamilyElement(
          this.product,
          this.materialRatioes
        );
        this.productService
          .addProduct(param)
          .subscribe((data: ProductFamilyElement) => {
            let id = data.product.id;
            if (id > 0) {
              this.product.id = id;
              sucessful = true;
              this.openSnackBar(sucessful);
            } else {
              this.openSnackBar(sucessful);
            }
          });
      } else {
        const param = new ProductFamilyElement(
          this.product,
          this.materialRatioes
        );
        this.productService
          .updateProduct(param)
          .subscribe((data: ProductFamilyElement) => {
            let id = data.product.id;
            if (id > 0) {
              this.product.id = id;
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


  // 删除物料配比表
  detetMaterialRatio(ratio: MaterialRatioElement) {
    if (ratio && ratio.id > 0) {
      let sucessful = false;
      this.productService.deleteMaterialRatio(ratio.id).subscribe(
        (record: number) => {
          if (record > 0) {
            sucessful = true;
            this.getParts(this.product.productCode);
            this.refreshParts();
          }
          this.openSnackBar(sucessful);
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }

  addMaterialRatio() {
    this.approvedMaterialRatio();
    if (this.approveMaterialsVerify) {
      let subMaterialCode = this.materialRatio.subMaterialCode;
      let targetSecondMaterial = this.secondMaterials.filter(function (item) {
        return item.subMaterialCode == subMaterialCode;
      })[0];
      let targetFirstMaterial = this.firstMaterials.filter(function (item) {
        return item.materialCode == targetSecondMaterial.materialCode;
      })[0];
      this.materialRatioes.push(
        new MaterialRatioElement(
          this.materialRatio.id,
          this.materialRatio.productCode,
          targetFirstMaterial.materialCode,
          targetFirstMaterial.materialName,
          this.materialRatio.subMaterialCode,
          targetSecondMaterial.subMaterialName,
          this.materialRatio.subMaterialRatio,
          this.materialRatio.status,
          this.materialRatio.enteredBy
        )
      );
      this.refreshParts();
      // this.ref.detectChanges();
    } else {
      // this.ref.detectChanges();
      this.snackBar.open("物料", "添加失败", { duration: 3000 });
    }
  }

  remove(data: ProductElement): void {
    // data.isEdit = false;
    //找到要移除的元素下标，把0换成目标元素的下表
    // this.listProducts.splice(0, 1);
    // this.dataSource = new MatTableDataSource<ProductElement>(this.listProducts);
    // this.initMatTable();
    // this.ref.detectChanges();
    // this.ref.tick();
  }

  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open("产品", "保存成功", { duration: 3000 });
      this.dialogRef.close(this.product);
    } else {
      this.snackBar.open("产品", "保存失败", { duration: 3000 });
    }
  }
}
