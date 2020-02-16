package com.wanda.warehouse.domain;

public class SubMaterialStockVo {
	private long id;
	private String subMaterialCode;
	private int stockModifyStatus;
	private String orderNo;
//	private String productCode;
	private String purchaser;
	private int quantity;
	private int status;
	private String enteredBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubMaterialCode() {
		return subMaterialCode;
	}

	public void setSubMaterialCode(String subMaterialCode) {
		this.subMaterialCode = subMaterialCode;
	}

	public int getStockModifyStatus() {
		return stockModifyStatus;
	}

	public void setStockModifyStatus(int stockModifyStatus) {
		this.stockModifyStatus = stockModifyStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		return "SubMaterialStockVo [id=" + id + ", subMaterialCode=" + subMaterialCode + ", stockModifyStatus="
				+ stockModifyStatus + ", orderNo=" + orderNo + ", purchaser=" + purchaser + ", quantity=" + quantity
				+ ", status=" + status + ", enteredBy=" + enteredBy + "]";
	}

	public SubMaterialStockVo(long id, String subMaterialCode, int stockModifyStatus, String orderNo, String purchaser,
			int quantity, int status, String enteredBy) {
		super();
		this.id = id;
		this.subMaterialCode = subMaterialCode;
		this.stockModifyStatus = stockModifyStatus;
		this.orderNo = orderNo;
		this.purchaser = purchaser;
		this.quantity = quantity;
		this.status = status;
		this.enteredBy = enteredBy;
	}

}
