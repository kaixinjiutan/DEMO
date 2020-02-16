package com.wanda.warehouse.service.impl;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.CustomerVo;
import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.repository.CustomerRepository;
import com.wanda.warehouse.repository.entity.Customer;
import com.wanda.warehouse.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static Logger log = Logger.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	CustomerRepository customerRepo;

	
	@Override
	public Page<CustomerVo> findAllCustomers(int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<CustomerVo> customers = customerRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllCustomers("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+customers);
		return customers;
	}
	
	@Override
	public List<CustomerVo> findAllCustomers() {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<CustomerVo> customers = customerRepo.findAllActiveAsVo(Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllCustomers() cost "+(System.currentTimeMillis()-start)+" ms. return "+customers);
		return customers;
	}

	@Override
	public CustomerVo findCustomerById(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		CustomerVo customer = customerRepo.findByIdAsVo(id);
		log.debug("findCustomerById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+customer);
		return customer;
	}

	@Override
	public CustomerVo findCustomerCodeByCustomerNameAsVo(String customerName){
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		CustomerVo vo = customerRepo.findCustomerCodeByCustomerNameAsVo(customerName);
		log.debug("findCustomerByCustomerCode("+customerName+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		return vo;
	}
	
	@Override
	public CustomerVo findCustomerByCustomerCode(String customerCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		CustomerVo vo = customerRepo.findCustomerByCustomerCodeAsVo(customerCode);
		log.debug("findCustomerByCustomerCode("+customerCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		return vo;
	}
	
	@Override
	public long addCustomer(CustomerVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Customer customer = this.addEntity(vo, Name);
		customer = customerRepo.save(customer);
		long id = -1;
		if(customer != null && customer.getId() > 0) {
			id = customer.getId();
		}
		log.debug("saveCustomer("+customer+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}

	@Override
	public int updateCustomer(CustomerVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前Customer是否存在
		Customer oldCustomer = customerRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldCustomer != null) {
			oldCustomer = this.updateEntity(vo, Name, oldCustomer);
			oldCustomer = customerRepo.save(oldCustomer);
			if(oldCustomer != null && oldCustomer.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateCustomer("+vo+","+Name+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Override
	public void removeCustomer(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		customerRepo.deleteById(id);
		log.debug("removeCustomer("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}

	private Customer addEntity(CustomerVo vo,String Name) {
		Customer c = new Customer();
		c.setCustomerCode(vo.getCustomerCode());
		c.setCustomerName(vo.getCustomerName());
		c.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		c.setEnteredBy(Name);
		c.setEnteredDate(new Date());
		return c;
	}

	private Customer updateEntity(CustomerVo nvo, String Name, Customer c) {
		if(nvo.getCustomerName() != null && !nvo.getCustomerName().isEmpty()) {
			c.setCustomerName(nvo.getCustomerName());
		}
		if(nvo.getStatus() > 0) {
			c.setStatus(nvo.getStatus());
		}
		c.setLastUpdateBy(Name);
		c.setLastUpdateDate(new Date());
		return c;
	}
}
