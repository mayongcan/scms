/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.goods.entity.ScmsGoodsModifyLog;
import com.scms.modules.goods.repository.custom.ScmsGoodsModifyLogRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsGoodsModifyLogRepository extends JpaRepository<ScmsGoodsModifyLog, Long>, JpaSpecificationExecutor<ScmsGoodsModifyLog>, ScmsGoodsModifyLogRepositoryCustom {
	
	
	
}