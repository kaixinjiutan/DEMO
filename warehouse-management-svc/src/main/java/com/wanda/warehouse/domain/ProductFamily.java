package com.wanda.warehouse.domain;

import java.util.List;

public class ProductFamily {

	private ProductVo product;
	private List<ProductSubMaterialRatioVo> materials;

	public ProductFamily(ProductVo product, List<ProductSubMaterialRatioVo> materials) {
		super();
		this.product = product;
		this.materials = materials;
	}

	public ProductVo getProduct() {
		return product;
	}

	public List<ProductSubMaterialRatioVo> getMaterials() {
		return materials;
	}
}
