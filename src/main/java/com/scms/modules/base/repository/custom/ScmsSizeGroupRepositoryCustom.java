/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsSizeGroup;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsSizeGroupRepositoryCustom {

	/**
	 * 获取ScmsSizeGroup列表
	 * @param scmsSizeGroup
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsSizeGroup scmsSizeGroup, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsSizeGroup列表总数
	 * @param scmsSizeGroup
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsSizeGroup scmsSizeGroup, Map<String, Object> params);
}