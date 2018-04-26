/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsInventoryTransferGoodsDetail;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsInventoryTransferGoodsDetailRepositoryCustom {

	/**
	 * 获取ScmsInventoryTransferGoodsDetail列表
	 * @param scmsInventoryTransferGoodsDetail
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsInventoryTransferGoodsDetail列表总数
	 * @param scmsInventoryTransferGoodsDetail
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail, Map<String, Object> params);
}