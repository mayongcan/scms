/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.base.entity.ScmsSizeGroup;
import com.scms.modules.base.repository.custom.ScmsSizeGroupRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsSizeGroupRepository extends JpaRepository<ScmsSizeGroup, Long>, JpaSpecificationExecutor<ScmsSizeGroup>, ScmsSizeGroupRepositoryCustom {
	
	
	
}