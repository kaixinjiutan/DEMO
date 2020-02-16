import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { UserListComponent } from "./user-list/user-list.component";
import { UserAddComponent } from "./user-add/user-add.component";
import { FormsModule } from "@angular/forms";
import { SharedModule } from "../shared/shared.module";
import { DemoMaterialModule } from "../material-module";

@NgModule({
  declarations: [UserListComponent, UserAddComponent],
  entryComponents: [UserAddComponent],
  imports: [CommonModule, FormsModule, DemoMaterialModule, SharedModule]
})
export class UserModule {}
