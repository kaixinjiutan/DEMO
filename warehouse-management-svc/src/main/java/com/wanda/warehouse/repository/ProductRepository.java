package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.ProductVo;
import com.wanda.warehouse.repository.entity.Product;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("select new com.wanda.warehouse.domain.ProductVo(p.id, p.ProductCode, p.ProductName, p.Markup, p.Price, p.Cost, p.Status, p.EnteredBy, p.LastUpdateBy) from Product p where p.Status != ?1 order by id desc")
	Page<ProductVo> findAllStatusRatioEndAsVo (Pageable pageable,int status);
	
	@Query("select new com.wanda.warehouse.domain.ProductVo(p.id, p.ProductCode, p.ProductName, p.Markup, p.Price, p.Cost, p.Status, p.EnteredBy, p.LastUpdateBy) from Product p where p.id = ?1")
	ProductVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.ProductVo(p.id, p.ProductCode, p.ProductName, p.Markup, p.Price, p.Cost, p.Status, p.EnteredBy, p.LastUpdateBy) from Product p where p.Status != ?1 order by id desc")
	List<ProductVo> findProductsStatusRatioEndAsVo(int status);
	
	@Query("select new com.wanda.warehouse.domain.ProductVo(p.id, p.ProductCode, p.ProductName, p.Markup, p.Price, p.Cost, p.Status, p.EnteredBy, p.LastUpdateBy) from Product p where p.ProductCode = ?1")
	ProductVo findByProductCodeAsVo(String productCode);
}
