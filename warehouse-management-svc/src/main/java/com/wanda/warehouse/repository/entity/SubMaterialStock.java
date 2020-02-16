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
@Table(name = "SubMaterialStock", indexes = {@Index(name = "u_sub_material_code",  columnList="", unique = true)})
//@Index(name = "i_company_activity", columnList = "activity_id,company_id") 联合索引
public class SubMaterialStock {

	@Id
	@SequenceGenerator(name="seq_sub_material_stock",allocationSize=1,initialValue=1, sequenceName="seq_sub_material_stock_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_sub_material_stock")
	
	@Column(name = "id")
	private long id;
	@Column(name = "sub_material_code")
	private String SubMaterialCode;
	@Column(name = "stock_modify_status")
	private int StockModifyStatus;
	@Column(name = "order_no")
	private String OrderNo;
	@Column(name = "purchaser")
	private String Purchaser;
	@Column(name = "quantity")
	private int Quantity;
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
	public String getSubMaterialCode() {
		return SubMaterialCode;
	}
	public int getStockModifyStatus() {
		return StockModifyStatus;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public String getPurchaser() {
		return Purchaser;
	}
	public int getQuantity() {
		return Quantity;
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
	public void setSubMaterialCode(String subMaterialCode) {
		SubMaterialCode = subMaterialCode;
	}
	public void setStockModifyStatus(int stockModifyStatus) {
		StockModifyStatus = stockModifyStatus;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public void setPurchaser(String purchaser) {
		Purchaser = purchaser;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
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
		return "SubMaterialStock [id=" + id + ", SubMaterialCode=" + SubMaterialCode + ", StockModifyStatus="
				+ StockModifyStatus + ", OrderNo=" + OrderNo + ", Purchaser="
				+ Purchaser + ", Quantity=" + Quantity + ", Status=" + Status + ", EnteredBy=" + EnteredBy
				+ ", EnteredDate=" + EnteredDate + ", LastUpdateBy=" + LastUpdateBy + ", LastUpdateDate="
				+ LastUpdateDate + "]";
	}
}
