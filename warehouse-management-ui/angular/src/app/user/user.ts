export class Error {
  show: boolean;
  msg: string;

  constructor(show: boolean, msg: string) {
    this.show = show;
    this.msg = msg;
  }
}

export class UserFormError {
  userCode: Error;
  password: Error;

  constructor(userCode: Error, password: Error) {
    this.userCode = userCode;
    this.password = password;
  }
}

export class UserElement {
  id: number;
  userCode: string;
  password: string;
  status: number;

  constructor(id: number, userCode: string, password: string, status: number) {
    this.id = id;
    this.userCode = userCode;
    this.password = password;
    this.status = status;
  }
}
