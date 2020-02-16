package com.wanda.warehouse.domain;

public class CustomerVo {

	private long id;
	private String customerCode;
	private String customerName;
	private int status;
	private String enteredBy;

	public CustomerVo(long id, String customerCode, String customerName, int status, String enteredBy) {
		super();
		this.id = id;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.status = status;
		this.enteredBy = enteredBy;
	}

	@Override
	public String toString() {
		return "CustomerVo [id=" + id + ", customerCode=" + customerCode + ", customerName=" + customerName
				+ ", status=" + status + ", enteredBy=" + enteredBy + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
