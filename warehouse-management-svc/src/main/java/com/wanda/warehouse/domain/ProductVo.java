package com.wanda.warehouse.domain;

public class ProductVo {

	// 产品表除[enteredBy,enteredDate,lastUpdateBy,lastUpdateDate]以外的属性
	private long id;
	private String productCode;
	private String productName;
	private double markUp;
	private double price;
	private double cost;
	private int status;
	private String enteredBy;
	private String lastEnteredBy;

	public ProductVo(long id, String productCode, String productName, double markUp, double price, double cost, int status, String enteredBy,
			String lastEnteredBy) {
		super();
		this.id = id;
		this.productCode = productCode;
		this.productName = productName;
		this.markUp = markUp;
		this.price = price;
		this.cost = cost;
		this.status = status;
		this.enteredBy = enteredBy;
		this.lastEnteredBy = lastEnteredBy;
	}

	public long getId() {
		return id;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public double getMarkUp() {
		return markUp;
	}

	public double getPrice() {
		return price;
	}

	public double getCost() {
		return cost;
	}

	public int getStatus() {
		return status;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public String getLastEnteredBy() {
		return lastEnteredBy;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setMarkUp(double markUp) {
		this.markUp = markUp;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public void setLastEnteredBy(String lastEnteredBy) {
		this.lastEnteredBy = lastEnteredBy;
	}

	@Override
	public String toString() {
		return "ProductVo [id=" + id + ", productCode=" + productCode + ", productName=" + productName + ", markUp="
				+ markUp + ", price=" + price + ", cost=" + cost + ", status=" + status + ", enteredBy=" + enteredBy
				+ ", lastEnteredBy=" + lastEnteredBy + "]";
	}

}
