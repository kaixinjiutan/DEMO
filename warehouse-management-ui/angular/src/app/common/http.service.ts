import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { UserInfoService } from "./user-info.service";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: "root"
})
export class HttpService {
  configUrl = environment.url;

  constructor(private http: HttpClient, private userService: UserInfoService) {}

  get(url: string, noHeader = false) {
    if (noHeader) {
      return this.http.get(`${this.configUrl}/${url}`);
    } else {
      return this.http.get(`${this.configUrl}/${url}`, {
        headers: new HttpHeaders()
          .set("Content-type", "application/json")
          .set("userCode", this.userService.getToken())
      });
    }
  }
  post(url: string, params = {}, noHeader = false) {
    if (noHeader) {
      return this.http.post(`${this.configUrl}/${url}`, params);
    } else {
      return this.http.post(`${this.configUrl}/${url}`, params, {
        headers: new HttpHeaders()
          .set("Content-type", "application/json")
          .set("userCode", this.userService.getToken())
      });
    }
  }
  delete(url, noHeader = false) {
    if (noHeader) {
      return this.http.delete(`${this.configUrl}/${url}`);
    } else {
      return this.http.delete(`${this.configUrl}/${url}`, {
        headers: new HttpHeaders()
          .set("Content-type", "application/json")
          .set("userCode", this.userService.getToken())
      });
    }
  }
}
