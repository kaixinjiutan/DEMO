package com.wanda.warehouse.controller;

import java.util.List;
import java.util.Objects;

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

import com.wanda.warehouse.domain.CustomerVo;
import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/customer")
@Api(tags = "客户相关功能")
public class CustomerController {
	
	private static Logger log = Logger.getLogger(CustomerController.class);
	
	@Autowired CustomerService customerService;
	
	@ApiOperation(value = "查询客户列表", notes = "分页查询客户表")
	@GetMapping("/page/{page}/{size}")
	public Page<CustomerVo> getCustomers(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<CustomerVo> voPage = null;
		try {
			voPage = customerService.findAllCustomers(page, size);
		}catch(Exception e) {
			log.error(userCode +" getCustomers("+page+","+size+") error.",e);
		}
		log.info(userCode +" getCustomers("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询客户列表", notes = "查询客户表")
	@GetMapping("/list")
	public List<CustomerVo> getCustomers(@RequestHeader String userCode) {
		long start = System.currentTimeMillis();
		List<CustomerVo> voList = null;
		try {
			voList = customerService.findAllCustomers();
		}catch(Exception e) {
			log.error(userCode +" getCustomers() error.",e);
		}
		log.info(userCode +" getCustomers() cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}

	@ApiOperation(value = "查询单个客户详情", notes = "根据id查询客户表一条记录")
	@GetMapping("/one/{id}")
	public CustomerVo getCustomer(@RequestHeader String userCode, @PathVariable int id) {
		long start = System.currentTimeMillis();
		CustomerVo voCustomer = null;
		try {
			voCustomer = customerService.findCustomerById(id);
		}catch(Exception e) {
			log.error(userCode +" getCustomer(id = "+id+") error", e);
		}
		log.info(userCode +" getCustomer(id = " + id + ") cost "+(System.currentTimeMillis()-start)+" ms. return "+voCustomer);
		return voCustomer;
	}
	
	@ApiOperation(value = "新增客户", notes = "向客户表插入一条记录")
	@PostMapping("/add")
	public long addCustomer(@RequestHeader String userCode,  @RequestBody CustomerVo vo) {
		long start = System.currentTimeMillis();
		long id = -1;
		try {
			CustomerVo oldVo = customerService.findCustomerByCustomerCode(vo.getCustomerCode());
			if(Objects.nonNull(oldVo)) {
				vo.setId(oldVo.getId());
				vo.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
				this.customerService.updateCustomer(vo, userCode);
				id = vo.getId();
			}else {
				id = customerService.addCustomer(vo, userCode);
			}
		}catch(Exception e) {
			log.error(userCode + " addCustomer("+vo+","+userCode+") error.",e);
		}
		log.info(userCode + " addCustomer("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@ApiOperation(value = "修改客户详情", notes = "修改客户详情，注意customerCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加")
	@PostMapping("/update")
	public int updateCustomer(@RequestHeader String userCode,  @RequestBody CustomerVo vo) {
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			//vo合法性检查
			if(vo == null) {
				throw new Exception("vo is null.");
			}
			if(vo.getId() < 1) {
				throw new Exception("id is less than 1.");
			}
			
			CustomerVo voCustomer = customerService.findCustomerById(vo.getId());
			if(voCustomer.getCustomerCode().equals(vo.getCustomerCode())) {
				voCustomer.setCustomerName(vo.getCustomerName());
				record = customerService.updateCustomer(voCustomer, userCode);
			}
			else {
				throw new Exception("dont have this CustomerCode.");
			}
		}catch(Exception e) {
			log.error(userCode+" updateCustomer("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateCustomer("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	

	@ApiOperation(value = "删除客户详情", notes = "删除客户详情，注意这里只做逻辑删除，不做物理删除，即只更新db里的status信息")
	@PostMapping("/delete/{id}")
	public int removeCustomer(@RequestHeader String userCode, @PathVariable long id) {
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			if(id < 1) {
				throw new Exception("id is less than 1.");
			}
			CustomerVo vo = customerService.findCustomerById(id);
			vo.setStatus(Dictionary.PRODUCT_STATUS_INVALID.getDcode());
			record = customerService.updateCustomer(vo, userCode);
		}catch(Exception e) {
			log.error(userCode+" deleteCustomer("+id+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" deleteCustomer("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
}
