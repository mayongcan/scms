/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.entity.ScmsShopInfo;
import com.scms.modules.base.repository.custom.ScmsShopInfoRepositoryCustom;

public class ScmsShopInfoRepositoryImpl extends BaseRepository implements ScmsShopInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SHOP_NAME as \"shopName\", tb.SHOP_ADDR as \"shopAddr\", tb.SHOP_DESC as \"shopDesc\", "
	        + "tb.SHOP_LOGO as \"shopLogo\", tb.SHOP_PHONE as \"shopPhone\", tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", tb.IS_VALID as \"isValid\" "
			+ "FROM scms_shop_info tb "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_shop_info tb "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsShopInfo scmsShopInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsShopInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID ASC ", " \"id\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsShopInfo scmsShopInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsShopInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsShopInfo scmsShopInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsShopInfo != null && scmsShopInfo.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsShopInfo.getMerchantsId());
		}
		if (scmsShopInfo != null && !StringUtils.isBlank(scmsShopInfo.getShopName())) {
            sqlParams.querySql.append(getLikeSql("tb.SHOP_NAME", ":shopName"));
			sqlParams.paramsList.add("shopName");
			sqlParams.valueList.add(scmsShopInfo.getShopName());
		}
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getShopKeyVal(Long merchantsId) {
        //组合查询条件
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append("SELECT tb.ID as ID, tb.SHOP_NAME as NAME FROM scms_shop_info tb WHERE tb.MERCHANTS_ID = :merchantsId ");
        sqlParams.paramsList.add("merchantsId");
        sqlParams.valueList.add(merchantsId);
        sqlParams.querySql.append(" ORDER BY ID ASC ");
        return getResultList(sqlParams);
    }
}