/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsVenderInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsVenderInfoRepositoryCustom {

	/**
	 * 获取ScmsVenderInfo列表
	 * @param scmsVenderInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsVenderInfo scmsVenderInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsVenderInfo列表总数
	 * @param scmsVenderInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsVenderInfo scmsVenderInfo, Map<String, Object> params);
}