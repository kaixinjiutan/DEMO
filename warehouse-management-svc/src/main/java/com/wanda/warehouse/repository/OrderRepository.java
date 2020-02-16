package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.OrderVo;
import com.wanda.warehouse.repository.entity.Order;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select new com.wanda.warehouse.domain.OrderVo(o.id, o.OrderNo, o.CustomerCode,c.customerName, o.StartDate, o.EndDate, o.Status, o.EnteredBy) "
			+ "from Order o,Customer c where o.CustomerCode = c.customerCode and o.Status = ?2 order by o.id desc")
	Page<OrderVo> findAllActiveAsVo (Pageable pageable,int status);
	
	@Query("select new com.wanda.warehouse.domain.OrderVo(o.id, o.OrderNo, o.CustomerCode,c.customerName, o.StartDate, o.EndDate, o.Status, o.EnteredBy) "
			+ "from Order o,Customer c where o.CustomerCode = c.customerCode and o.Status = ?1 and o.LastUpdateDate > sysdate-365 order by o.id desc")
	List<OrderVo> findAllActiveAsVo (int status);
	
	@Query("select new com.wanda.warehouse.domain.OrderVo(o.id, o.OrderNo, o.CustomerCode,c.customerName, o.StartDate, o.EndDate, o.Status,o.EnteredBy) "
			+ "from Order o,Customer c where o.CustomerCode = c.customerCode and o.id = ?1")
	OrderVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.OrderVo(o.id, o.OrderNo, o.CustomerCode,c.customerName, o.StartDate, o.EndDate, o.Status,o.EnteredBy) "
			+ "from Order o,Customer c where o.CustomerCode = c.customerCode and o.CustomerCode = ?1")
	List<OrderVo> findOrderByCustomerCodeAsVo(String customerCode);
	
	@Query("select new com.wanda.warehouse.domain.OrderVo(o.id, o.OrderNo, o.CustomerCode,c.customerName, o.StartDate, o.EndDate, o.Status,o.EnteredBy) "
			+ "from Order o,Customer c where o.CustomerCode = c.customerCode and o.OrderNo = ?1")
	OrderVo findByOrderNoAsVo(String orderNo);
	
}
