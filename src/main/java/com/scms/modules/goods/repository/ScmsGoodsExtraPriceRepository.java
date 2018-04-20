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

import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;
import com.scms.modules.goods.repository.custom.ScmsGoodsExtraPriceRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsGoodsExtraPriceRepository extends JpaRepository<ScmsGoodsExtraPrice, Long>, JpaSpecificationExecutor<ScmsGoodsExtraPrice>, ScmsGoodsExtraPriceRepositoryCustom {
	
    /**
     * 删除CategoryRecur
     * @param categoryChildId
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM scms_goods_extra_price "
            + "WHERE GOODS_ID = :goodsId ", nativeQuery = true)
    public void delByGoodsId(@Param("goodsId")Long goodsId);
    
	
}