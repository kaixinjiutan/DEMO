import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { FrameComponent } from "./frame/frame.component";
import { OrderListComponent } from "../order/order-list/order-list.component";
import { ProductListComponent } from "../product/product-list/product-list.component";
// import { PartsHomeComponent } from '../parts/parts-home/parts-home.component';
import { MaterialListComponent } from "../material/material-list/material-list.component";
import { CustomerListComponent } from "../customer/customer-list/customer-list.component";
import { UserListComponent } from "../user/user-list/user-list.component";

import { AuthGuard } from "../auth/auth.guard";

const routes: Routes = [
  {
    path: "frame",
    component: FrameComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: "order",
        component: OrderListComponent
      },
      {
        path: "product",
        component: ProductListComponent
      },
      // {
      //   path: 'parts',
      //   component: PartsHomeComponent
      // },
      {
        path: "material",
        component: MaterialListComponent
      },
      {
        path: "customer",
        component: CustomerListComponent
      },
      {
        path: "user",
        component: UserListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {}
