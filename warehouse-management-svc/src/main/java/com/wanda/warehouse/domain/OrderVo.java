package com.wanda.warehouse.domain;

public class OrderVo {

	private long id;
	private String orderNo;
	private String customerCode;
	private String customerName;
	private String startDate;
	private String endDate;
	private int status;
	private String enteredBy;

	public OrderVo(long id, String orderNo, String customerCode, String customerName, String startDate, String endDate, int status,
			String enteredBy) {
		super();
		this.id = id;
		this.orderNo = orderNo;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.enteredBy = enteredBy;
	}

	@Override
	public String toString() {
		return "OrderVo [id=" + id + ", orderNo=" + orderNo + ", customerCode=" + customerCode + ", customerName=" + customerName + ", startDate="
				+ startDate + ", endDate=" + endDate + ", status=" + status + ", enteredBy=" + enteredBy + "]";
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
