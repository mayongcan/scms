/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderGoodsDetailRepositoryCustom {

	/**
	 * 获取ScmsOrderGoodsDetail列表
	 * @param scmsOrderGoodsDetail
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderGoodsDetail scmsOrderGoodsDetail, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderGoodsDetail列表总数
	 * @param scmsOrderGoodsDetail
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderGoodsDetail scmsOrderGoodsDetail, Map<String, Object> params);
}