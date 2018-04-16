/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.base.entity.ScmsSizeInfo;
import com.scms.modules.base.repository.custom.ScmsSizeInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsSizeInfoRepository extends JpaRepository<ScmsSizeInfo, Long>, JpaSpecificationExecutor<ScmsSizeInfo>, ScmsSizeInfoRepositoryCustom {
	
    List<ScmsSizeInfo> findBySizeNameAndType(String sizeName, String type);
	
    List<ScmsSizeInfo> findBySizeNameAndTypeAndMerchantsId(String sizeName, String type, Long merchantsId);
}