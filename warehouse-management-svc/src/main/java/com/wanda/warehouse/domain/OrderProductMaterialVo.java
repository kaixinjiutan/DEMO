package com.wanda.warehouse.domain;

public class OrderProductMaterialVo {

	private long productId;
	private String productCode;
	private long subMaterialId;
	private String subMaterialCode;
	private int stock;
	private int subMaterialQuantity;
	private int status;

	public OrderProductMaterialVo(long productId, String productCode, long subMaterialId, String subMaterialCode,
			int stock, int subMaterialQuantity, int status) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.subMaterialId = subMaterialId;
		this.subMaterialCode = subMaterialCode;
		this.stock = stock;
		this.subMaterialQuantity = subMaterialQuantity;
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderProductMaterialVo [productId=" + productId + ", productCode=" + productCode + ", subMaterialId="
				+ subMaterialId + ", subMaterialCode=" + subMaterialCode + ", stock=" + stock + ", subMaterialQuantity="
				+ subMaterialQuantity + ", status=" + status + "]";
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public long getSubMaterialId() {
		return subMaterialId;
	}

	public void setSubMaterialId(long subMaterialId) {
		this.subMaterialId = subMaterialId;
	}

	public String getSubMaterialCode() {
		return subMaterialCode;
	}

	public void setSubMaterialCode(String subMaterialCode) {
		this.subMaterialCode = subMaterialCode;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSubMaterialQuantity() {
		return subMaterialQuantity;
	}

	public void setSubMaterialQuantity(int subMaterialQuantity) {
		this.subMaterialQuantity = subMaterialQuantity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
