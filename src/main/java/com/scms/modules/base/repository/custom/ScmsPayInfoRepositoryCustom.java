/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsPayInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsPayInfoRepositoryCustom {

	/**
	 * 获取ScmsPayInfo列表
	 * @param scmsPayInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsPayInfo scmsPayInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsPayInfo列表总数
	 * @param scmsPayInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsPayInfo scmsPayInfo, Map<String, Object> params);
}