/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.scms.modules.base.entity.ScmsTagInfo;
import com.scms.modules.base.repository.custom.ScmsTagInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsTagInfoRepository extends JpaRepository<ScmsTagInfo, Long>, JpaSpecificationExecutor<ScmsTagInfo>, ScmsTagInfoRepositoryCustom {

    List<ScmsTagInfo> findByTagTypeAndTagNameAndType(String tagType, String tagName, String type);
    
    List<ScmsTagInfo> findByTagTypeAndTagNameAndTypeAndMerchantsId(String tagType, String tagName, String type, Long merchantsId);
}