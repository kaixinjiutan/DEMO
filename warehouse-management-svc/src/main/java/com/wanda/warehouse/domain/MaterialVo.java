package com.wanda.warehouse.domain;

public class MaterialVo {

	// 物料表除[enteredBy,enteredDate,lastUpdateBy,lastUpdateDate]以外的属性
	private long id;
	private String materialCode;
	private String materialName;
	private int status;
	private String enteredBy;

	public MaterialVo(long id, String materialCode, String materialName, int status, String enteredBy) {
		super();
		this.id = id;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.status = status;
		this.enteredBy = enteredBy;
	}

	@Override
	public String toString() {
		return "MaterialVo [id=" + id + ", materialCode=" + materialCode + ", materialName=" + materialName
				+ ", status=" + status + ", enteredBy=" + enteredBy + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
