package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.MaterialVo;
import com.wanda.warehouse.repository.entity.Material;;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material,Long>{

	@Query("select new com.wanda.warehouse.domain.MaterialVo(m.id, m.MaterialCode, m.MaterialName, m.Status, m.EnteredBy) from Material m  where status = ?1 order by id desc")
	Page<MaterialVo> findAllActiveAsVo (Pageable pageable,int status);
	
	@Query("select new com.wanda.warehouse.domain.MaterialVo(m.id, m.MaterialCode, m.MaterialName, m.Status, m.EnteredBy) from Material m  where status = ?1 order by id desc")
	List<MaterialVo> findAllActiveAsVo (int status);
	
	@Query("select new com.wanda.warehouse.domain.MaterialVo(m.id, m.MaterialCode, m.MaterialName, m.Status, m.EnteredBy) from Material m where m.id = ?1")
	MaterialVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.MaterialVo(m.id, m.MaterialCode, m.MaterialName, m.Status, m.EnteredBy) from Material m where m.MaterialCode = ?1")
	MaterialVo findMaterialsByMaterialCodeAsVo(String MaterialCode);
	
	@Query("select new com.wanda.warehouse.domain.MaterialVo(m.id, m.MaterialCode, m.MaterialName, m.Status, m.EnteredBy) from Material m where m.MaterialName = ?1")
	MaterialVo findMaterialsByMaterialNameAsVo(String MaterialName);
}

