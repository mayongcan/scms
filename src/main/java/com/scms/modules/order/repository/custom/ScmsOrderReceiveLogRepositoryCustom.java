/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderReceiveLog;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderReceiveLogRepositoryCustom {

	/**
	 * 获取ScmsOrderReceiveLog列表
	 * @param scmsOrderReceiveLog
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderReceiveLog列表总数
	 * @param scmsOrderReceiveLog
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params);
}