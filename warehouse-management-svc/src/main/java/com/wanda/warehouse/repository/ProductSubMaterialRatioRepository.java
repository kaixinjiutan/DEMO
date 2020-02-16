package com.wanda.warehouse.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.warehouse.domain.ProductSubMaterialRatioVo;
import com.wanda.warehouse.repository.entity.ProductSubMaterialRatio;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface ProductSubMaterialRatioRepository extends JpaRepository<ProductSubMaterialRatio, Long>{

	@Query("select new com.wanda.warehouse.domain.ProductSubMaterialRatioVo("
			+ "psmr.id,psmr.ProductCode,m.MaterialCode,m.MaterialName,psmr.SubMaterialCode,sm.SubMaterialName,psmr.SubMaterialRatio,psmr.Status,psmr.EnteredBy) "
			+ "from ProductSubMaterialRatio psmr,SubMaterial sm,Material m "
			+ "where psmr.SubMaterialCode = sm.SubMaterialCode and sm.MaterialCode = m.MaterialCode and psmr.ProductCode IN(:productCodes) order by psmr.id desc")
	List<ProductSubMaterialRatioVo> findProductSubMaterialRatiosByProductCodes(@Param("productCodes") Set<String> productCodes);

	
	@Query("select new com.wanda.warehouse.domain.ProductSubMaterialRatioVo(psmr.id,psmr.ProductCode,m.MaterialCode,m.MaterialName,sm.SubMaterialCode,sm.SubMaterialName,psmr.SubMaterialRatio,psmr.Status,psmr.EnteredBy) from ProductSubMaterialRatio psmr,SubMaterial sm,Material m where psmr.SubMaterialCode = sm.SubMaterialCode and sm.MaterialCode = m.MaterialCode and psmr.Status = ?1 order by psmr.id desc")
	Page<ProductSubMaterialRatioVo> findAllActiveAsVo (Pageable pageable,int Status);
	
	@Query("select new com.wanda.warehouse.domain.ProductSubMaterialRatioVo(psmr.id,psmr.ProductCode,m.MaterialCode,m.MaterialName,sm.SubMaterialCode,sm.SubMaterialName,psmr.SubMaterialRatio,psmr.Status,psmr.EnteredBy) from ProductSubMaterialRatio psmr,SubMaterial sm,Material m where psmr.SubMaterialCode = sm.SubMaterialCode and sm.MaterialCode = m.MaterialCode and psmr.Status = ?1 order by psmr.id desc")
	List<ProductSubMaterialRatioVo> findAllActiveAsVo (int Status);
	
	@Query("select new com.wanda.warehouse.domain.ProductSubMaterialRatioVo(psmr.id,psmr.ProductCode,m.MaterialCode,m.MaterialName,sm.SubMaterialCode,sm.SubMaterialName,psmr.SubMaterialRatio,psmr.Status,psmr.EnteredBy) from ProductSubMaterialRatio psmr,SubMaterial sm,Material m where psmr.SubMaterialCode = sm.SubMaterialCode and sm.MaterialCode = m.MaterialCode and psmr.id = ?1")
	ProductSubMaterialRatioVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.ProductSubMaterialRatioVo(psmr.id,psmr.ProductCode,m.MaterialCode,m.MaterialName,sm.SubMaterialCode,sm.SubMaterialName,psmr.SubMaterialRatio,psmr.Status,psmr.EnteredBy) from ProductSubMaterialRatio psmr,SubMaterial sm,Material m where psmr.SubMaterialCode = sm.SubMaterialCode and sm.MaterialCode = m.MaterialCode and psmr.ProductCode = ?1 order by psmr.id desc")
	List<ProductSubMaterialRatioVo> findProductSubMaterialRatiosByProductCode(String productCode, Pageable pageable);
	
	@Query("select new com.wanda.warehouse.domain.ProductSubMaterialRatioVo(psmr.id,psmr.ProductCode,m.MaterialCode,m.MaterialName,sm.SubMaterialCode,sm.SubMaterialName,psmr.SubMaterialRatio,psmr.Status,psmr.EnteredBy) from ProductSubMaterialRatio psmr,SubMaterial sm,Material m where psmr.SubMaterialCode = sm.SubMaterialCode and sm.MaterialCode = m.MaterialCode and psmr.ProductCode = ?1 order by psmr.id desc")
	List<ProductSubMaterialRatioVo> findProductSubMaterialRatiosByProductCode(String productCode);
	
	@Modifying
	@Transactional
	@Query(value= "delete from ProductSubMaterialRatio where ProductCode = ?1")
	void deleteByProductCode(String ProductCode);

}