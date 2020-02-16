package com.wanda.warehouse.domain;

public class OrderProductVo {

	private long id;
	private String orderNo;
	private String productCode;
	private int quantity;
	private int status;
	private String enteredBy;

	public OrderProductVo(long id, String orderNo, String productCode, int quantity, int status, String enteredBy) {
		super();
		this.id = id;
		this.orderNo = orderNo;
		this.productCode = productCode;
		this.quantity = quantity;
		this.status = status;
		this.enteredBy = enteredBy;
	}

	@Override
	public String toString() {
		return "OrderProductVo [id=" + id + ", orderNo=" + orderNo + ", productCode=" + productCode + ", quantity="
				+ quantity + ", status=" + status + ", enteredBy=" + enteredBy + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

}
