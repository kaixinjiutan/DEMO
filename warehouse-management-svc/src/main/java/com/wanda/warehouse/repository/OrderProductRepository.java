package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.warehouse.domain.OrderProductVo;
import com.wanda.warehouse.repository.entity.OrderProduct;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
	
	@Query("select new com.wanda.warehouse.domain.OrderProductVo(op.id, op.OrderNo, op.ProductCode, op.Quantity, op.Status, op.EnteredBy) from OrderProduct op where op.id = ?1")
	OrderProductVo findProductsByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.OrderProductVo(op.id, op.OrderNo, op.ProductCode, op.Quantity, op.Status, op.EnteredBy) from OrderProduct op where op.OrderNo = ?1")
	List<OrderProductVo> findProductsByOrdercodeAsVo(String Ordercode);
	
	@Modifying
	@Transactional
	@Query(value= "delete from OrderProduct where OrderNo = ?1")
	void deleteByOrderNo(String orderNo);
}
