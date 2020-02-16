package com.wanda.warehouse.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.OrderVo;
import com.wanda.warehouse.domain.ProductSubMaterialRatioVo;
import com.wanda.warehouse.domain.SubMaterialStockVo;
import com.wanda.warehouse.domain.SubMaterialVo;
import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.OrderFamily;
import com.wanda.warehouse.domain.OrderProductVo;
import com.wanda.warehouse.repository.OrderProductRepository;
import com.wanda.warehouse.repository.OrderRepository;
import com.wanda.warehouse.repository.ProductSubMaterialRatioRepository;
import com.wanda.warehouse.repository.entity.Order;
import com.wanda.warehouse.repository.entity.OrderProduct;
import com.wanda.warehouse.service.OrderProductService;
import com.wanda.warehouse.service.OrderService;
import com.wanda.warehouse.service.ProductSubMaterialRatioService;
import com.wanda.warehouse.service.SubMaterialService;
import com.wanda.warehouse.service.SubMaterialStockService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger log = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderProductRepository orderProductRepo;
	@Autowired
	ProductSubMaterialRatioRepository productSubMaterialRatioRepositoryRepo;
	@Autowired
	SubMaterialService subMaterialService;
	@Autowired
	SubMaterialStockService subMaterialStockService;
	@Autowired
	ProductSubMaterialRatioService productSubMaterialRatioService;
	@Autowired
	OrderProductService orderProductService;

	@Override
	public Page<OrderVo> findAllOrders(int page, int size) {
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<OrderVo> orders = orderRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllOrders(" + page + "," + size + ") cost " + (System.currentTimeMillis() - start)
				+ " ms. return " + orders);
		return orders;
	}
	
	@Override
	public List<OrderVo> findAllOrders() {
		long start = System.currentTimeMillis();
		List<OrderVo> orders = orderRepo.findAllActiveAsVo(Dictionary.ORDER_STATUS_VALID.getDcode());
		log.debug("findAllOrders() cost " + (System.currentTimeMillis() - start) + " ms. return " + orders);
		return orders;
	}

	@Override
	public OrderVo findOrderById(long id) {
		long start = System.currentTimeMillis();
		OrderVo order = orderRepo.findByIdAsVo(id);
		log.debug("findOrderById(" + id + ") cost " + (System.currentTimeMillis() - start) + " ms. return " + order);
		return order;
	}

	@Override
	public long addOrder(OrderVo vo, String userCode) {
		long start = System.currentTimeMillis();
		Order order = this.addEntity(vo, userCode);
		order = orderRepo.save(order);
		long id = -1;
		if (order != null && order.getId() > 0) {
			id = order.getId();
		}
		log.debug("saveOrder(" + order + ") cost " + (System.currentTimeMillis() - start) + " ms. return " + id);
		return id;
	}

	@Override
	public int updateOrder(OrderVo vo, String userCode) {
		long start = System.currentTimeMillis();
		int record = -1;
		// 去db查询当前Order是否存在
		Order oldOrder = orderRepo.getOne((long) 0);
		// 如果为空，说明不存在，抛出异常
		if (oldOrder != null) {
			oldOrder = this.updateEntity(vo, userCode, oldOrder);
			oldOrder = orderRepo.save(oldOrder);
			if (oldOrder != null && oldOrder.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateOrder(" + vo + "," + userCode + ") cost " + (System.currentTimeMillis() - start) + " ms. return "
				+ record);
		return record;
	}

	@Override
	public void removeOrder(long id) {
		long start = System.currentTimeMillis();
		orderRepo.deleteById(id);
		log.debug("removeOrder(" + id + ") cost " + (System.currentTimeMillis() - start) + " ms.");
	}

	@Override
	public List<OrderVo> findOrdersByCustomerCode(String customerCode) {
		long start = System.currentTimeMillis();
		List<OrderVo> orders = orderRepo.findOrderByCustomerCodeAsVo(customerCode);
		log.debug("findAllOrdersByCustomerCode(" + customerCode + ") cost " + (System.currentTimeMillis() - start)
				+ " ms. return " + orders);
		return orders;
	}
	
	
	@Override
	public List<OrderProductVo> findOrderProductsByOrdercodeAsVo(String Ordercode){
		long start = System.currentTimeMillis();
		List<OrderProductVo> orderProducts = new ArrayList<OrderProductVo>();
		log.debug("findProductsByOrdercodeAsVo("+Ordercode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+orderProducts);
		orderProducts = orderProductRepo.findProductsByOrdercodeAsVo(Ordercode);
		return orderProducts;
	}

	private Order addEntity(OrderVo vo, String userCode) {
		Order o = new Order();
		o.setOrderNo(vo.getOrderNo());
		o.setCustomerCode(vo.getCustomerCode());
		o.setStartDate(vo.getStartDate());
		o.setEndDate(vo.getEndDate());
		o.setStatus(Dictionary.ORDER_STATUS_VALID.getDcode());
		o.setEnteredBy(userCode);
		o.setEnteredDate(new Date());
		o.setLastUpdateBy(userCode);
		o.setLastUpdateDate(new Date());
		return o;
	}

	private Order updateEntity(OrderVo vo, String userCode, Order o) {
		o.setOrderNo(vo.getOrderNo());
		o.setCustomerCode(vo.getCustomerCode());
		o.setStartDate(vo.getStartDate());
		o.setEndDate(vo.getEndDate());
		o.setEnteredBy(userCode);
		o.setEnteredDate(new Date());
		return o;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	@Transactional
	@Override
	public void removeOrderFamily(OrderFamily vo, String userName) {
		SubMaterialStockVo subMaterialStock = new SubMaterialStockVo(
				1, "subMaterialCode", 1, "orderNo", "purchaser",
				0, 1, "enteredBy");
		Map<String,Integer> subMaterialCodeToQuantity = new HashMap<>();
		//获取产品以及数量的对应关系
		List<OrderProductVo> OrderProductNvo = new ArrayList<>();
		OrderProductNvo = vo.getOrderProducts();
		Map<String,Integer> productToQuantity = vo.getOrderProducts().stream().collect(Collectors.toMap(OrderProductVo::getProductCode, OrderProductVo::getQuantity));
		//查询当前订单下所有产品的子物料配比信息
		List<ProductSubMaterialRatioVo> materialRatioes = this.productSubMaterialRatioRepositoryRepo.findProductSubMaterialRatiosByProductCodes(productToQuantity.keySet());

		//计算每个子物料需要的数量
		if(Objects.nonNull(materialRatioes)&&!materialRatioes.isEmpty()) {
			for(ProductSubMaterialRatioVo materialRatioe:materialRatioes) {
				//获得产品数量
				Integer productQuantity = productToQuantity.get(materialRatioe.getProductCode());
				//计算需要的子物料数量
				int subMaterialQuantity = materialRatioe.getSubMaterialRatio()*productQuantity;
				subMaterialCodeToQuantity.put(materialRatioe.getSubMaterialCode(), subMaterialQuantity);
			}
		}
		List<SubMaterialVo> subMaterials = this.subMaterialService.findSubMaterialBySubMaterialCode(subMaterialCodeToQuantity.keySet());
		
		if(Objects.nonNull(subMaterials)&&!subMaterials.isEmpty()) {
			for(SubMaterialVo subMaterial:subMaterials) {
				//退回二级物料
				int cost = subMaterial.getStock()+subMaterialCodeToQuantity.get(subMaterial.getSubMaterialCode());
				subMaterial.setStock(cost);
				subMaterialService.updateSubMaterial(subMaterial, userName);
				subMaterialStock.setSubMaterialCode(subMaterial.getSubMaterialCode());
				subMaterialStock.setStockModifyStatus(1);
				subMaterialStock.setOrderNo(vo.getOrder().getOrderNo());
				subMaterialStock.setPurchaser(userName);
				subMaterialStock.setQuantity(subMaterialCodeToQuantity.get(subMaterial.getSubMaterialCode()));
				subMaterialStockService.addSubMaterialStock(subMaterialStock, userName);
			}
		}

		for(OrderProductVo opv : OrderProductNvo) {
			orderProductService.removeOrderProduct(opv);
		}
		
		this.removeOrder(vo.getOrder().getId());
	}

////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public OrderVo findOrderByNo(String no) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		OrderVo order = this.orderRepo.findByOrderNoAsVo(no);
		log.debug("findOrderByNo(" + no + ") cost " + (System.currentTimeMillis() - start) + " ms. return " + order);
		return order;
	}
////////////////////////////////////////////////////////////////////////////////////////////////	
	@Transactional
	@Override
	public OrderFamily preAddOrderFamily(OrderFamily vo) {
		Map<String,Integer> subMaterialCodeToQuantity = new HashMap<>();
		vo = this.preAddOrderFamily(vo, subMaterialCodeToQuantity);
		return vo;
	}

	private OrderFamily preAddOrderFamily(OrderFamily vo, Map<String,Integer> subMaterialCodeToQuantity) {
		//获取产品以及数量的对应关系
		Map<String,Integer> productToQuantity = vo.getOrderProducts().stream().collect(Collectors.toMap(OrderProductVo::getProductCode, OrderProductVo::getQuantity));
		//查询当前订单下所有产品的子物料配比信息
		List<ProductSubMaterialRatioVo> materialRatioes = this.productSubMaterialRatioRepositoryRepo.findProductSubMaterialRatiosByProductCodes(productToQuantity.keySet());
		//计算每个子物料需要的数量
		if(Objects.nonNull(materialRatioes)&&!materialRatioes.isEmpty()) {
			for(ProductSubMaterialRatioVo materialRatioe:materialRatioes) {
				//获得产品数量
				Integer productQuantity = productToQuantity.get(materialRatioe.getProductCode());
				//计算需要的子物料数量
				int subMaterialQuantity = materialRatioe.getSubMaterialRatio()*productQuantity;
				subMaterialCodeToQuantity.put(materialRatioe.getSubMaterialCode(), subMaterialQuantity);
			}
		}
		Set<String> insufficientSubMaterials = new HashSet<>();
		Set<String> sufficientSubMaterials = new HashSet<>();
		List<SubMaterialVo> subMaterials = this.subMaterialService.findSubMaterialBySubMaterialCode(subMaterialCodeToQuantity.keySet());
		if(Objects.nonNull(subMaterials)&&!subMaterials.isEmpty()) {
			for(SubMaterialVo subMaterial:subMaterials) {
				//用现有库存和当前需要的子物料数量计算本次订单中，当前物料是否库存充足
				int cost = subMaterial.getStock()-subMaterialCodeToQuantity.get(subMaterial.getSubMaterialCode());
				if(cost < 0) {
					//记录子物料库存信息：库存不足时
					insufficientSubMaterials.add("子物料【"+subMaterial.getMaterialCode()+"】库存不足，现有库存为【"+subMaterial.getStock()+subMaterial.getUnit()+"】,本订单需要【"+subMaterialCodeToQuantity.get(subMaterial.getSubMaterialCode())+subMaterial.getUnit()+"】,请及时补货");
				} else {
					//记录子物料库存信息：库存
					sufficientSubMaterials.add("子物料【"+subMaterial.getMaterialCode()+"】库存充足，现有库存为【"+subMaterial.getStock()+subMaterial.getUnit()+"】,本订单需要【"+subMaterialCodeToQuantity.get(subMaterial.getSubMaterialCode())+subMaterial.getUnit()+"】");
				}
			}
		}
		vo.setInsufficientSubMaterials(insufficientSubMaterials);
		vo.setSufficientSubMaterials(sufficientSubMaterials);
		return vo;
	}
////////////////////////////////////////////////////////////////////////////////////////////////	
	@Transactional
	@Override
	public OrderFamily addOrderFamily(OrderFamily vo, String userName) {
		Map<String,Integer> subMaterialCodeToQuantity = new HashMap<>();
		vo = this.preAddOrderFamily(vo,subMaterialCodeToQuantity);
		//如果不存在库存不足的子物料，则插入改订单相关信息
		if(Objects.isNull(vo.getInsufficientSubMaterials())||vo.getInsufficientSubMaterials().isEmpty()) {
			//新增订单表记录
			long id = this.addOrder(vo.getOrder(), userName);
			if(id < 1) {
				throw new RuntimeException("add order["+vo.getOrder()+"] failed!");
			}else {
				vo.getOrder().setId(id);
			}
			//新增订单产品表记录
			for (OrderProductVo op : vo.getOrderProducts()) {
				op.setOrderNo(vo.getOrder().getOrderNo());
				long opid = this.addOrderProduct(op, userName);
				if(opid < 1) {
					throw new RuntimeException("add orderProduct["+op+"] failed!");
				}else {
					op.setId(opid);
				}
			}
			//更新子物料库存表
			for(String subMaterialCode:subMaterialCodeToQuantity.keySet()) {
				int subMaterialQuantity = subMaterialCodeToQuantity.get(subMaterialCode);
				int record = this.subMaterialService.updateSubMaterialStock(subMaterialQuantity, subMaterialCode, userName);
				if(record < 1) {
					throw new RuntimeException("update stock of subMaterialCode["+subMaterialCode+"] failed!");
				}else {
					//向子物料库存表里增加一条记录
					SubMaterialStockVo stockVo = new SubMaterialStockVo(0,subMaterialCode,-1,vo.getOrder().getOrderNo(),null,subMaterialQuantity,Dictionary.DATA_STATUS_VALID.getDcode(),userName);
					long stockId = this.subMaterialStockService.addSubMaterialStock(stockVo, userName);
					if(stockId < 1) {
						throw new RuntimeException("add subMaterialStock["+stockVo+"] failed!");
					}
				}
			}
		}
		return vo;
	}

	private long addOrderProduct(OrderProductVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		OrderProduct orderProduct = this.addOrderProductEntity(vo, Name);
		orderProduct = this.orderProductRepo.save(orderProduct);
		long id = -1;
		if (orderProduct != null && orderProduct.getId() > 0) {
			id = orderProduct.getId();
		}
		log.debug("addOrderProduct(" + orderProduct + ") cost " + (System.currentTimeMillis() - start) + " ms. return "
				+ id);
		return id;
	}
////////////////////////////////////////////////////////////////////////////////////////////////
	@Transactional
	@Override
	public OrderFamily updateOrderFamily(OrderFamily vo, String userName) {
		Set<Long> idl = new HashSet<Long>();
		OrderFamily midOrderFamily = new OrderFamily();
		midOrderFamily.setOrder(vo.getOrder());
		for(OrderProductVo midvo : vo.getOrderProducts()) {
			idl.add(midvo.getId());
		}
		midOrderFamily.setOrderProducts(orderProductService.findOrderProductsByIdlAsVo(idl));
		this.removeOrderFamily(midOrderFamily, userName);
		vo = this.addOrderFamily(vo, userName);
		
/*		
		// 先删除原有的子物料信息
		this.orderProductRepo.deleteByOrderNo(vo.getOrder().getOrderNo());
		// 更新产品
		long id = this.updateOrder(vo.getOrder(), userName);
		vo.getOrder().setId(id);
		// 添加新的子物料信息
		for (OrderProductVo mvo : vo.getOrderProducts()) {
			mvo.setOrderNo(vo.getOrder().getOrderNo());
			long mid = this.addOrderProduct(mvo, userName);
			mvo.setId(mid);
		}
*/
		return vo;
	}
////////////////////////////////////////////////////////////////////////////////////////////////
	private OrderProduct addOrderProductEntity(OrderProductVo vo,String Name) {
		OrderProduct o = new OrderProduct();
		o.setOrderNo(vo.getOrderNo());
		o.setProductCode(vo.getProductCode());
		o.setStatus(Dictionary.ORDER_STATUS_RATIO_START.getDcode());
		o.setQuantity(vo.getQuantity());
		o.setEnteredBy(Name);
		o.setEnteredDate(new Date());
		return o;
	}

}