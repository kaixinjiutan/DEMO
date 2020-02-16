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
@Table(name = "Customer", indexes = {@Index(name = "u_customer_code",  columnList="customer_code", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class Customer {
	@Id
	@SequenceGenerator(name="seq_customer",allocationSize=1,initialValue=1, sequenceName="seq_customer_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_customer") 
	@Column(name = "id")
	private long id;
	@Column(name = "customer_code")
	private String customerCode;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "status")
	private int status;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public int getStatus() {
		return status;
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
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setStatus(int status) {
		this.status = status;
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
		return "Customer [id=" + id + ", customerCode=" + customerCode + ", customerName=" + customerName + ", status="
				+ status + ", EnteredBy=" + EnteredBy + ", EnteredDate=" + EnteredDate + ", LastUpdateBy="
				+ LastUpdateBy + ", LastUpdateDate=" + LastUpdateDate + "]";
	}
}
