/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.goods.entity.ScmsGoodsModifyLog;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsGoodsModifyLogRepositoryCustom {

	/**
	 * 获取ScmsGoodsModifyLog列表
	 * @param scmsGoodsModifyLog
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsGoodsModifyLog scmsGoodsModifyLog, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsGoodsModifyLog列表总数
	 * @param scmsGoodsModifyLog
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsGoodsModifyLog scmsGoodsModifyLog, Map<String, Object> params);
}