/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.goods.entity.ScmsGoodsCategory;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsGoodsCategoryRepositoryCustom {

	/**
	 * 获取ScmsGoodsCategory列表
	 * @param scmsGoodsCategory
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsGoodsCategory列表总数
	 * @param scmsGoodsCategory
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params);
}