export class Error {
  show: boolean;
  msg: string;

  constructor(show: boolean, msg: string) {
    this.show = show;
    this.msg = msg;
  }
}

export class ProductFormError {
  productCode: Error;
  productName: Error;
  markup: Error;
  materialRatioes: Error;

  constructor(productCode: Error, productName: Error,markup: Error, materialRatioes: Error) {
    this.productCode = productCode;
    this.productName = productName;
    this.markup = markup;
    this.materialRatioes = materialRatioes;
  }
}

export class MaterialRatioFormError {
  materialCode: Error;
  subMaterialCode: Error;
  ratio: Error;
  constructor(materialCode: Error, subMaterialCode: Error, ratio: Error) {
    this.materialCode = materialCode;
    this.subMaterialCode = subMaterialCode;
    this.ratio = ratio;
  }
}

export class ProductElement {
  id: number;
  productCode: string;
  productName: string;
  markup: number;
  materialRatioes: number;
  status: number;

  constructor(
    id: number,
    productCode: string,
    productName: string,
    markup: number,
    materialRatioes: number,
    status: number
  ) {
    this.id = id;
    this.productCode = productCode;
    this.productName = productName;
    this.markup = markup;
    this.materialRatioes = materialRatioes;
    this.status = status;
  }
}

export class MaterialRatioElement {
  id: number;
  productCode: string;
  materialCode: string;
  materialName: string;
  subMaterialCode: string;
  subMaterialName: string;
  subMaterialRatio: number;
  status: number;
  enteredBy: string;
  constructor(id: number,productCode: string, materialCode: string, materialName: string, subMaterialCode: string, subMaterialName: string, subMaterialRatio: number,status:number,enteredBy:string) {
    this.id = id;
    this.productCode = productCode;
    this.materialCode = materialCode;
    this.materialName = materialName;
    this.subMaterialCode = subMaterialCode;
    this.subMaterialName = subMaterialName;
    this.subMaterialRatio = subMaterialRatio;
    this.status = status;
    this.enteredBy = enteredBy;
  }
}

export class ProductFamilyElement {
  product: ProductElement;
  materials: MaterialRatioElement[];
  constructor(
    product: ProductElement,
    materials: MaterialRatioElement[]
  ){
    this.product = product;
    this.materials = materials;
  }
}
