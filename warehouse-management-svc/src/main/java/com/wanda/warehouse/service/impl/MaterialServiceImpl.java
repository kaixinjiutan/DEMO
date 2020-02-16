package com.wanda.warehouse.service.impl;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.MaterialVo;
import com.wanda.warehouse.domain.SubMaterialVo;
import com.wanda.warehouse.repository.MaterialRepository;
import com.wanda.warehouse.repository.SubMaterialRepository;
import com.wanda.warehouse.repository.entity.Material;
import com.wanda.warehouse.service.MaterialService;
import com.wanda.warehouse.service.SubMaterialService;


@Service
public class MaterialServiceImpl implements MaterialService{

	private static Logger log = Logger.getLogger(MaterialServiceImpl.class);
	
	@Autowired
	MaterialRepository materialRepo;
	
	@Autowired
	SubMaterialRepository subMaterialRepo;
	
	@Autowired
	SubMaterialService subMaterialService;

	@Override
	public Page<MaterialVo> findAllMaterials(int page, int size) {
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<MaterialVo> materials = materialRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllMaterials("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+materials);
		return materials;
	}
	
	@Override
	public List<MaterialVo> findAllMaterials() {
		long start = System.currentTimeMillis();
		List<MaterialVo> materials = materialRepo.findAllActiveAsVo(Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllMaterials() cost "+(System.currentTimeMillis()-start)+" ms. return "+materials);
		return materials;
	}

	@Override
	public MaterialVo findMaterialById(long id) {
		long start = System.currentTimeMillis();
		MaterialVo material = materialRepo.findByIdAsVo(id);
		log.debug("findMaterialById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+material);
		return material;
	}

	@Override
	public MaterialVo findMaterialsByMaterialCode(String mateialCode) {
		long start = System.currentTimeMillis();
		MaterialVo vo = materialRepo.findMaterialsByMaterialCodeAsVo(mateialCode);
		log.debug("findMaterialsByMaterialCode("+mateialCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		return vo;
	}
	
	@Override
	public long addMaterial(MaterialVo vo,String userCode) {
		long start = System.currentTimeMillis();
		Material material = this.addEntity(vo, userCode);
		material = materialRepo.save(material);
		long id = -1;
		if(material != null && material.getId() > 0) {
			id = material.getId();
		}
		log.debug("saveMaterial("+material+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@Override
	public int updateMaterial(MaterialVo vo, String userCode) {
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前Material是否存在
		Material oldMaterial = materialRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldMaterial != null) {
			List<SubMaterialVo> subMaterials = subMaterialRepo.findByMaterialCodeAsVo(oldMaterial.getMaterialCode());
			if(subMaterials != null && subMaterials.size() > 0) {
				for(SubMaterialVo subVo:subMaterials) {
					subVo.setStatus(Dictionary.DATA_STATUS_INVALID.getDcode());
					record = subMaterialService.updateSubMaterial(subVo, null);
				}
			}
			oldMaterial = this.updateEntity(vo, userCode, oldMaterial);
			oldMaterial = materialRepo.save(oldMaterial);
			if(oldMaterial != null && oldMaterial.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateMaterial("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Override
	public void removeMaterial(long id) {
		long start = System.currentTimeMillis();
		materialRepo.deleteById(id);
		log.debug("removeMaterial("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}
	
	private Material addEntity(MaterialVo vo,String userCode) {
		Material m = new Material();
		m.setMaterialCode(vo.getMaterialCode());
		m.setMaterialName(vo.getMaterialName());
		m.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		m.setEnteredBy(userCode);
		m.setEnteredDate(new Date());
		return m;
	}

	private Material updateEntity(MaterialVo nvo, String Name, Material m) {
		if(nvo.getMaterialName() != null && !nvo.getMaterialName().isEmpty()) {
			m.setMaterialName(nvo.getMaterialName());
		}
		if(nvo.getStatus() > 0) {
			m.setStatus(nvo.getStatus());
		}
		m.setLastUpdateBy(Name);
		m.setLastUpdateDate(new Date());
		return m;
	}

}
