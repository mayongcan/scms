/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsTagInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsTagInfoRepositoryCustom {

	/**
	 * 获取ScmsTagInfo列表
	 * @param scmsTagInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsTagInfo scmsTagInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsTagInfo列表总数
	 * @param scmsTagInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsTagInfo scmsTagInfo, Map<String, Object> params);
}