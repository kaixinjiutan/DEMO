const USER = "user";
const PRODUCT = "product";
const PRODUCTSUBMATERIALRATIO = "productSubMaterialRatio";
const PART = "part";
const SAMPLE = "Sample";
const CUSTOMER = "customer";
const MATERIAL = "material";
const SUBMATERIAL = "subMaterial";
const ORDER = "order";
const ORDERPRODUCT = "orderProduct";

export const API = {
  userLogin: `${USER}/login`,
  addUser: `${USER}/add`,
  addCustomer: `${CUSTOMER}/add`,
  updateUser: `${USER}/update`,
  deletUser: `${USER}/delete`,
  deletCustomer: `${CUSTOMER}/delete`,
  listUser: `${USER}/list`,
  listCustomer: `${CUSTOMER}/list`,
  updateCustomer: `${CUSTOMER}/update`,
  listProduct: `${PRODUCT}/list`,
  addProduct: `${PRODUCT}/add`,
  updateProduct: `${PRODUCT}/update`,
  deleteProduct: `${PRODUCT}/delete`,
  listMaterialsOfProduct: `${PRODUCTSUBMATERIALRATIO}/list`,
  getMaterialsOfProduct: `${PRODUCTSUBMATERIALRATIO}/list`,
  deleteMaterialRatio: `${PRODUCTSUBMATERIALRATIO}/delete`,
  listMaterial: `${MATERIAL}/list`,
  addMaterial: `${MATERIAL}/add`,
  deleteMaterial: `${MATERIAL}/delete`,
  updateMaterial: `${MATERIAL}/update`,
  deleteSubMaterial: `${SUBMATERIAL}/delete`,
  listSubMaterial: `${SUBMATERIAL}/list`,
  addSubMaterial: `${SUBMATERIAL}/add`,
  updateSubMaterial: `${SUBMATERIAL}/update`,
  listOrder: `${ORDER}/list`,
  listProductsOfOrder: `${ORDER}/order/products`,
  addOrder: `${ORDER}/add`,
  updateOrder: `${ORDER}/update`,
  deleteOrder: `${ORDER}/delete`
};
