package com.wanda.warehouse.service;


import java.util.List;

import org.springframework.data.domain.Page;
import com.wanda.warehouse.domain.CustomerVo;

public interface CustomerService {
	
	Page<CustomerVo> findAllCustomers(int page, int size);
	
	List<CustomerVo> findAllCustomers();
	
	CustomerVo findCustomerById(long id);
	
	CustomerVo findCustomerCodeByCustomerNameAsVo(String customerName);
	
	CustomerVo findCustomerByCustomerCode(String customerCode);
	
	long addCustomer(CustomerVo vo,String Name);
	
	int updateCustomer(CustomerVo vo,String Name);
	
	void removeCustomer(long id);
}
