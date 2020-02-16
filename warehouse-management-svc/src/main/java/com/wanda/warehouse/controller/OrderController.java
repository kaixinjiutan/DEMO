package com.wanda.warehouse.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.warehouse.domain.OrderFamily;
import com.wanda.warehouse.domain.OrderProductVo;
import com.wanda.warehouse.domain.OrderVo;
import com.wanda.warehouse.service.OrderProductService;
import com.wanda.warehouse.service.OrderService;
import com.wanda.warehouse.service.SubMaterialService;
import com.wanda.warehouse.service.SubMaterialStockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
@Api(tags = "订单相关功能")
public class OrderController {
	
	private static Logger log = Logger.getLogger(OrderController.class);
	
	@Autowired OrderService orderService;
	@Autowired OrderProductService orderProductService;
	@Autowired SubMaterialService subMaterialService;
	@Autowired SubMaterialStockService subMaterialStockService;
	
	@ApiOperation(value = "查询订单列表", notes = "分页查询订单表")
	@GetMapping("/page/{page}/{size}")
	public Page<OrderVo> getOrders(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<OrderVo> voPage = null;
		try {
			voPage = orderService.findAllOrders(page, size);
		}catch(Exception e) {
			log.error(userCode +" getOrders("+page+","+size+") error.",e);
		}
		log.info(userCode +" getOrders("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询订单列表", notes = "查询订单表")
	@GetMapping("/list")
	public List<OrderVo> getOrders(@RequestHeader String userCode) {
		long start = System.currentTimeMillis();
		List<OrderVo> voList = null;
		try {
			voList = orderService.findAllOrders();
		}catch(Exception e) {
			log.error(userCode +" getOrders() error.",e);
		}
		log.info(userCode +" getOrders() cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}

	@ApiOperation(value = "查询订单产品", notes = "根据订单编号查询订单产品")
	@GetMapping("/order/products/{orderCode}")
	public List<OrderProductVo> findOrderAndOrderproductByOrdercode(@RequestHeader String userCode, @PathVariable String orderCode){
		long start = System.currentTimeMillis();
		List<OrderProductVo> voList = null;
		try {
			voList = orderService.findOrderProductsByOrdercodeAsVo(orderCode);
		}catch(Exception e) {
			log.error(userCode +" findOrderProductsByOrdercodeAsVo("+orderCode+") error.",e);
		}
		log.info(userCode +" findOrderProductsByOrdercodeAsVo("+orderCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}
	
	@ApiOperation(value = "根据客户查询订单列表", notes = "根据客户查询订单表")
	@GetMapping("/list/{customerCode}/{page}/{size}")
	public List<OrderVo> getOrders(@RequestHeader String userCode, @PathVariable String customerCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		List<OrderVo> voList = null;
		try {
			voList = orderService.findOrdersByCustomerCode(customerCode);
		}catch(Exception e) {
			log.error(userCode +" getOrdersByCustomerCode("+customerCode+") error.",e);
		}
		log.info(userCode +" getOrdersByCustomerCode("+customerCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}
	
	@ApiOperation(value = "查询单个订单详情", notes = "根据id查询订单表一条记录")
	@GetMapping("/one/{id}")
	public OrderVo getOrder(@RequestHeader String userCode, @PathVariable int id) {
		long start = System.currentTimeMillis();
		OrderVo voOrder = null;
		try {
			voOrder = orderService.findOrderById(id);
		}catch(Exception e) {
			log.error(userCode +" getOrder(id = "+id+") error", e);
		}
		log.info(userCode +" getOrder(id = " + id + ") cost "+(System.currentTimeMillis()-start)+" ms. return "+voOrder);
		return voOrder;
	}
	
	@ApiOperation(value = "新增订单预演", notes = "并不变更数据库，根据现有信息计算当前订单的物料消耗，OrderFamily.insufficientSubMaterials和OrderFamily.sufficientSubMaterials存储了预演的结果信息")
	@PostMapping("/preAdd")
	public Set<String> preAddOrderProducts(@RequestHeader String userCode, @RequestBody OrderFamily vo) {
		long start = System.currentTimeMillis();
		 Set<String> result = new HashSet<>();
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			if(vo.getOrder() == null) {
				throw new Exception("Order is null.");
			}
			if(vo.getOrderProducts() == null || vo.getOrderProducts().isEmpty()) {
				throw new Exception("orderProducts is null.");
			}
			OrderVo order = this.orderService.findOrderByNo(vo.getOrder().getOrderNo());
			if(order == null) {
				//下面的内容在service的一个方法里写，注意加事务@Transaction
				//1.根据当前订单选择的产品编码列表，查询产品物料配比表，返回产品编码与相应的物料配比对应关系
				//2.根据第一步的查询结果计算当前订单需要的物料信息
				//3.去sub_material_stock表，新增N条记录，记录当前订单消耗的物料库存信息
				//4.更新子物料表的库存信息，此处做减法
				//5.第4步执行之后检查子物料是否有库存小于零的情况，如果有的话则下单失败，如果没有的话则下单成功
				//6.返回值1.成功 -1.失败
				vo = this.orderService.preAddOrderFamily(vo);
				if(Objects.nonNull(vo.getInsufficientSubMaterials())&&!vo.getInsufficientSubMaterials().isEmpty()) {
					result.addAll(vo.getInsufficientSubMaterials());
				}
				if(Objects.nonNull(vo.getSufficientSubMaterials())&&!vo.getSufficientSubMaterials().isEmpty()) {
					result.addAll(vo.getSufficientSubMaterials());
				}
			} else {
				throw new Exception("Order is exist.");
			}
		}catch(Exception e) {
			log.error(userCode + " preAddOrderProducts("+userCode+","+vo+") error.",e);
		}
		log.info(userCode + " preAddOrderProducts("+userCode+","+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+result);
		return result;
	}
	

	@ApiOperation(value = "新增订单", notes = "向订单表插入一条记录，同时向订单产品表中增加N条记录，同时更新配比表库存信息，在一个事务中处理")
	@PostMapping("/add")
	public OrderFamily addOrderProducts(@RequestHeader String userCode, @RequestBody OrderFamily vo) {
		long start = System.currentTimeMillis();
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			if(vo.getOrder() == null) {
				throw new Exception("Order is null.");
			}
			if(vo.getOrderProducts() == null || vo.getOrderProducts().isEmpty()) {
				throw new Exception("orderProducts is null.");
			}
			OrderVo order = this.orderService.findOrderByNo(vo.getOrder().getOrderNo());
			if(order == null) {
				//下面的内容在service的一个方法里写，注意加事务@Transaction
				//1.根据当前订单选择的产品编码列表，查询产品物料配比表，返回产品编码与相应的物料配比对应关系
				//2.根据第一步的查询结果计算当前订单需要的物料信息
				//3.去sub_material_stock表，新增N条记录，记录当前订单消耗的物料库存信息
				//4.更新子物料表的库存信息，此处做减法
				//5.第4步执行之后检查子物料是否有库存小于零的情况，如果有的话则下单失败，如果没有的话则下单成功
				//6.返回值1.成功 -1.失败
				vo = this.orderService.addOrderFamily(vo, userCode);
			} else {
				throw new Exception("Order is exist.");
			}
		}catch(Exception e) {
			log.error(userCode + " addOrderProducts("+userCode+","+vo+") error.",e);
		}
		log.info(userCode + " addOrderProducts("+userCode+","+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		
		return vo;
	}
	
	@ApiOperation(value = "修改订单详情", notes = "修改订单详情，注意orderCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加")
	@PostMapping("/update")
	public OrderFamily updateOrder(@RequestHeader String userCode,  @RequestBody OrderFamily vo) {
		long start = System.currentTimeMillis();
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			//vo合法性检查
			if(vo == null) {
				throw new Exception("vo is null.");
			}
			if(vo.getOrder() == null) {
				throw new Exception("vo.order is null.");
			}
			if(vo.getOrder().getId() < 1) {
				throw new Exception("vo.order.id is less than 1.");
			}
			vo = orderService.updateOrderFamily(vo, userCode);
		}catch(Exception e) {
			log.error(userCode+" updateOrder("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateOrder("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		return vo;
	}
	

	@ApiOperation(value = "删除订单详情", notes = "删除订单详情，注意这里只做逻辑删除，不做物理删除，即只更新db里的status信息")
	@PostMapping("/delete/{id}")
	public void removeOrder(@RequestHeader String userCode,  @RequestBody OrderFamily vo) {
		//删除订单时要计算库存，去sub_material_stock表新增记录
		//查询过程order(order_no)->order_product(product_code)->product_sub_material_ratio(sub_material_code)
		orderService.removeOrderFamily(vo, userCode);
	}
}
