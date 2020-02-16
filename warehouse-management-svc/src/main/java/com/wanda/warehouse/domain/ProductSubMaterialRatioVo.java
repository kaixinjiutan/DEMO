package com.wanda.warehouse.domain;

public class ProductSubMaterialRatioVo {

	private long id;
	private String productCode;
	private String materialCode;
	private String materialName;
	private String subMaterialCode;
	private String subMaterialName;
	private int subMaterialRatio;
	private int status;
	private String enteredBy;

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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getSubMaterialCode() {
		return subMaterialCode;
	}

	public void setSubMaterialCode(String subMaterialCode) {
		this.subMaterialCode = subMaterialCode;
	}

	public String getSubMaterialName() {
		return subMaterialName;
	}

	public void setSubMaterialName(String subMaterialName) {
		this.subMaterialName = subMaterialName;
	}

	public int getSubMaterialRatio() {
		return subMaterialRatio;
	}

	public void setSubMaterialRatio(int subMaterialRatio) {
		this.subMaterialRatio = subMaterialRatio;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	@Override
	public String toString() {
		return "ProductSubMaterialRatioVo [id=" + id + ", productCode=" + productCode + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", subMaterialCode=" + subMaterialCode + ", subMaterialName="
				+ subMaterialName + ", subMaterialRatio=" + subMaterialRatio + ", status=" + status + ", enteredBy="
				+ enteredBy + "]";
	}
	
	public ProductSubMaterialRatioVo(long id, String productCode
			, String materialCode, String materialName
			, String subMaterialCode, String subMaterialName
			, int subMaterialRatio, int status, String enteredBy) {
		super();
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
