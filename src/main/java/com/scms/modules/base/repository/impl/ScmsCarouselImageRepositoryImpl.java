/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.base.entity.ScmsCarouselImage;
import com.scms.modules.base.repository.custom.ScmsCarouselImageRepositoryCustom;

public class ScmsCarouselImageRepositoryImpl extends BaseRepository implements ScmsCarouselImageRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.IMAGE_NAME as \"imageName\", tb.IMAGE_PATH as \"imagePath\", "
	        + "tb.DISP_ORDER as \"dispOrder\", tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\" "
			+ "FROM scms_carousel_image tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_carousel_image tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsCarouselImage scmsCarouselImage, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsCarouselImage, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.DISP_ORDER ASC ", " \"dispOrder\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsCarouselImage scmsCarouselImage, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsCarouselImage, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsCarouselImage scmsCarouselImage, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsCarouselImage != null && scmsCarouselImage.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsCarouselImage.getMerchantsId());
		}
        return sqlParams;
	}
}