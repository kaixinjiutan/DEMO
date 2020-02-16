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
@Table(name = "Material", indexes = {@Index(name = "u_material_code",  columnList="material_code", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class Material {

	@Id
	@SequenceGenerator(name="seq_material",allocationSize=1,initialValue=1, sequenceName="seq_material_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_material") 
	
	@Column(name = "id")
	private long id;
	@Column(name = "material_code")
	private String MaterialCode;
	@Column(name = "material_name")
	private String MaterialName;
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
	public String getMaterialCode() {
		return MaterialCode;
	}
	public String getMaterialName() {
		return MaterialName;
	}
	public int getStatus() {
		return Status;
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
	public void setMaterialCode(String materialCode) {
		MaterialCode = materialCode;
	}
	public void setMaterialName(String materialName) {
		MaterialName = materialName;
	}
	public void setStatus(int status) {
		Status = status;
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
		return "Material [id=" + id + ", MaterialCode=" + MaterialCode + ", MaterialName=" + MaterialName + ", Status="
				+ Status + ", EnteredBy=" + EnteredBy + ", EnteredDate=" + EnteredDate + ", LastUpdateBy="
				+ LastUpdateBy + ", LastUpdateDate=" + LastUpdateDate + "]";
	}
}
