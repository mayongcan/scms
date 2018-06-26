/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.base.entity.ScmsPrintRemote;
import com.scms.modules.base.repository.custom.ScmsPrintRemoteRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsPrintRemoteRepository extends JpaRepository<ScmsPrintRemote, Long>, JpaSpecificationExecutor<ScmsPrintRemote>, ScmsPrintRemoteRepositoryCustom {
	
    List<ScmsPrintRemote> findByMerchantsIdAndOrderNum(Long merchantsId, String orderNum);
	
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_print_remote "
            + "SET STATUS = :status, PRINT_BY = :printBy, PRINT_DATE = :printDate "
            + "WHERE ID = :id ", nativeQuery = true)
    public void updateStatus(@Param("id")Long id, @Param("status")String status, @Param("printBy")Long printBy, @Param("printDate")Date printDate);
}