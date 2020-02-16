package com.wanda.warehouse.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.HisProductSubMaterialRatioVo;
import com.wanda.warehouse.domain.ProductSubMaterialRatioVo;
import com.wanda.warehouse.repository.ProductSubMaterialRatioRepository;
import com.wanda.warehouse.repository.entity.ProductSubMaterialRatio;
import com.wanda.warehouse.service.HisProductSubMaterialRatioService;
import com.wanda.warehouse.service.ProductSubMaterialRatioService;

@Service
public class ProductSubMaterialRatioServiceImpl implements ProductSubMaterialRatioService {

	private static Logger log = Logger.getLogger(ProductSubMaterialRatioServiceImpl.class);
	
	@Autowired
	ProductSubMaterialRatioRepository productSubMaterialRatioRepo;
	@Autowired
	HisProductSubMaterialRatioService hisProductSubMaterialRatioService;
	
	@Override
	public Page<ProductSubMaterialRatioVo> findAllProductSubMaterialRatios(int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductSubMaterialRatioVo> productSubMaterialRatios = productSubMaterialRatioRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllProductSubMaterialRatios("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+productSubMaterialRatios);
		return productSubMaterialRatios;
	}

	@Override
	public ProductSubMaterialRatioVo findProductSubMaterialRatioById(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		ProductSubMaterialRatioVo productSubMaterialRatio = productSubMaterialRatioRepo.findByIdAsVo(id);
		log.debug("findProductSubMaterialRatioById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+productSubMaterialRatio);
		return productSubMaterialRatio;
	}

	@Override
	public long addProductSubMaterialRatio(ProductSubMaterialRatioVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		ProductSubMaterialRatio productSubMaterialRatio = this.addEntity(vo, Name);
		productSubMaterialRatio = productSubMaterialRatioRepo.save(productSubMaterialRatio);
		long id = -1;
		if(productSubMaterialRatio != null && productSubMaterialRatio.getId() > 0) {
			id = productSubMaterialRatio.getId();
		}
		log.debug("saveProductSubMaterialRatio("+productSubMaterialRatio+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}

	@Override
	public int updateProductSubMaterialRatio(ProductSubMaterialRatioVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前ProductSubMaterialRatio是否存在
		ProductSubMaterialRatio oldProductSubMaterialRatio = productSubMaterialRatioRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldProductSubMaterialRatio != null) {
			oldProductSubMaterialRatio = this.updateEntity(vo, Name, oldProductSubMaterialRatio);
			oldProductSubMaterialRatio = productSubMaterialRatioRepo.save(oldProductSubMaterialRatio);
			if(oldProductSubMaterialRatio != null && oldProductSubMaterialRatio.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateProductSubMaterialRatio("+vo+","+Name+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Override
	public void removeProductSubMaterialRatio(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		productSubMaterialRatioRepo.deleteById(id);
		log.debug("removeProductSubMaterialRatio("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}

	@Override
	public List<ProductSubMaterialRatioVo> findProductSubMaterialRatiosByProductCode(String productCode, int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		List<ProductSubMaterialRatioVo> vo = productSubMaterialRatioRepo.findProductSubMaterialRatiosByProductCode(productCode,pageable);
		log.debug("findProductSubMaterialRatiosByProductCode("+productCode+page+size+") cost "+(System.currentTimeMillis()-start)+" ms.");
		return vo;
	}
	
	@Override
	public List<ProductSubMaterialRatioVo> findProductSubMaterialRatioByProductCode(String productCode){
		long start = System.currentTimeMillis();
		List<ProductSubMaterialRatioVo> ProductSubMaterialRatioVos = productSubMaterialRatioRepo.findProductSubMaterialRatiosByProductCode(productCode);
		log.debug("ProductSubMaterialRatios("+productCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+ProductSubMaterialRatioVos);
		return ProductSubMaterialRatioVos;
	}
	
	@Override
	public void removeProductSubMaterialRatio(String productCode,String productName,String userName) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<ProductSubMaterialRatioVo> oldRatio = productSubMaterialRatioRepo.findProductSubMaterialRatiosByProductCode(productCode);
		if(Objects.nonNull(oldRatio)&&!oldRatio.isEmpty()) {
			for(ProductSubMaterialRatioVo o:oldRatio) {
				HisProductSubMaterialRatioVo his = new  HisProductSubMaterialRatioVo(0,productCode,o.getSubMaterialCode()
						,o.getSubMaterialRatio(),userName,Dictionary.DATA_STATUS_VALID.getDcode());
				hisProductSubMaterialRatioService.addHisProductSubMaterialRatio(his, userName);
			}
		}
		productSubMaterialRatioRepo.deleteByProductCode(productCode);
		log.debug("removeProductSubMaterialRatio("+productCode+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}
	
	private ProductSubMaterialRatio addEntity(ProductSubMaterialRatioVo vo,String Name) {
		ProductSubMaterialRatio psmr = new ProductSubMaterialRatio();
		psmr.setProductCode(vo.getProductCode());
		psmr.setSubMaterialCode(vo.getSubMaterialCode());
		psmr.setSubMaterialRatio(vo.getSubMaterialRatio());
		psmr.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		psmr.setEnteredBy(Name);
		psmr.setEnteredDate(new Date());
		return psmr;
	}

	private ProductSubMaterialRatio updateEntity(ProductSubMaterialRatioVo vo, String Name, ProductSubMaterialRatio psmr) {
		psmr.setProductCode(vo.getProductCode());
		psmr.setSubMaterialCode(vo.getSubMaterialCode());
		psmr.setSubMaterialRatio(vo.getSubMaterialRatio());
		psmr.setLastUpdateBy(Name);
		psmr.setLastUpdateDate(new Date());
		return psmr;
	}
}
