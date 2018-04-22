/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scms.modules.base.entity.ScmsTransportInfo;
import com.scms.modules.base.repository.custom.ScmsTransportInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsTransportInfoRepository extends JpaRepository<ScmsTransportInfo, Long>, JpaSpecificationExecutor<ScmsTransportInfo>, ScmsTransportInfoRepositoryCustom {

    List<ScmsTransportInfo> findByTransportNameAndType(String transportName, String type);
    
    List<ScmsTransportInfo> findByTransportNameAndTypeAndMerchantsId(String transportName, String type, Long merchantsId);
    
}