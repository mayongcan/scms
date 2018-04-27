/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsInventoryCheckGoods;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsInventoryCheckGoodsRepositoryCustom {

	/**
	 * 获取ScmsInventoryCheckGoods列表
	 * @param scmsInventoryCheckGoods
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsInventoryCheckGoods scmsInventoryCheckGoods, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsInventoryCheckGoods列表总数
	 * @param scmsInventoryCheckGoods
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsInventoryCheckGoods scmsInventoryCheckGoods, Map<String, Object> params);
}