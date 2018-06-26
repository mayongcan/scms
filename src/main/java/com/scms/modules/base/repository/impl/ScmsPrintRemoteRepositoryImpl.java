/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.entity.ScmsPrintRemote;
import com.scms.modules.base.repository.custom.ScmsPrintRemoteRepositoryCustom;

public class ScmsPrintRemoteRepositoryImpl extends BaseRepository implements ScmsPrintRemoteRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.ORDER_NUM as \"orderNum\", tb.ORDER_ROW as \"orderRow\", "
	        + "tb.GOODS_LIST as \"goodsList\", tb.GOODS_DETAIL_LIST as \"goodsDetailList\", tb.ORDER_PAY_LIST as \"orderPayList\", tb.OTHER_INFO as \"otherInfo\", "
	        + "tb.STATUS as \"status\", tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", tb.PRINT_BY as \"printBy\", tb.PRINT_DATE as \"printDate\" "
			+ "FROM scms_print_remote tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_print_remote tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsPrintRemote scmsPrintRemote, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsPrintRemote, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsPrintRemote scmsPrintRemote, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsPrintRemote, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsPrintRemote scmsPrintRemote, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsPrintRemote != null && scmsPrintRemote.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsPrintRemote.getMerchantsId());
		}
		if(scmsPrintRemote != null && !StringUtils.isBlank(scmsPrintRemote.getStatus())) {
            sqlParams.querySql.append(" AND tb.STATUS = :status ");
            sqlParams.paramsList.add("status");
            sqlParams.valueList.add(scmsPrintRemote.getStatus());
		}
        return sqlParams;
	}
}