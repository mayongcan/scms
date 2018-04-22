/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.entity.ScmsPayInfo;
import com.scms.modules.base.repository.custom.ScmsPayInfoRepositoryCustom;

public class ScmsPayInfoRepositoryImpl extends BaseRepository implements ScmsPayInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PAY_NAME as \"payName\", tb.TYPE as \"type\", tb.MERCHANTS_ID as \"merchantsId\", m.MERCHANTS_NAME as \"merchantsName\" "
			+ "FROM scms_pay_info tb left join scms_merchants_info m on m.ID = tb.MERCHANTS_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_pay_info tb left join scms_merchants_info m on m.ID = tb.MERCHANTS_ID "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsPayInfo scmsPayInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsPayInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID ASC ", " \"id\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsPayInfo scmsPayInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsPayInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsPayInfo scmsPayInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND (tb.TYPE = 1 or ( tb.TYPE = 2 and tb.MERCHANTS_ID = :merchantsId )) ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        String type = MapUtils.getString(params, "type");
        String name = MapUtils.getString(params, "name");
        if(!StringUtils.isBlank(type)) {
            sqlParams.querySql.append(" AND tb.TYPE = :type ");
            sqlParams.paramsList.add("type");
            sqlParams.valueList.add(type);
        }
        if(!StringUtils.isBlank(name)) {
            sqlParams.querySql.append(getLikeSql("tb.PAY_NAME", ":name"));
            sqlParams.paramsList.add("name");
            sqlParams.valueList.add(name);
        }
        return sqlParams;
	}
}