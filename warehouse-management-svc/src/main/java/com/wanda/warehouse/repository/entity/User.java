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
@Table(name = "User", indexes = { @Index(name = "u_user_code", columnList = "user_code", unique = true) })
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class User {

	@Id
	@SequenceGenerator(name = "seq_user", allocationSize = 1, initialValue = 1, sequenceName = "seq_user_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@Column(name = "id")
	private long id;
	@Column(name = "user_code")
	private String userCode;
	@Column(name = "password")
	private String password;
	@Column(name = "status")
	private int status;
	@Column(name = "entered_by")
	private String enteredBy;
	@Column(name = "entered_date")
	private Date enteredDate;
	@Column(name = "last_update_by")
	private String lastUpdateBy;
	@Column(name = "last_update_date")
	private Date lastUpdateDate;
	public long getId() {
		return id;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getPassword() {
		return password;
	}
	public int getStatus() {
		return status;
	}
	public String getEnteredBy() {
		return enteredBy;
	}
	public Date getEnteredDate() {
		return enteredDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}
	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userCode=" + userCode + ", password=" + password + ", status=" + status
				+ ", enteredBy=" + enteredBy + ", enteredDate=" + enteredDate + ", lastUpdateBy=" + lastUpdateBy
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}
}
