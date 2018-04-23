/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.customer.entity.ScmsSupplierInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsSupplierInfoRepositoryCustom {

	/**
	 * 获取ScmsSupplierInfo列表
	 * @param scmsSupplierInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsSupplierInfo列表总数
	 * @param scmsSupplierInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params);
}