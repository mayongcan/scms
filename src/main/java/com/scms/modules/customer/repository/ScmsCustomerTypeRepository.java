/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.scms.modules.customer.entity.ScmsCustomerType;
import com.scms.modules.customer.repository.custom.ScmsCustomerTypeRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsCustomerTypeRepository extends JpaRepository<ScmsCustomerType, Long>, JpaSpecificationExecutor<ScmsCustomerType>, ScmsCustomerTypeRepositoryCustom {

    List<ScmsCustomerType> findByTypeNameAndType(String typeName, String type);
    
    List<ScmsCustomerType> findByTypeNameAndTypeAndMerchantsId(String typeName, String type, Long merchantsId);
}