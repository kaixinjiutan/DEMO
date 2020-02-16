import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { tap, delay } from 'rxjs/operators';
import { Router } from '@angular/router';
import { HttpService } from '../common/http.service';
import { UserInfoService } from '../common/user-info.service';
import { API } from '../common/api';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLogged: boolean;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(
    private userInfoService: UserInfoService,
    private http: HttpService,
    private router: Router,
  ) {
    this.isLogged = this.userInfoService.getToken() ? true : false;
  }

  logOn(params): Observable<object> {
    return this.http.post(API.userLogin, params, true);
  }
  logout(): void {
    // Remove token from local storage
    this.userInfoService.removeUserInfo();
    // Redirect to login
    this.router.navigate(['/login']);
  }
}
