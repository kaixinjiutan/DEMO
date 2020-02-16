export class Error {
  show: boolean;
  msg: string;

  constructor(show: boolean, msg: string) {
    this.show = show;
    this.msg = msg;
  }
}

export class MaterialFormError {
  materialCode: Error;
  materialName: Error;
  constructor(materialCode: Error, materialName: Error) {
    this.materialCode = materialCode;
    this.materialName = materialName;
  }
}
export class SubMaterialFormError {
  materialCode: Error;
  // materialName: Error;
  subMaterialCode: Error;
  subMaterialName: Error;
  stock: Error;
  unit: Error;
  cost: Error;
  constructor(
    materialCode: Error,
    // materialName: Error,
    subMaterialCode: Error,
    subMaterialName: Error,
    stock: Error,
    unit: Error,
    cost: Error
  ) {
    this.materialCode = materialCode;
    // this.materialName = materialName;
    this.subMaterialCode = subMaterialCode;
    this.subMaterialName = subMaterialName;
    this.stock = stock;
    this.unit = unit;
    this.cost = cost;
  }
}
export class MaterialElement {
  id: number;
  enteredBy: string;
  materialCode: string;
  materialName: string;
  status: number;

  constructor(
    id: number,
    enteredBy: string,
    materialCode: string,
    materialName: string,
    status: number
  ) {
    this.id = id;
    this.enteredBy = enteredBy;
    this.materialCode = materialCode;
    this.materialName = materialName;
    this.status = status;
  }
}

export class SubMaterialElement {
  id: number;
  enteredBy: string;
  materialCode: string;
  materialName: string;
  subMaterialCode: string;
  subMaterialName: string;
  cost: number;
  stock: number;
  unit: string;
  status: number;
  constructor(
    id: number,
    enteredBy: string,
    materialCode: string,
    materialName: string,
    subMaterialCode: string,
    subMaterialName: string,
    cost: number,
    stock: number,
    unit: string,
    status: number,
  ) {
    this.id = id;
    this.enteredBy = enteredBy;
    this.materialCode = materialCode;
    this.materialName = materialName;
    this.subMaterialCode = subMaterialCode;
    this.subMaterialName = subMaterialName;
    this.cost = cost;
    this.stock = stock;
    this.unit = unit;
    this.status = status;

  }
}
