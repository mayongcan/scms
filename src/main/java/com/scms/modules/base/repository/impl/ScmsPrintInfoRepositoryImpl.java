/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.entity.ScmsPrintInfo;
import com.scms.modules.base.repository.custom.ScmsPrintInfoRepositoryCustom;

public class ScmsPrintInfoRepositoryImpl extends BaseRepository implements ScmsPrintInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.PRINT_TYPE as \"printType\", tb.CONTENT as \"content\" "
			+ "FROM scms_print_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_print_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsPrintInfo scmsPrintInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsPrintInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsPrintInfo scmsPrintInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsPrintInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsPrintInfo scmsPrintInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsPrintInfo != null && scmsPrintInfo.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsPrintInfo.getMerchantsId());
		}
		if (scmsPrintInfo != null && !StringUtils.isBlank(scmsPrintInfo.getPrintType())) {
			sqlParams.querySql.append(" AND tb.PRINT_TYPE = :printType ");
			sqlParams.paramsList.add("printType");
			sqlParams.valueList.add(scmsPrintInfo.getPrintType());
		}
        return sqlParams;
	}
}