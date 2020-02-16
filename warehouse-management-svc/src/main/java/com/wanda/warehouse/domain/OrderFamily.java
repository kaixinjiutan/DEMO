package com.wanda.warehouse.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderFamily {

	private OrderVo order;
	private List<OrderProductVo> orderProducts;
	private Set<String> insufficientSubMaterials;
	private Set<String> sufficientSubMaterials;
	
	public OrderFamily() {
		order = new OrderVo(0, "", "", "", "", "", 0, "");
		orderProducts = new ArrayList<OrderProductVo>();
		insufficientSubMaterials = new HashSet<>();
		sufficientSubMaterials = new HashSet<>();
	}

	public OrderVo getOrder() {
		return order;
	}

	public void setOrder(OrderVo order) {
		this.order = order;
	}

	public List<OrderProductVo> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProductVo> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public Set<String> getInsufficientSubMaterials() {
		return insufficientSubMaterials;
	}

	public void setInsufficientSubMaterials(Set<String> insufficientSubMaterials) {
		this.insufficientSubMaterials = insufficientSubMaterials;
	}

	public Set<String> getSufficientSubMaterials() {
		return sufficientSubMaterials;
	}

	public void setSufficientSubMaterials(Set<String> sufficientSubMaterials) {
		this.sufficientSubMaterials = sufficientSubMaterials;
	}

	public OrderFamily(OrderVo order, List<OrderProductVo> orderProducts, Set<String> insufficientSubMaterials,
			Set<String> sufficientSubMaterials) {
		super();
		this.order = order;
		this.orderProducts = orderProducts;
		this.insufficientSubMaterials = insufficientSubMaterials;
		this.sufficientSubMaterials = sufficientSubMaterials;
	}

	@Override
	public String toString() {
		return "OrderFamily [order=" + order + ", orderProducts=" + orderProducts + ", insufficientSubMaterials="
				+ insufficientSubMaterials + ", sufficientSubMaterials=" + sufficientSubMaterials + "]";
	}

}
