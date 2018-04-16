/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.base.entity.ScmsFeedbackInfo;
import com.scms.modules.base.repository.custom.ScmsFeedbackInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsFeedbackInfoRepository extends JpaRepository<ScmsFeedbackInfo, Long>, JpaSpecificationExecutor<ScmsFeedbackInfo>, ScmsFeedbackInfoRepositoryCustom {
	
	
	
}