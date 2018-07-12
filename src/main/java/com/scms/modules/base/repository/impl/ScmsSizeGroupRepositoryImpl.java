/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.base.entity.ScmsSizeGroup;
import com.scms.modules.base.repository.custom.ScmsSizeGroupRepositoryCustom;

public class ScmsSizeGroupRepositoryImpl extends BaseRepository implements ScmsSizeGroupRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.GROUP_NAME as \"groupName\", tb.SIZE_ID_LIST as \"sizeIdList\", tb.SIZE_NAME_LIST as \"sizeNameList\" "
			+ "FROM scms_size_group tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_size_group tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsSizeGroup scmsSizeGroup, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsSizeGroup, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsSizeGroup scmsSizeGroup, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsSizeGroup, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsSizeGroup scmsSizeGroup, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsSizeGroup != null && scmsSizeGroup.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsSizeGroup.getMerchantsId());
		}
		if (scmsSizeGroup != null && !StringUtils.isBlank(scmsSizeGroup.getGroupName())) {
            sqlParams.querySql.append(getLikeSql("tb.GROUP_NAME", ":groupName"));
            sqlParams.paramsList.add("groupName");
            sqlParams.valueList.add(scmsSizeGroup.getGroupName());
        }
        return sqlParams;
	}
}