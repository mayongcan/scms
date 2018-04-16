/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.base.entity.ScmsColorInfo;
import com.scms.modules.base.repository.custom.ScmsColorInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsColorInfoRepository extends JpaRepository<ScmsColorInfo, Long>, JpaSpecificationExecutor<ScmsColorInfo>, ScmsColorInfoRepositoryCustom {

    List<ScmsColorInfo> findByColorNameAndType(String colorName, String type);
    
    List<ScmsColorInfo> findByColorNameAndTypeAndMerchantsId(String colorName, String type, Long merchantsId);
	
}