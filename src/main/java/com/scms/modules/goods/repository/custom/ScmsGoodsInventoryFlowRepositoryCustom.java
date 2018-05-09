/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.goods.entity.ScmsGoodsInventoryFlow;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsGoodsInventoryFlowRepositoryCustom {

	/**
	 * 获取ScmsGoodsInventoryFlow列表
	 * @param scmsGoodsInventoryFlow
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsGoodsInventoryFlow列表总数
	 * @param scmsGoodsInventoryFlow
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params);
}