/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.order.entity.ScmsOrderPay;
import com.scms.modules.order.repository.custom.ScmsOrderPayRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsOrderPayRepository extends JpaRepository<ScmsOrderPay, Long>, JpaSpecificationExecutor<ScmsOrderPay>, ScmsOrderPayRepositoryCustom {
	
	
	
}