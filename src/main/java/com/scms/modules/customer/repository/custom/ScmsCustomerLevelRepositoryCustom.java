/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.customer.entity.ScmsCustomerLevel;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsCustomerLevelRepositoryCustom {

	/**
	 * 获取ScmsCustomerLevel列表
	 * @param scmsCustomerLevel
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsCustomerLevel scmsCustomerLevel, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsCustomerLevel列表总数
	 * @param scmsCustomerLevel
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsCustomerLevel scmsCustomerLevel, Map<String, Object> params);
}