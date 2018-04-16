/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.user.entity.ScmsUserPoint;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsUserPointRepositoryCustom {

	/**
	 * 获取ScmsUserPoint列表
	 * @param scmsUserPoint
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsUserPoint scmsUserPoint, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsUserPoint列表总数
	 * @param scmsUserPoint
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsUserPoint scmsUserPoint, Map<String, Object> params);
}