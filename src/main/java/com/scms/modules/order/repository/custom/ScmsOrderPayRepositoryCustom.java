/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderPay;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderPayRepositoryCustom {

	/**
	 * 获取ScmsOrderPay列表
	 * @param scmsOrderPay
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderPay scmsOrderPay, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderPay列表总数
	 * @param scmsOrderPay
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderPay scmsOrderPay, Map<String, Object> params);
}