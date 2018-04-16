/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.user.entity.ScmsCommissionRule;
import com.scms.modules.user.repository.custom.ScmsCommissionRuleRepositoryCustom;

public class ScmsCommissionRuleRepositoryImpl extends BaseRepository implements ScmsCommissionRuleRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SHOP_ID as \"shopId\", tb.RULE_NAME as \"ruleName\", tb.CLEARING_TYPE as \"clearingType\", tb.CLEARING_PERIOD as \"clearingPeriod\", tb.CLEARING_START as \"clearingStart\", tb.COMMISSION_PERCENT as \"commissionPercent\", tb.MODIFY_BY as \"modifyBy\", tb.MODIFY_DATE as \"modifyDate\", tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", tb.IS_VALID as \"isValid\" "
			+ "FROM scms_commission_rule tb "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_commission_rule tb "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsCommissionRule scmsCommissionRule, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsCommissionRule, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsCommissionRule scmsCommissionRule, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsCommissionRule, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsCommissionRule scmsCommissionRule, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsCommissionRule != null && scmsCommissionRule.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsCommissionRule.getMerchantsId());
		}
		if (scmsCommissionRule != null && scmsCommissionRule.getShopId() != null) {
			sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
			sqlParams.paramsList.add("shopId");
			sqlParams.valueList.add(scmsCommissionRule.getShopId());
		}
		if (scmsCommissionRule != null && !StringUtils.isBlank(scmsCommissionRule.getRuleName())) {
            sqlParams.querySql.append(getLikeSql("tb.RULE_NAME", ":ruleName"));
			sqlParams.paramsList.add("ruleName");
			sqlParams.valueList.add(scmsCommissionRule.getRuleName());
		}
		if (scmsCommissionRule != null && !StringUtils.isBlank(scmsCommissionRule.getClearingType())) {
			sqlParams.querySql.append(" AND tb.CLEARING_TYPE = :clearingType ");
			sqlParams.paramsList.add("clearingType");
			sqlParams.valueList.add(scmsCommissionRule.getClearingType());
		}
		if (scmsCommissionRule != null && !StringUtils.isBlank(scmsCommissionRule.getClearingPeriod())) {
			sqlParams.querySql.append(" AND tb.CLEARING_PERIOD = :clearingPeriod ");
			sqlParams.paramsList.add("clearingPeriod");
			sqlParams.valueList.add(scmsCommissionRule.getClearingPeriod());
		}
        return sqlParams;
	}
}