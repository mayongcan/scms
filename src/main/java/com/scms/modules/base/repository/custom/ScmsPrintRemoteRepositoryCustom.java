/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsPrintRemote;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsPrintRemoteRepositoryCustom {

	/**
	 * 获取ScmsPrintRemote列表
	 * @param scmsPrintRemote
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsPrintRemote scmsPrintRemote, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsPrintRemote列表总数
	 * @param scmsPrintRemote
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsPrintRemote scmsPrintRemote, Map<String, Object> params);
}