/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsTransportInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsTransportInfoRepositoryCustom {

	/**
	 * 获取ScmsTransportInfo列表
	 * @param scmsTransportInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsTransportInfo scmsTransportInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsTransportInfo列表总数
	 * @param scmsTransportInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsTransportInfo scmsTransportInfo, Map<String, Object> params);
}