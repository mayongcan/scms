/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.repository.custom.ScmsOrderInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsOrderInfoRepository extends JpaRepository<ScmsOrderInfo, Long>, JpaSpecificationExecutor<ScmsOrderInfo>, ScmsOrderInfoRepositoryCustom {
	
	
	/**
	 * 删除信息（将信息的IS_VALID设置为N）
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE scms_order_info "
			+ "SET IS_VALID = :isValid "
			+ "WHERE ID IN (:idList)", nativeQuery = true)
	public void delEntity(@Param("isValid")String isValid, @Param("idList")List<Long> idList);
	
}