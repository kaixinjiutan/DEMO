package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.OrderFamily;
import com.wanda.warehouse.domain.OrderProductVo;
import com.wanda.warehouse.domain.OrderVo;

public interface OrderService {

	Page<OrderVo> findAllOrders(int page, int size);
	
	List<OrderVo> findAllOrders();
	
	OrderVo findOrderById(long id);
	
	long addOrder(OrderVo vo,String userCode);
	
	int updateOrder(OrderVo vo,String userCode);
	
	void removeOrder(long id);
	
	List<OrderVo> findOrdersByCustomerCode(String customerCode);
	
	List<OrderProductVo> findOrderProductsByOrdercodeAsVo(String Ordercode);
	
	OrderVo findOrderByNo(String no);
	
	OrderFamily addOrderFamily(OrderFamily vo,String userCode);
	
	OrderFamily updateOrderFamily(OrderFamily vo, String userCode);
	
	OrderFamily preAddOrderFamily(OrderFamily vo);
	
	void removeOrderFamily(OrderFamily vo, String userCode);
	
}
