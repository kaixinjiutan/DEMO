package com.wanda.warehouse.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.OrderProductVo;
import com.wanda.warehouse.repository.OrderProductRepository;
import com.wanda.warehouse.repository.entity.OrderProduct;
import com.wanda.warehouse.service.OrderProductService;

@Service
public class OrderProductServiceImpl implements OrderProductService{

	private static Logger log = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired OrderProductRepository orderproductRepo;
	
	@Override
	public long addOrderProduct(OrderProductVo vo,String Name) {
		long start = System.currentTimeMillis();
		OrderProduct orderproduct = this.addEntity(vo, Name);
		orderproduct = orderproductRepo.save(orderproduct);
		long id = -1;
		if(orderproduct != null && orderproduct.getId() > 0) {
			id = orderproduct.getId();
		}
		log.debug("saveOrder("+orderproduct+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@Override
	public List<OrderProductVo> findOrderProductsByIdlAsVo(Set<Long> idl){
		long start = System.currentTimeMillis();
		List<OrderProductVo> orderProducts = new ArrayList<OrderProductVo>();
		for(long id : idl) {
			orderProducts.add(orderproductRepo.findProductsByIdAsVo(id));
		}
		log.debug("findOrderProductsByIdlAsVo("+idl+") cost "+(System.currentTimeMillis()-start)+" ms. return "+orderProducts);
		return orderProducts;
	}
	
	@Override
	public List<OrderProductVo> findOrderProductsByOrderCode(String orderCode){
		long start = System.currentTimeMillis();
		List<OrderProductVo> orderProducts = orderproductRepo.findProductsByOrdercodeAsVo(orderCode);
		log.debug("findOrderProductsByOrderCode("+orderCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+orderProducts);
		return orderProducts;
	}
	
	@Override
	public void removeOrderProduct(OrderProductVo vo) {
		orderproductRepo.deleteById(vo.getId());
	}
	
	private OrderProduct addEntity(OrderProductVo vo,String Name) {
		OrderProduct o = new OrderProduct();
		o.setId(vo.getId());
		o.setOrderNo(vo.getOrderNo());
		o.setProductCode(vo.getProductCode());
		o.setQuantity(vo.getQuantity());
		o.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		o.setEnteredBy(Name);
		o.setEnteredDate(new Date());
		return o;
	}

}
