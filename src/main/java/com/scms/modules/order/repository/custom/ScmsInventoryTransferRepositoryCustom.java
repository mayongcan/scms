/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsInventoryTransfer;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsInventoryTransferRepositoryCustom {

	/**
	 * 获取ScmsInventoryTransfer列表
	 * @param scmsInventoryTransfer
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsInventoryTransfer列表总数
	 * @param scmsInventoryTransfer
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params);
}