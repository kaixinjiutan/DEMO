import { OrderInfoElement, OrderProductElement } from "./order";

// export const TABLE_DATA: PeriodicElement[] = [
export const OEDER_DATA = [
  new OrderInfoElement(
    1,
    "OXX01",
    "CXX01",
    "得力",
    "2019-11-11",
    "2019-11-10",
    0,
    "张译文"
  )
];

export const OEDER_PRODUCT_DATA = [
  new OrderProductElement(1, "OXX01","", "6580", 200, 0, "张译文")
];

export const ORDER_FAMILY_ELEMENT = [
  {
    // [],
    // new OrderElement(1, "OXX01", "CXX01", "2019-11-10", "张译文", "2019-11-11", 0),
    //  new OrderProductElement("张译文", 1, "OXX01", "6580", 200, 0),
    // []
  }
];
