import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MMaterialModule} from './material-module';
import {SharedModule} from '../shared/shared.module';

import { HomeRoutingModule } from './home-routing.module';
import { FrameComponent } from './frame/frame.component';


@NgModule({
  declarations: [FrameComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    MMaterialModule,
    SharedModule
  ],
  exports:[
    CommonModule,
    HomeRoutingModule,
    MMaterialModule,
    SharedModule
  ],
  bootstrap: [FrameComponent],
})
export class HomeModule { }
