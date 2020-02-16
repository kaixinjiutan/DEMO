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
@Table(name = "OrderProduct", indexes = {@Index(name = "u_order_product",  columnList="order_no,product_code", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class OrderProduct {

	@Id
	@SequenceGenerator(name="seq_order_product",allocationSize=1,initialValue=1, sequenceName="seq_order_product_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_order_product") 
	
	@Column(name = "id")
	private long id;
	@Column(name = "order_no")
	private String OrderNo;
	@Column(name = "product_code")
	private String ProductCode;
	@Column(name = "status")
	private int Status;
	@Column(name = "quantity")
	private int Quantity;
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
	public String getOrderNo() {
		return OrderNo;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public int getStatus() {
		return Status;
	}
	public int getQuantity() {
		return Quantity;
	}
	public String getEnteredBy() {
		return EnteredBy;
	}
	public Date getEnteredDate() {
		return EnteredDate;
	}
	public String getLastUpdateBy() {
		return LastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return LastUpdateDate;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public void setEnteredBy(String enteredBy) {
		EnteredBy = enteredBy;
	}
	public void setEnteredDate(Date enteredDate) {
		EnteredDate = enteredDate;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		LastUpdateBy = lastUpdateBy;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		LastUpdateDate = lastUpdateDate;
	}
	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", OrderNo=" + OrderNo + ", ProductCode=" + ProductCode + ", Status=" + Status
				+ ", Quantity=" + Quantity + ", EnteredBy=" + EnteredBy + ", EnteredDate=" + EnteredDate
				+ ", LastUpdateBy=" + LastUpdateBy + ", LastUpdateDate=" + LastUpdateDate + "]";
	}
}
