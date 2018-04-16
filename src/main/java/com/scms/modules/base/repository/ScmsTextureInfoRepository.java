/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.scms.modules.base.entity.ScmsTextureInfo;
import com.scms.modules.base.repository.custom.ScmsTextureInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsTextureInfoRepository extends JpaRepository<ScmsTextureInfo, Long>, JpaSpecificationExecutor<ScmsTextureInfo>, ScmsTextureInfoRepositoryCustom {

    List<ScmsTextureInfo> findByTextureNameAndType(String textureName, String type);
    
    List<ScmsTextureInfo> findByTextureNameAndTypeAndMerchantsId(String textureName, String type, Long merchantsId);
    
}