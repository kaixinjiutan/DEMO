import { Injectable } from "@angular/core";
import { HttpService } from "../common/http.service";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { Observable, of } from "rxjs";

import {
  OrderInfoElement,
  OrderProductElement,
  OrderFamilyElement
} from "./order";

import { API } from "../common/api";

@Injectable({
  providedIn: "root"
})
export class OrderService {
  constructor(private http: HttpService, private fb: FormBuilder) {}

  getProductsOfOrder(orderCode: string): Observable<any> {
    return this.http.get(API.listProductsOfOrder + "/" + orderCode);
  }

  getOrders(): Observable<any> {
    return this.http.get(API.listOrder);
  }

  addOrder(Order: OrderFamilyElement) {
    return this.http.post(API.addOrder, Order);
  }

  updateOrder(Order: OrderFamilyElement) {
    return this.http.post(API.updateOrder, Order);
  }

  deleteOrder(id: number) {
    return this.http.get(API.deleteOrder + "/" + id);
  }
}
