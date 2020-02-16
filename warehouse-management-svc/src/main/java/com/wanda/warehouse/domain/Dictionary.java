package com.wanda.warehouse.domain;

public enum Dictionary {
	
	DATA_STATUS_INVALID(10001,"无效","DATA_STATUS"),
	DATA_STATUS_VALID(10002,"有效","DATA_STATUS"),
	
	PRODUCT_STATUS_RATIO_START(20001,"开始配比","PRODUCT_STATUS"),
	PRODUCT_STATUS_RATIO_END(20002,"完成配比","PRODUCT_STATUS"),
	PRODUCT_STATUS_VALID(20003,"有效","PRODUCT_STATUS"),
	PRODUCT_STATUS_INVALID(20004,"无效","PRODUCT_STATUS"),
	
	ORDER_STATUS_RATIO_START(30001,"下单成功","ORDER_STATUS"),
	ORDER_STATUS_RATIO_END(30002,"完成订单","ORDER_STATUS"),
	ORDER_STATUS_VALID(30003,"有效","ORDER_STATUS"),
	ORDER_STATUS_INVALID(30004,"无效","ORDER_STATUS"),
	
	;
	
	private int dcode;
	private String dname;
	private String type;
	
	Dictionary(int dcode,String dname,String type){
		this.dcode = dcode;
		this.dname = dname;
		this.type = type;
	}

	public int getDcode() {
		return dcode;
	}

	public String getDname() {
		return dname;
	}

	public String getType() {
		return type;
	}
	
}
