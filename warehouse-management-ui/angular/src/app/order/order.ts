export class Error {
  show: boolean;
  msg: string;

  constructor(show: boolean, msg: string) {
    this.show = show;
    this.msg = msg;
  }
}

export class OrderFormError {
  orderNo: Error;
  customerCode: Error;
  startDate: Error;
  endDate: Error;
  orderProducts: Error;

  constructor(orderNo: Error, customerCode: Error, startDate: Error, endDate: Error, orderProducts: Error) {
    this.orderNo = orderNo;
    this.customerCode = customerCode;
    this.startDate = startDate;
    this.endDate = endDate;
    this.orderProducts = orderProducts;
  }
}

export class OrderProductFormError {
  productCode: Error;
  productQuantity: Error;
  constructor(productCode: Error, productQuantity: Error) {
    this.productCode = productCode;
    this.productQuantity = productQuantity;
  }
}

export class OrderInfoElement {
  id: number;
  orderNo: string;
  customerCode: string;
  customerName: string;
  startDate: string;
  endDate: string;
  status: number;
  enteredBy: string;

  constructor(
    id: number,
    orderNo: string,
    customerCode: string,
    customerName: string,
    startDate: string,
    endDate: string,
    status: number,
    enteredBy: string
  ) {
    this.id = id;
    this.orderNo = orderNo;
    this.customerCode = customerCode;
    this.customerName = customerName;
    this.endDate = endDate;
    this.enteredBy = enteredBy;
    this.startDate = startDate;
    this.status = status;
  }
}

export class OrderProductElement {
  id: number;
  orderNo: string;
  productCode: string;
  productName: string;
  quantity: number;
  status: number;
  enteredBy: string;


  constructor(
    id: number,
    orderNo: string,
    productCode: string,
    productName: string,
    quantity: number,
    status: number,
    enteredBy: string
  ) {
    this.enteredBy = enteredBy;
    this.id = id;
    this.orderNo = orderNo;
    this.productCode = productCode;
    this.productName = productName;
    this.quantity = quantity;
    this.status = status;
  }
}

export class OrderFamilyElement {
  order: OrderInfoElement;
  orderProducts: OrderProductElement[];
  insufficientSubMaterials: string[];
  sufficientSubMaterials: string[];
  constructor(
    order: OrderInfoElement,
    orderProducts: OrderProductElement[],
    insufficientSubMaterials: string[],
    sufficientSubMaterials: string[]
  ) {
    this.order = order;
    this.orderProducts = orderProducts;
    this.insufficientSubMaterials = insufficientSubMaterials;
    this.sufficientSubMaterials = sufficientSubMaterials;
  }
}
