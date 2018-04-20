/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.goods.entity.ScmsGoodsInventory;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsGoodsInventoryRepositoryCustom {

	/**
	 * 获取ScmsGoodsInventory列表
	 * @param scmsGoodsInventory
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsGoodsInventory列表总数
	 * @param scmsGoodsInventory
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params);
}