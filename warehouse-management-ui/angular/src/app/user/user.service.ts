import { Injectable } from "@angular/core";

import { HttpService } from "../common/http.service";

import { Observable, of } from "rxjs";
import { catchError, tap } from "rxjs/operators";

import { UserElement } from "./user";

import { FormBuilder } from "@angular/forms";

import { API } from "../common/api";

@Injectable({
  providedIn: "root"
})
export class UserService {
  constructor(private http: HttpService, private fb: FormBuilder) { }

  //   添加用户
  addUser(user: UserElement) {
    return this.http.post(API.addUser, user);
  }
   //  更新用户
  updateUser(user: UserElement) {
    return this.http.post(API.updateUser, user);
  }
  // 删除用户
  deletUser(id: number) {
    return this.http.delete(API.deletUser + "/" + id);
  }
  // 获取用户
  getUsers(): Observable<any> {
    return this.http.get(API.listUser).pipe(
      tap(_ => this.log('获取一条数据')),
      catchError(this.handleError<UserElement[]>('getUsers', []))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
  private log(str: string) {
    console.log(str);
  }
}
