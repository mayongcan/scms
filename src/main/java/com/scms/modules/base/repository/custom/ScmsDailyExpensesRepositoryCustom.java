/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsDailyExpenses;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsDailyExpensesRepositoryCustom {

	/**
	 * 获取ScmsDailyExpenses列表
	 * @param scmsDailyExpenses
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsDailyExpenses scmsDailyExpenses, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsDailyExpenses列表总数
	 * @param scmsDailyExpenses
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsDailyExpenses scmsDailyExpenses, Map<String, Object> params);
}