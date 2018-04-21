/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderModifyLog;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderModifyLogRepositoryCustom {

	/**
	 * 获取ScmsOrderModifyLog列表
	 * @param scmsOrderModifyLog
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderModifyLog scmsOrderModifyLog, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderModifyLog列表总数
	 * @param scmsOrderModifyLog
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderModifyLog scmsOrderModifyLog, Map<String, Object> params);
}