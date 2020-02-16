import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { OrderListComponent } from "./order-list/order-list.component";
import { OrderAddComponent } from "./order-add/order-add.component";
import { DemoMaterialModule } from "../material-module";
import { SharedModule } from "../shared/shared.module";

import { FormsModule } from "@angular/forms";
import { ReactiveFormsModule } from "@angular/forms";
@NgModule({
  //   declarations: [
  //     OrderListComponent,
  //     OrderAddComponent,
  //     OrderProductListComponent
  //   ],
  //   entryComponents: [OrderAddComponent],
  //   imports: [CommonModule, DemoMaterialModule, SharedModule],
  //   // providers: [OrderListComponent]

  declarations: [OrderListComponent, OrderAddComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    DemoMaterialModule
  ],
  // providers: [ProductListComponent,ProductAddComponent],
  entryComponents: [OrderAddComponent]
})
export class OrderModule {}
