/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.base.entity.ScmsAdvertInfo;
import com.scms.modules.base.repository.custom.ScmsAdvertInfoRepositoryCustom;

public class ScmsAdvertInfoRepositoryImpl extends BaseRepository implements ScmsAdvertInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.POSITION as \"position\", tb.ADVERT_NAME as \"advertName\",  tb.ADVERT_IMAGE as \"advertImage\", "
	        + "tb.ADVERT_DESC as \"advertDesc\", tb.ACTION_URL as \"actionUrl\", tb.DISP_ORDER as \"dispOrder\", tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\" "
			+ "FROM scms_advert_info tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_advert_info tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsAdvertInfo scmsAdvertInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsAdvertInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.DISP_ORDER ASC ", " \"dispOrder\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsAdvertInfo scmsAdvertInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsAdvertInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsAdvertInfo scmsAdvertInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsAdvertInfo != null && scmsAdvertInfo.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsAdvertInfo.getMerchantsId());
		}
        if (scmsAdvertInfo != null && !StringUtils.isBlank(scmsAdvertInfo.getPosition())) {
            sqlParams.querySql.append(" AND tb.POSITION = :position ");
            sqlParams.paramsList.add("position");
            sqlParams.valueList.add(scmsAdvertInfo.getPosition());
        }
        if (scmsAdvertInfo != null && !StringUtils.isBlank(scmsAdvertInfo.getAdvertName())) {
            sqlParams.querySql.append(getLikeSql("tb.ADVERT_NAME", ":advertName"));
            sqlParams.paramsList.add("advertName");
            sqlParams.valueList.add(scmsAdvertInfo.getAdvertName());
        }
        return sqlParams;
	}
}