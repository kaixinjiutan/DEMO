import { Component, OnInit } from "@angular/core";

import { NgModule, ViewChild, ChangeDetectorRef } from "@angular/core";
import { MMaterialModule } from "../../home/material-module";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { SharedModule } from "../../shared/shared.module";
import { MaterialElement } from "../material";
import { FirstMateialComponent } from "../first-mateial/first-mateial.component";

import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MaterialService } from "../material.service";
import { MatTable, MatSort } from "@angular/material";
import { Router } from "@angular/router";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";

@Component({
  selector: "app-material-list",
  templateUrl: "./material-list.component.html",
  styleUrls: ["./material-list.component.css"]
})
@NgModule({
  declarations: [],
  imports: [MMaterialModule, SharedModule],
  bootstrap: [],
  exports: [MMaterialModule, SharedModule]
})
export class MaterialListComponent implements OnInit {
  listMaterial: MaterialElement[] = [];
  inputMaterial: MaterialElement[] = [];
  filteredInputMaterial: Observable<MaterialElement[]>;
  dataSource = new MatTableDataSource<MaterialElement>();
  inputMaterialForm: FormGroup = this.fb.group({
    inputMaterial: [""]
  });
  displayedColumns: string[] = [
    "id",
    "materialCode",
    "materialName",
    // "status",
    "operator"
  ];
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;
  constructor(
    private ref: ChangeDetectorRef,
    private dialog: MatDialog,
    private materialService: MaterialService,
    private router: Router,
    public fb: FormBuilder
  ) {}

  ngOnInit() {}
  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  // 获取物料列表
  getMaterial(): void {
    this.materialService.getMaterial().subscribe(
      (data: MaterialElement[]) => {
        this.listMaterial = data;
        this.inputMaterial = this.listMaterial;
        this.dataSource = new MatTableDataSource<MaterialElement>(
          this.listMaterial
        );
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
}
