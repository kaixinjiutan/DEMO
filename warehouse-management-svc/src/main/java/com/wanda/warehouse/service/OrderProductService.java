package com.wanda.warehouse.service;


import java.util.List;
import java.util.Set;

import com.wanda.warehouse.domain.OrderProductVo;

public interface OrderProductService {
	
	List<OrderProductVo> findOrderProductsByIdlAsVo(Set<Long> idl);

	long addOrderProduct(OrderProductVo vo,String Name);
	
	void removeOrderProduct(OrderProductVo vo);

	List<OrderProductVo> findOrderProductsByOrderCode(String orderCode);
	
}
