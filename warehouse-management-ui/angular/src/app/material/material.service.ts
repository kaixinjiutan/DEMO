import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { HttpService } from "../common/http.service";
import { MaterialElement, SubMaterialElement } from "./material";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators
} from "@angular/forms";
import { API } from "../common/api";

@Injectable({
  providedIn: "root"
})
export class MaterialService {
  constructor(private http: HttpService, private fb: FormBuilder) {}

  // 查询物料/material/list
  getMaterial(): Observable<any> {
    return this.http.get(API.listMaterial);
  }
  // 增加物料
  addMaterial(material: MaterialElement) {
    return this.http.post(API.addMaterial, material);
  }
  // 删除物料
  deleteMaterial(id: number) {
    return this.http.post(API.deleteMaterial + "/" + id);
  }
  // 修改物料
  updateMaterial(material: MaterialElement) {
    return this.http.post(API.updateMaterial, material);
  }

  // 删除二级物料/subMaterial/delete/{id}
  deleteSubMaterial(id: number) {
    return this.http.post(API.deleteSubMaterial + "/" + id);
  }
  // 获取二级物料
  getSubMaterial(): Observable<any> {
    return this.http.get(API.listSubMaterial);
  }
  // 添加二级物料
  addSubMaterial(subMaterial: SubMaterialElement) {
    return this.http.post(API.addSubMaterial, subMaterial);
  }
  // 修改二级物料
  updateSubMaterial(subMaterial: SubMaterialElement) {
    return this.http.post(API.updateSubMaterial, subMaterial);
  }
}
