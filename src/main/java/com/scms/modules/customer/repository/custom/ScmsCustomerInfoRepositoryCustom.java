/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.customer.entity.ScmsCustomerInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsCustomerInfoRepositoryCustom {

	/**
	 * 获取ScmsCustomerInfo列表
	 * @param scmsCustomerInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsCustomerInfo列表总数
	 * @param scmsCustomerInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params);
}