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
@Table(name = "Product", indexes = { @Index(name = "u_product_code", columnList = "product_code", unique = true) })
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class Product {
	@Id
	@SequenceGenerator(name = "seq_product", allocationSize = 1, initialValue = 1, sequenceName = "seq_product_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product")

	@Column(name = "id")
	private long id;
	@Column(name = "product_code")
	private String ProductCode;
	@Column(name = "product_name")
	private String ProductName;
	@Column(name = "markup")
	private double Markup;//0.05
	@Column(name = "price")
	private double Price;//(1+markup)*Cost
	@Column(name = "cost")
	private double Cost;//子物料成本核算+人工费+管理费，暂时使用子物料核算
	@Column(name = "status")
	private int Status;
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

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public double getMarkup() {
		return Markup;
	}

	public void setMarkup(double markup) {
		Markup = markup;
	}

	public double getPrice() {
		return (1+this.Markup)*this.Cost;
	}

//	private void setPrice(double price) {
//		Price = price;
//	}

	public double getCost() {
		return Cost;
	}

	public void setCost(double cost) {
		Cost = cost;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
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
		return "Product [id=" + id + ", ProductCode=" + ProductCode + ", ProductName=" + ProductName + ", Status="
				+ Status + ", EnteredBy=" + EnteredBy + ", EnteredDate=" + EnteredDate + ", LastUpdateBy="
				+ LastUpdateBy + ", LastUpdateDate=" + LastUpdateDate + "]";
	}
}
