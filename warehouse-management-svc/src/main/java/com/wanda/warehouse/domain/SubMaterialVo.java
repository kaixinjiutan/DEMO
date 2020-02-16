package com.wanda.warehouse.domain;

public class SubMaterialVo {

	// 子物料表除[enteredBy,enteredDate,lastUpdateBy,lastUpdateDate]以外的属性
	private long id;
	private String subMaterialCode;
	private String subMaterialName;
	private String materialCode;
	private String materialName;
	private int stock;
	private String unit;
	private double cost;
	private int status;
	private String enteredBy;


	public SubMaterialVo(long id, String subMaterialCode, String subMaterialName, String materialCode,String materialName, int stock,
			String unit, double cost, int status, String enteredBy) {
		super();
		this.id = id;
		this.subMaterialCode = subMaterialCode;
		this.subMaterialName = subMaterialName;
		this.materialCode = materialCode;
		this.materialName =  materialName;
		this.stock = stock;
		this.unit = unit;
		this.cost = cost;
		this.status = status;
		this.enteredBy = enteredBy;
	}


	public long getId() {
		return id;
	}


	public String getSubMaterialCode() {
		return subMaterialCode;
	}


	public String getSubMaterialName() {
		return subMaterialName;
	}


	public String getMaterialCode() {
		return materialCode;
	}


	public String getMaterialName() {
		return materialName;
	}


	public int getStock() {
		return stock;
	}


	public String getUnit() {
		return unit;
	}


	public double getCost() {
		return cost;
	}


	public int getStatus() {
		return status;
	}


	public String getEnteredBy() {
		return enteredBy;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setSubMaterialCode(String subMaterialCode) {
		this.subMaterialCode = subMaterialCode;
	}


	public void setSubMaterialName(String subMaterialName) {
		this.subMaterialName = subMaterialName;
	}


	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}


	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}


	@Override
	public String toString() {
		return "SubMaterialVo [id=" + id + ", subMaterialCode=" + subMaterialCode + ", subMaterialName="
				+ subMaterialName + ", materialCode=" + materialCode + ", materialName=" + materialName + ", stock="
				+ stock + ", unit=" + unit + ", cost=" + cost + ", status=" + status + ", enteredBy=" + enteredBy + "]";
	}

}
