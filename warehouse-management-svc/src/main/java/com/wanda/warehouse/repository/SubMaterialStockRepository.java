package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.SubMaterialStockVo;
import com.wanda.warehouse.repository.entity.SubMaterialStock;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface SubMaterialStockRepository extends JpaRepository<SubMaterialStock, Long>{

	@Query("select new com.wanda.warehouse.domain.SubMaterialStockVo(id,SubMaterialCode,StockModifyStatus,OrderNo,Purchaser,Quantity,Status,EnteredBy) from SubMaterialStock sms where Status = ?1 order by id desc")
	Page<SubMaterialStockVo> findAllActiveAsVo (Pageable pageable,int Status);
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialStockVo(id,SubMaterialCode,StockModifyStatus,OrderNo,Purchaser,Quantity,Status,EnteredBy) from SubMaterialStock sms where Status = ?1 order by id desc")
	List<SubMaterialStockVo> findAllActiveAsVo (int Status);
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialStockVo(id,SubMaterialCode,StockModifyStatus,OrderNo,Purchaser,Quantity,Status,EnteredBy) from SubMaterialStock sms where sms.id = ?1")
	SubMaterialStockVo findByIdAsVo(long id);
	
}