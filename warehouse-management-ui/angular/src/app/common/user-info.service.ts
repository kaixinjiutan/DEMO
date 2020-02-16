import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserInfoService {

  public userInfoKey = 'userCode';

  constructor() { }

  saveUserInfo(userCode:string){
    window.localStorage.setItem(this.userInfoKey,userCode);
  }

  getUserInfo(){
    return window.localStorage.getItem(this.userInfoKey);
  }

  removeUserInfo(){
    window.localStorage.removeItem(this.userInfoKey);
  }

  getToken(){
    try{
      let userCode = this.getUserInfo();
      return userCode;
    } catch (err) {
      alert('err='+err);
      return '';
    }
  }
}
