import { Injectable } from "@angular/core";

import { HttpService } from "../common/http.service";

import { Observable, of } from "rxjs";
import { ProductFamilyElement, MaterialRatioElement } from "./product";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";

import { API } from "../common/api";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  constructor(private http: HttpService, private fb: FormBuilder) {}

  getMaterialsOfProduct(productCode: string): Observable<any> {
    return this.http.get(API.listMaterialsOfProduct + "/" + productCode);
  }

  getProducts(): Observable<any> {
    return this.http.get(API.listProduct);
  }

  addProduct(product: ProductFamilyElement) {
    return this.http.post(API.addProduct, product);
  }

  updateProduct(product: ProductFamilyElement) {
    return this.http.post(API.updateProduct, product);
  }

  deleteProduct(id: number) {
    return this.http.post(API.deleteProduct + "/" + id);
  }
  // 删除物料配比
  deleteMaterialRatio(id: number) {
    return this.http.post(API.deleteMaterialRatio + "/" + id);
    // /productSubMaterialRatio/delete/{id}

  }

  //获取物料配比列表
  getMaterialRatio(): Observable<any> {
    return this.http.get(API.getMaterialsOfProduct);
  }

}
