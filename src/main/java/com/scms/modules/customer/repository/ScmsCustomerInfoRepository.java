/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.customer.entity.ScmsCustomerInfo;
import com.scms.modules.customer.repository.custom.ScmsCustomerInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsCustomerInfoRepository extends JpaRepository<ScmsCustomerInfo, Long>, JpaSpecificationExecutor<ScmsCustomerInfo>, ScmsCustomerInfoRepositoryCustom {
	
	
	/**
	 * 删除信息（将信息的IS_VALID设置为N）
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE scms_customer_info "
			+ "SET IS_VALID = :isValid "
			+ "WHERE ID IN (:idList)", nativeQuery = true)
	public void delEntity(@Param("isValid")String isValid, @Param("idList")List<Long> idList);

	/**
	 * 更新客户余额
	 * @param customerBalance
	 * @param id
	 */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_customer_info "
            + "SET CUSTOMER_BALANCE = :customerBalance "
            + "WHERE ID = :id", nativeQuery = true)
    public void updateCustomerBalance(@Param("customerBalance")Double customerBalance, @Param("id")Long id);
	
}