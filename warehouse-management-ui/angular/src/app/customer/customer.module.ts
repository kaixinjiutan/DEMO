import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { CustomerListComponent } from "./customer-list/customer-list.component";
import { CustomerAddComponent } from "./customer-add/customer-add.component";

import { FormsModule } from "@angular/forms";
import { SharedModule } from "../shared/shared.module";
import { DemoMaterialModule } from "../material-module";
@NgModule({
  declarations: [CustomerListComponent, CustomerAddComponent],
  entryComponents: [CustomerAddComponent],
  imports: [CommonModule, FormsModule, DemoMaterialModule, SharedModule]
})
export class CustomerModule {}
