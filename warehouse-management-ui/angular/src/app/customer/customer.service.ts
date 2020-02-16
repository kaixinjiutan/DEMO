import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { HttpService } from "../common/http.service";
import { API } from "../common/api";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";

import { CustomerElement } from "./customer";
@Injectable({
  providedIn: "root"
})
export class CustomerService {
  constructor(private http: HttpService, private fb: FormBuilder) {}

  showAdd = false;

  // 获取用户
  getCustomer(): Observable<any> {
    return this.http.get(API.listCustomer);
    // return this.http.get(API.listUser);
  }

  //   添加用户
  addCustomer(customer: CustomerElement) {
    return this.http.post(API.addCustomer, customer);
  }
  // 删除
  deleteCustomer(id: number) {
    return this.http.post(API.deletCustomer + "/" + id);
  }

  // 更新
  updateCustomer(customer: CustomerElement) {
    return this.http.post(API.updateCustomer, customer);
  }
}
