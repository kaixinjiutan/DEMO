package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.HisProductSubMaterialRatioVo;
import com.wanda.warehouse.repository.entity.HisProductSubMaterialRatio;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface HisProductSubMaterialRatioRepository extends JpaRepository<HisProductSubMaterialRatio, Long>{

	@Query("select new com.wanda.warehouse.domain.HisProductSubMaterialRatioVo(id,ProductCode,SubMaterialCode,SubMaterialRatio,EnteredBy,Status) from HisProductSubMaterialRatio hpsmr where status = ?1 order by id desc")
	Page<HisProductSubMaterialRatioVo> findAllActiveAsVo (Pageable pageable,int Status);
	
	@Query("select new com.wanda.warehouse.domain.HisProductSubMaterialRatioVo(id,ProductCode,SubMaterialCode,SubMaterialRatio,EnteredBy,Status) from HisProductSubMaterialRatio hpsmr where status = ?1 order by id desc")
	List<HisProductSubMaterialRatioVo> findAllActiveAsVo (int Status);
	
	@Query("select new com.wanda.warehouse.domain.HisProductSubMaterialRatioVo(id,ProductCode,SubMaterialCode,SubMaterialRatio,EnteredBy,Status) from HisProductSubMaterialRatio hpsmr where hpsmr.id = ?1")
	HisProductSubMaterialRatioVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.HisProductSubMaterialRatioVo(id,ProductCode,SubMaterialCode,SubMaterialRatio,EnteredBy,Status) from HisProductSubMaterialRatio hpsmr where hpsmr.ProductCode = ?1")
	List<HisProductSubMaterialRatioVo> findByProductCodeAsVo(String productCode);
	
}
