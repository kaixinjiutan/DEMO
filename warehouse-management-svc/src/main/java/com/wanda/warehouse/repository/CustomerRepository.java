package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.CustomerVo;
import com.wanda.warehouse.repository.entity.Customer;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	@Query("select new com.wanda.warehouse.domain.CustomerVo(c.id, c.customerCode, c.customerName, c.status, c.EnteredBy) from Customer c where status = ?1 order by id desc")
	Page<CustomerVo> findAllActiveAsVo (Pageable pageable,int status);
	
	@Query("select new com.wanda.warehouse.domain.CustomerVo(c.id, c.customerCode, c.customerName, c.status, c.EnteredBy) from Customer c where status = ?1 order by id desc")
	List<CustomerVo> findAllActiveAsVo (int status);

	@Query("select new com.wanda.warehouse.domain.CustomerVo(c.id, c.customerCode, c.customerName, c.status, c.EnteredBy) from Customer c where c.id = ?1")
	CustomerVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.CustomerVo(c.id, c.customerCode, c.customerName, c.status, c.EnteredBy) from Customer c where c.customerName = ?1")
	CustomerVo findCustomerCodeByCustomerNameAsVo(String customerName);
	
	@Query("select new com.wanda.warehouse.domain.CustomerVo(c.id, c.customerCode, c.customerName, c.status, c.EnteredBy) from Customer c where c.customerCode = ?1")
	CustomerVo findCustomerByCustomerCodeAsVo(String customerCode);
}