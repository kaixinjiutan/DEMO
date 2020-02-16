import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup,Validators } from '@angular/forms';

import { AuthService } from '../auth.service';
import { UserInfoService } from '../../common/user-info.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  message: string;

  loginFrom: FormGroup;

  constructor(
    private userInfoService: UserInfoService,
    public authService: AuthService,
    public router: Router,
    private fb: FormBuilder) {
  }

  ngOnInit() {
    this.loginFrom = this.fb.group({
      userCode: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  logOn() {
    const user = this.loginFrom.value;
    if (!user || !user.userCode || !user.password) {
      return;
    }
    const params = {
      userCode: user.userCode,
      password: user.password
    };
    this.authService.logOn(params)
      .subscribe((data: any) => {
        if (data && data.status == 10002) {
          const userCode:string = data.userCode ? data.userCode : '';
          if (userCode) {
            this.userInfoService.saveUserInfo(userCode);
            this.authService.isLogged = true;
            const redirect = this.authService.redirectUrl ? this.router.parseUrl(this.authService.redirectUrl) : '/frame/order';
            // Redirect the user
            this.router.navigateByUrl(redirect);
          } else {
            alert('用户不存在');
          }
        } else {
          alert('登录失败');
        }
      });
  }
}
