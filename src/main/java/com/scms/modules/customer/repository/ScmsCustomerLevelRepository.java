/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.scms.modules.customer.entity.ScmsCustomerLevel;
import com.scms.modules.customer.repository.custom.ScmsCustomerLevelRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsCustomerLevelRepository extends JpaRepository<ScmsCustomerLevel, Long>, JpaSpecificationExecutor<ScmsCustomerLevel>, ScmsCustomerLevelRepositoryCustom {

    List<ScmsCustomerLevel> findByLevelNameAndType(String levelName, String type);
    
    List<ScmsCustomerLevel> findByLevelNameAndTypeAndMerchantsId(String levelName, String type, Long merchantsId);
}