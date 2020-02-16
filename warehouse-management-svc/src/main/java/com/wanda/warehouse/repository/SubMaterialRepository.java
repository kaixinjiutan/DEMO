package com.wanda.warehouse.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.warehouse.domain.SubMaterialVo;
import com.wanda.warehouse.repository.entity.SubMaterial;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface SubMaterialRepository extends JpaRepository<SubMaterial, Long>{

	@Query("select new com.wanda.warehouse.domain.SubMaterialVo(sm.id, sm.SubMaterialCode, sm.SubMaterialName, m.MaterialCode, m.MaterialName, sm.Stock, sm.Unit, sm.Cost, sm.Status, sm.EnteredBy) "
			+ "from SubMaterial sm,Material m where sm.MaterialCode = m.MaterialCode and "
			+ "sm.Status = ?1 order by sm.id desc")
	Page<SubMaterialVo> findAllActiveAsVo (Pageable pageable,int Status);
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialVo(sm.id, sm.SubMaterialCode, sm.SubMaterialName, m.MaterialCode, m.MaterialName, sm.Stock, sm.Unit, sm.Cost, sm.Status, sm.EnteredBy) "
			+ "from SubMaterial sm,Material m where sm.MaterialCode = m.MaterialCode and "
			+ "sm.Status = ?1 order by sm.id desc")
	List<SubMaterialVo> findAllActiveAsVo (int Status);
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialVo(sm.id, sm.SubMaterialCode, sm.SubMaterialName, m.MaterialCode, m.MaterialName, sm.Stock, sm.Unit, sm.Cost, sm.Status, sm.EnteredBy) "
			+ "from SubMaterial sm,Material m where sm.MaterialCode = m.MaterialCode and "
			+ "sm.id = ?1")
	SubMaterialVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialVo(sm.id, sm.SubMaterialCode, sm.SubMaterialName, m.MaterialCode, m.MaterialName, sm.Stock, sm.Unit, sm.Cost, sm.Status, sm.EnteredBy) "
			+ "from SubMaterial sm,Material m where sm.MaterialCode = m.MaterialCode and "
			+ "sm.SubMaterialCode = ?1")
	SubMaterialVo findBySubMaterialCodeAsVo(String submaterialcode);
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialVo(sm.id, sm.SubMaterialCode, sm.SubMaterialName, m.MaterialCode, m.MaterialName, sm.Stock, sm.Unit, sm.Cost, sm.Status, sm.EnteredBy) "
			+ "from SubMaterial sm,Material m where sm.MaterialCode = m.MaterialCode and "
			+ "sm.MaterialCode = ?1")
	List<SubMaterialVo> findByMaterialCodeAsVo(String materialcode);
	
	
	@Query("select new com.wanda.warehouse.domain.SubMaterialVo(sm.id, sm.SubMaterialCode, sm.SubMaterialName, m.MaterialCode, m.MaterialName, sm.Stock, sm.Unit, sm.Cost, sm.Status, sm.EnteredBy) "
			+ "from SubMaterial sm,Material m where sm.MaterialCode = m.MaterialCode and "
			+ "sm.SubMaterialCode IN(:submaterialcodes)")
	List<SubMaterialVo> findBySubMaterialCodeAsVo(@Param("submaterialcodes") Set<String> submaterialcodes);
	
	@Modifying
	@Transactional
	@Query("update SubMaterial sm set sm.Stock = (sm.Stock - ?1),sm.LastUpdateDate=sysdate,sm.LastUpdateBy = ?4 where sm.SubMaterialCode = ?2 and sm.Stock = ?3")
	int updateSubMaterialStock(int quantity,String submaterialcode,int oldStock,String userName);
}