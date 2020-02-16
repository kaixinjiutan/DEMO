export class Error {
  show: boolean;
  msg: string;

  constructor(show: boolean, msg: string) {
    this.show = show;
    this.msg = msg;
  }
}

export class CustomerFormError {
  customerCode: Error;
  customerName: Error;

  constructor(customerCode: Error, customerName: Error) {
    this.customerCode = customerCode;
    this.customerName = customerName;
  }
}

export class CustomerElement {
  id: number;
  customerCode: string;
  customerName: string;
  status: number;

  constructor(
    id: number,
    customerCode: string,
    customerName: string,
    status: number
  ) {
    this.id = id;
    this.customerCode = customerCode;
    this.customerName = customerName;
    this.status = status;
  }
}
