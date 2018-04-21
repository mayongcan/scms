/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderSendLog;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderSendLogRepositoryCustom {

	/**
	 * 获取ScmsOrderSendLog列表
	 * @param scmsOrderSendLog
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderSendLog列表总数
	 * @param scmsOrderSendLog
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params);
}