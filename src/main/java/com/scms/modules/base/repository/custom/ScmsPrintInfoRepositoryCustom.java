/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsPrintInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsPrintInfoRepositoryCustom {

	/**
	 * 获取ScmsPrintInfo列表
	 * @param scmsPrintInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsPrintInfo scmsPrintInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsPrintInfo列表总数
	 * @param scmsPrintInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsPrintInfo scmsPrintInfo, Map<String, Object> params);
}