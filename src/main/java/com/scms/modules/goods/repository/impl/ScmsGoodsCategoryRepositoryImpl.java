/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.goods.entity.ScmsGoodsCategory;
import com.scms.modules.goods.repository.custom.ScmsGoodsCategoryRepositoryCustom;

public class ScmsGoodsCategoryRepositoryImpl extends BaseRepository implements ScmsGoodsCategoryRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.PARENT_ID as \"parentId\", tb.CATEGORY_NAME as \"categoryName\", tb.CATEGORY_DESC as \"categoryDesc\", "
	        + "tb.CATEGORY_PHOTO as \"categoryPhoto\", tb.TYPE as \"type\", tb.MERCHANTS_ID as \"merchantsId\", tb.DISP_ORDER as \"dispOrder\", tb.CREATE_BY as \"createBy\", "
	        + "tb.CREATE_DATE as \"createDate\", m.MERCHANTS_NAME as \"merchantsName\", user.USER_NAME as \"createByName\" "
			+ "FROM scms_goods_category tb left join scms_merchants_info m on m.ID = tb.MERCHANTS_ID "
			+ "left join sys_user_info user on user.USER_ID = tb.CREATE_BY "
			+ "WHERE tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_goods_category tb left join scms_merchants_info m on m.ID = tb.MERCHANTS_ID "
			+ "WHERE tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsGoodsCategory, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.PARENT_ID, tb.DISP_ORDER, tb.ID ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsGoodsCategory, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params){
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
            sqlParams.querySql.append(getLikeSql("tb.CATEGORY_NAME", ":name"));
            sqlParams.paramsList.add("name");
            sqlParams.valueList.add(name);
        }
        return sqlParams;
	}
}