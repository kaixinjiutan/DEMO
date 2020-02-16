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
@Table(name = "ProductSubMaterialRatio", indexes = {@Index(name = "u_product_sub_material_ratio",  columnList="product_code,sub_material_code", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class ProductSubMaterialRatio {

	@Id
	@SequenceGenerator(name="seq_product_sub_material_ratio",allocationSize=1,initialValue=1, sequenceName="seq_product_sub_material_ratio_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_product_sub_material_ratio") 
	
	@Column(name = "id")
	private long id;
	@Column(name = "product_code")
	private String ProductCode;
	@Column(name = "sub_material_code")
	private String SubMaterialCode;
	@Column(name = "sub_material_ratio")
	private int SubMaterialRatio;
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
	public String getProductCode() {
		return ProductCode;
	}
	public String getSubMaterialCode() {
		return SubMaterialCode;
	}
	public int getSubMaterialRatio() {
		return SubMaterialRatio;
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
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public void setSubMaterialCode(String subMaterialCode) {
		SubMaterialCode = subMaterialCode;
	}
	public void setSubMaterialRatio(int subMaterialRatio) {
		SubMaterialRatio = subMaterialRatio;
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
		return "ProductSubMaterialRatio [id=" + id + ", ProductCode=" + ProductCode + ", SubMaterialCode="
				+ SubMaterialCode + ", SubMaterialRatio=" + SubMaterialRatio + ", Status=" + Status + ", EnteredBy="
				+ EnteredBy + ", EnteredDate=" + EnteredDate + ", LastUpdateBy=" + LastUpdateBy + ", LastUpdateDate="
				+ LastUpdateDate + "]";
	}
}
