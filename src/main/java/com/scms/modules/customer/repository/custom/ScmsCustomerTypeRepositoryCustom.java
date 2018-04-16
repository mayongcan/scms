/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.customer.entity.ScmsCustomerType;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsCustomerTypeRepositoryCustom {

	/**
	 * 获取ScmsCustomerType列表
	 * @param scmsCustomerType
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsCustomerType scmsCustomerType, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsCustomerType列表总数
	 * @param scmsCustomerType
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsCustomerType scmsCustomerType, Map<String, Object> params);
}