import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";

import { MAT_LABEL_GLOBAL_OPTIONS } from "@angular/material";
import { FormsModule } from "@angular/forms";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { PageNotFoundComponent } from "./page-not-found/page-not-found.component";

import { HomeModule } from "./home/home.module";
import { OrderModule } from "./order/order.module";
import { ProductModule } from "./product/product.module";
// import { PartsModule } from './parts/parts.module';
import { MaterialModule } from "./material/material.module";
import { CustomerModule } from "./customer/customer.module";
import { UserModule } from "./user/user.module";
import { AuthModule } from "./auth/auth.module";
import { DemoMaterialModule } from "./material-module";

@NgModule({
  declarations: [AppComponent, PageNotFoundComponent],
  imports: [
    DemoMaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    HomeModule,
    OrderModule,
    ProductModule,
    // PartsModule,
    MaterialModule,
    CustomerModule,
    UserModule,
    AuthModule,
    AppRoutingModule
  ],
  providers: [
    { provide: MAT_LABEL_GLOBAL_OPTIONS, useValue: { float: "always" } }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
