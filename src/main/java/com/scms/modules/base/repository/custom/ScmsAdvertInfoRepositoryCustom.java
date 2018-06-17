/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsAdvertInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsAdvertInfoRepositoryCustom {

	/**
	 * 获取ScmsAdvertInfo列表
	 * @param scmsAdvertInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsAdvertInfo scmsAdvertInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsAdvertInfo列表总数
	 * @param scmsAdvertInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsAdvertInfo scmsAdvertInfo, Map<String, Object> params);
}