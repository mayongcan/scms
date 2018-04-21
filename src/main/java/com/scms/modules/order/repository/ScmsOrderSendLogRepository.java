/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.order.entity.ScmsOrderSendLog;
import com.scms.modules.order.repository.custom.ScmsOrderSendLogRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsOrderSendLogRepository extends JpaRepository<ScmsOrderSendLog, Long>, JpaSpecificationExecutor<ScmsOrderSendLog>, ScmsOrderSendLogRepositoryCustom {
	
	
	
}