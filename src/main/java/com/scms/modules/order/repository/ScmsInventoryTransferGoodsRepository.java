/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.order.entity.ScmsInventoryTransferGoods;
import com.scms.modules.order.repository.custom.ScmsInventoryTransferGoodsRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsInventoryTransferGoodsRepository extends JpaRepository<ScmsInventoryTransferGoods, Long>, JpaSpecificationExecutor<ScmsInventoryTransferGoods>, ScmsInventoryTransferGoodsRepositoryCustom {
	
	List<ScmsInventoryTransferGoods> findByOrderId(Long orderId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM scms_inventory_transfer_goods "
            + "WHERE ORDER_ID = :orderId ", nativeQuery = true)
    public void delByOrderId(@Param("orderId")Long orderId);
}