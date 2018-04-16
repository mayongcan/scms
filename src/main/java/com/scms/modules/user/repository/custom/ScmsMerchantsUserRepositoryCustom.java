/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.user.entity.ScmsMerchantsUser;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsMerchantsUserRepositoryCustom {

	/**
	 * 获取ScmsMerchantsUser列表
	 * @param scmsMerchantsUser
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getMerchantsUserList(ScmsMerchantsUser scmsMerchantsUser, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsMerchantsUser列表总数
	 * @param scmsMerchantsUser
	 * @param params
	 * @return
	 */
	public int getMerchantsUserListCount(ScmsMerchantsUser scmsMerchantsUser, Map<String, Object> params);
}