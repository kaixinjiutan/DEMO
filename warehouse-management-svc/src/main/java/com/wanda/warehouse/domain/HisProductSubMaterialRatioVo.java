package com.wanda.warehouse.domain;

public class HisProductSubMaterialRatioVo {

	private long id;
	private String productCode;
	private String subMaterialCode;
	private int subMaterialRatio;
	private String enteredBy;
	private int status;

	public HisProductSubMaterialRatioVo(long id, String productCode, String subMaterialCode,
			int subMaterialRatio, String enteredBy, int status) {
		super();
		this.id = id;
		this.productCode = productCode;
		this.subMaterialCode = subMaterialCode;
		this.subMaterialRatio = subMaterialRatio;
		this.enteredBy = enteredBy;
		this.status = status;
	}

	@Override
	public String toString() {
		return "HisProductSubMaterialRatioVo [id=" + id + ", productCode=" + productCode + ", productName="
				+ ", subMaterialCode=" + subMaterialCode + ", subMaterialRatio=" + subMaterialRatio
				+ ", enteredBy=" + enteredBy + ", status=" + status + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSubMaterialCode() {
		return subMaterialCode;
	}

	public void setSubMaterialCode(String subMaterialCode) {
		this.subMaterialCode = subMaterialCode;
	}

	public int getSubMaterialRatio() {
		return subMaterialRatio;
	}

	public void setSubMaterialRatio(int subMaterialRatio) {
		this.subMaterialRatio = subMaterialRatio;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
