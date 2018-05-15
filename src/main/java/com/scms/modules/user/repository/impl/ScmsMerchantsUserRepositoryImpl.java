/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.user.entity.ScmsMerchantsUser;
import com.scms.modules.user.repository.custom.ScmsMerchantsUserRepositoryCustom;

public class ScmsMerchantsUserRepositoryImpl extends BaseRepository implements ScmsMerchantsUserRepositoryCustom{

	private static final String SQL_GET_MERCHANTS_USER_LIST = "SELECT tb.user_id as \"userId\", tb.user_name as \"userName\", tb.email as \"email\", tb.user_code as \"userCode\", tb.sex as \"sex\", tb.photo as \"photo\", "
	                + "DATE_FORMAT(tb.birthday,'%Y-%m-%d') as \"birthday\", tb.mobile as \"mobile\", tb.phone as \"phone\", tb.CREDENTIALS_TYPE as \"credentialsType\", tb.CREDENTIALS_NUM as \"credentialsNum\", "
                    + "DATE_FORMAT(tb.create_date,'%Y-%m-%d') as \"createDate\", tb.ADDRESS as \"address\", DATE_FORMAT(ul.valid_begin_date,'%Y-%m-%d') as \"beginDate\", DATE_FORMAT(ul.valid_end_date,'%Y-%m-%d') as \"endDate\", "
                    + "ul.lock_begin_date as \"lockBeginDate\", ul.lock_end_date as \"lockEndDate\", ul.lock_reason as \"lockReason\", DATE_FORMAT(ul.last_logon_date,'%Y-%m-%d') as \"lastLogonDate\", "
                    + "ul.last_logon_ip as \"lastLogonIP\", ul.access_ipaddress as \"ipAddress\", ul.online_status as \"onLineStatus\", "
                    + "si.ID as \"shopId\", si.SHOP_NAME as \"shopName\", mu.IS_ADMIN as \"isAdmin\", mu.IS_BLOCK as \"isBlock\", mu.ID as \"merchantsUserId\", mu.PRIVILEGE_CONTENT as \"privilegeContent\" "
            + "FROM sys_user_info tb left join scms_merchants_user mu on mu.USER_ID = tb.USER_ID "
                    + "left join scms_shop_info si on si.ID = mu.SHOP_ID "
                    + "left join sys_user_logon ul on tb.user_id = ul.user_id "
            + "WHERE tb.is_valid = 'Y' ";

	private static final String SQL_GET_MERCHANTS_USER_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM sys_user_info tb left join scms_merchants_user mu on mu.USER_ID = tb.USER_ID "
            + "left join scms_shop_info si on si.ID = mu.SHOP_ID "
            + "left join sys_user_logon ul on tb.user_id = ul.user_id "
            + "WHERE tb.is_valid = 'Y' ";
	
	public List<Map<String, Object>> getMerchantsUserList(ScmsMerchantsUser scmsMerchantsUser, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genMerchantsUserListWhere(SQL_GET_MERCHANTS_USER_LIST, scmsMerchantsUser, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.USER_ID DESC ", " \"userId\" DESC ");
		return getResultList(sqlParams);
	}

	public int getMerchantsUserListCount(ScmsMerchantsUser scmsMerchantsUser, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genMerchantsUserListWhere(SQL_GET_MERCHANTS_USER_LIST_COUNT, scmsMerchantsUser, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genMerchantsUserListWhere(String sql, ScmsMerchantsUser scmsMerchantsUser, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long shopId = MapUtils.getLong(params, "shopId", null);
        Long userId = MapUtils.getLong(params, "userId", null);
        String dataType = MapUtils.getString(params, "dataType");
        String tenantsId = MapUtils.getString(params, "tenantsId");
        String userName = MapUtils.getString(params, "userName");
        String userCode = MapUtils.getString(params, "userCode");
        String roleIdList = MapUtils.getString(params, "roleIdList");
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND mu.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        if (shopId != null) {
            sqlParams.querySql.append(" AND mu.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(shopId);
        }
        if (userId != null) {
            sqlParams.querySql.append(" AND tb.USER_ID = :userId ");
            sqlParams.paramsList.add("userId");
            sqlParams.valueList.add(userId);
        }
        if("notIn".equals(dataType)) {
            sqlParams.querySql.append(" AND tb.USER_ID not in(select distinct(USER_ID) from scms_merchants_user) ");
        }
        if(tenantsId != null) {
            sqlParams.querySql.append(" AND tb.TENANTS_ID = :tenantsId ");
            sqlParams.paramsList.add("tenantsId");
            sqlParams.valueList.add(tenantsId);
        }
        if(!StringUtils.isBlank(userName)) {
            sqlParams.querySql.append(this.getLikeSql("tb.USER_NAME", ":userName"));
            sqlParams.paramsList.add("userName");
            sqlParams.valueList.add(userName);
        }
        if(!StringUtils.isBlank(userCode)) {
            sqlParams.querySql.append(this.getLikeSql("tb.USER_CODE", ":userCode"));
            sqlParams.paramsList.add("userCode");
            sqlParams.valueList.add(userCode);
        }
        //过滤角色列表
        if(!StringUtils.isBlank(roleIdList)) {
            String array[] = roleIdList.split(",");
            List<Long> roleList = new ArrayList<Long>();
            Long id = null;
            for(String str : array) {
                id = StringUtils.toLong(str, null);
                if(id != null) roleList.add(id);
            }
            sqlParams.querySql.append(" AND tb.USER_ID in(select distinct(USER_ID) from sys_user_role where ROLE_ID in(:roleList)) ");
            sqlParams.paramsList.add("roleList");
            sqlParams.valueList.add(roleList);
        }
        String isBlock = MapUtils.getString(params, "isBlock");
        if(!StringUtils.isBlank(isBlock)) {
            sqlParams.querySql.append(" AND mu.IS_BLOCK = :isBlock ");
            sqlParams.paramsList.add("isBlock");
            sqlParams.valueList.add(isBlock);
        }
        return sqlParams;
	}
}