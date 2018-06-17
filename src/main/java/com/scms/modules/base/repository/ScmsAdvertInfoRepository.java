/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.base.entity.ScmsAdvertInfo;
import com.scms.modules.base.repository.custom.ScmsAdvertInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsAdvertInfoRepository extends JpaRepository<ScmsAdvertInfo, Long>, JpaSpecificationExecutor<ScmsAdvertInfo>, ScmsAdvertInfoRepositoryCustom {
	
    /**
     * 更新排序ID
     * @param dictDataId
     * @param dispOrder
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_advert_info "
            + "SET DISP_ORDER = :dispOrder "
            + "WHERE ID = :id ", nativeQuery = true)
    public void updateDispOrderById(@Param("id")Long id, @Param("dispOrder")Long dispOrder);
	
}