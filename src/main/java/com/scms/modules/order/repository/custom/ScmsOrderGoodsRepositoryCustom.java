/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderGoods;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderGoodsRepositoryCustom {

	/**
	 * 获取ScmsOrderGoods列表
	 * @param scmsOrderGoods
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderGoods scmsOrderGoods, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderGoods列表总数
	 * @param scmsOrderGoods
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderGoods scmsOrderGoods, Map<String, Object> params);
}