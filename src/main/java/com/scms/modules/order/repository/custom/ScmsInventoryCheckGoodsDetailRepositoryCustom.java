/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsInventoryCheckGoodsDetail;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsInventoryCheckGoodsDetailRepositoryCustom {

	/**
	 * 获取ScmsInventoryCheckGoodsDetail列表
	 * @param scmsInventoryCheckGoodsDetail
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsInventoryCheckGoodsDetail列表总数
	 * @param scmsInventoryCheckGoodsDetail
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail, Map<String, Object> params);
}