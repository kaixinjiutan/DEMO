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
@Table(name = "Sub_Material", indexes = {@Index(name = "u_sub_material_code",  columnList="", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class SubMaterial {

	@Id
	@SequenceGenerator(name="seq_sub_material",allocationSize=1,initialValue=1, sequenceName="seq_sub_material_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_sub_material") 
	
	@Column(name = "id")
	private long id;
	@Column(name = "sub_material_code")
	private String SubMaterialCode;
	@Column(name = "sub_material_name")
	private String SubMaterialName;
	@Column(name = "material_code")
	private String MaterialCode;
	@Column(name = "stock")
	private int Stock;
	@Column(name = "unit")
	private String Unit;
	@Column(name = "cost")
	private double Cost;
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

	public String getSubMaterialCode() {
		return SubMaterialCode;
	}

	public void setSubMaterialCode(String subMaterialCode) {
		SubMaterialCode = subMaterialCode;
	}

	public String getSubMaterialName() {
		return SubMaterialName;
	}

	public void setSubMaterialName(String subMaterialName) {
		SubMaterialName = subMaterialName;
	}

	public String getMaterialCode() {
		return MaterialCode;
	}

	public void setMaterialCode(String materialCode) {
		MaterialCode = materialCode;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

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
		return "SubMaterial [id=" + id + ", SubMaterialCode=" + SubMaterialCode + ", SubMaterialName=" + SubMaterialName
				+ ", MaterialCode=" + MaterialCode + ", Stock=" + Stock + ", Unit=" + Unit + ", Cost=" + Cost
				+ ", Status=" + Status + ", EnteredBy=" + EnteredBy + ", EnteredDate=" + EnteredDate + ", LastUpdateBy="
				+ LastUpdateBy + ", LastUpdateDate=" + LastUpdateDate + "]";
	}
	
	
}
