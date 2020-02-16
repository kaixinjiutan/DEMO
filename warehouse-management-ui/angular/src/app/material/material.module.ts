import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialListComponent } from "./material-list/material-list.component";
import { FirstMateialComponent } from "./first-mateial/first-mateial.component";

import { FormsModule } from "@angular/forms";
import { SharedModule } from "../shared/shared.module";
import { DemoMaterialModule } from "../material-module";
import { MaterialAddComponent } from "./material-add/material-add.component";
import { SecondMaterialComponent } from "./second-material/second-material.component";
import { SubMaterialAddComponent } from "./sub-material-add/sub-material-add.component";

@NgModule({
  declarations: [
    MaterialListComponent,
    FirstMateialComponent,
    MaterialAddComponent,
    SecondMaterialComponent,
    SubMaterialAddComponent
  ],
  entryComponents: [MaterialAddComponent, SubMaterialAddComponent],
  imports: [CommonModule, FormsModule, DemoMaterialModule, SharedModule]
})
export class MaterialModule {}
