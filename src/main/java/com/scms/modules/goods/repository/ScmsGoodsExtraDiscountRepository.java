/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.goods.entity.ScmsGoodsExtraDiscount;
import com.scms.modules.goods.repository.custom.ScmsGoodsExtraDiscountRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsGoodsExtraDiscountRepository extends JpaRepository<ScmsGoodsExtraDiscount, Long>, JpaSpecificationExecutor<ScmsGoodsExtraDiscount>, ScmsGoodsExtraDiscountRepositoryCustom {
	
    /**
     * 删除CategoryRecur
     * @param categoryChildId
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM scms_goods_extra_discount "
            + "WHERE GOODS_ID = :goodsId ", nativeQuery = true)
    public void delByGoodsId(@Param("goodsId")Long goodsId);
    
	
}