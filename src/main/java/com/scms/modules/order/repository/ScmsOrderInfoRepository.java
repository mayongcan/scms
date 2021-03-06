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
	

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_order_info "
            + "SET ORDER_STATUS = :orderStatus "
            + "WHERE ID IN (:idList)", nativeQuery = true)
    public void updateOrderStatus(@Param("orderStatus")String orderStatus, @Param("idList")List<Long> idList);
    
    /**
     * 更新发货状态
     * @param orderSendStatus
     * @param idList
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_order_info "
            + "SET ORDER_SEND_STATUS = :orderSendStatus "
            + "WHERE ID IN (:idList)", nativeQuery = true)
    public void updateOrderSendStatus(@Param("orderSendStatus")String orderSendStatus, @Param("idList")List<Long> idList);

    /**
     * 更新发货状态
     * @param orderSendStatus
     * @param idList
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_order_info "
            + "SET ORDER_SEND_STATUS = :orderSendStatus "
            + "WHERE ID = :id ", nativeQuery = true)
    public void updateOrderSendStatus(@Param("orderSendStatus")String orderSendStatus, @Param("id")Long id);
    
    /**
     * 更新收货状态
     * @param orderSendStatus
     * @param idList
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_order_info "
            + "SET ORDER_RECEIVE_STATUS = :orderReceiveStatus "
            + "WHERE ID IN (:idList)", nativeQuery = true)
    public void updateOrderReceiveStatus(@Param("orderReceiveStatus")String orderReceiveStatus, @Param("idList")List<Long> idList);
    
    /**
     * 更新收货状态
     * @param orderSendStatus
     * @param idList
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_order_info "
            + "SET ORDER_RECEIVE_STATUS = :orderReceiveStatus "
            + "WHERE ID = :id ", nativeQuery = true)
    public void updateOrderReceiveStatus(@Param("orderReceiveStatus")String orderReceiveStatus, @Param("id")Long id);
    
	
}