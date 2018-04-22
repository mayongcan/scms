/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.scms.modules.base.entity.ScmsPayInfo;
import com.scms.modules.base.repository.custom.ScmsPayInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsPayInfoRepository extends JpaRepository<ScmsPayInfo, Long>, JpaSpecificationExecutor<ScmsPayInfo>, ScmsPayInfoRepositoryCustom {
	
    List<ScmsPayInfo> findByPayNameAndType(String payName, String type);
    
    List<ScmsPayInfo> findByPayNameAndTypeAndMerchantsId(String payName, String type, Long merchantsId);
    
}