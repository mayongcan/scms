/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.base.entity.ScmsPrintInfo;
import com.scms.modules.base.repository.custom.ScmsPrintInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsPrintInfoRepository extends JpaRepository<ScmsPrintInfo, Long>, JpaSpecificationExecutor<ScmsPrintInfo>, ScmsPrintInfoRepositoryCustom {
	
	
	
}