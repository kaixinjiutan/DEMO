package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.MaterialVo;

public interface MaterialService {
	
	Page<MaterialVo> findAllMaterials(int page, int size);
	
	List<MaterialVo> findAllMaterials();
	
	MaterialVo findMaterialById(long id);
	
	MaterialVo findMaterialsByMaterialCode(String mateialCode);
	
	long addMaterial(MaterialVo vo,String userCode);
	
	int updateMaterial(MaterialVo vo,String userCode);
	
	void removeMaterial(long id);

}
