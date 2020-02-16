package com.wanda.warehouse.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "order_info", indexes = {@Index(name = "u_order_no",  columnList="order_no", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引

public class Order {

	@Id
	@SequenceGenerator(name = "seq_order", allocationSize = 1, initialValue = 1, sequenceName = "seq_order_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order")
	@Column(name = "id")
	private long id;
	@Column(name = "order_no")
	private String OrderNo;
	@Column(name = "customer_code")
	private String CustomerCode;
	@Column(name = "price")
	private double Price;
	@Column(name = "status")
	private int Status;
	@Column(name = "start_date")
	private String StartDate;
	@Column(name = "end_date")
	private String EndDate;
	@Column(name = "entered_by")
	private String EnteredBy;
	@Column(name = "entered_date")
	private Date EnteredDate;
	@Column(name = "last_update_by")
	private String LastUpdateBy;
	@Column(name = "last_update_date")
	private Date LastUpdateDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getCustomerCode() {
		return CustomerCode;
	}

	public void setCustomerCode(String customerCode) {
		CustomerCode = customerCode;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getEnteredBy() {
		return EnteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		EnteredBy = enteredBy;
	}

	public Date getEnteredDate() {
		return EnteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		EnteredDate = enteredDate;
	}

	public String getLastUpdateBy() {
		return LastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		LastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return LastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		LastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", OrderNo=" + OrderNo + ", CustomerCode=" + CustomerCode + ", Status=" + Status
				+ ", StartDate=" + StartDate + ", EndDate=" + EndDate + ", EnteredBy=" + EnteredBy + ", EnteredDate="
				+ EnteredDate + ", LastUpdateBy=" + LastUpdateBy + ", LastUpdateDate=" + LastUpdateDate + "]";
	}
}
