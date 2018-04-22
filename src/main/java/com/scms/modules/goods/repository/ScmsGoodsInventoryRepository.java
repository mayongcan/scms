/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.repository.custom.ScmsGoodsInventoryRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsGoodsInventoryRepository extends JpaRepository<ScmsGoodsInventory, Long>, JpaSpecificationExecutor<ScmsGoodsInventory>, ScmsGoodsInventoryRepositoryCustom {
	
    List<ScmsGoodsInventory> findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(Long shopId, Long goodsId, Long colorId, Long inventorySizeId, Long textureId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_goods_inventory "
            + "SET INVENTORY_NUM = :inventoryNum "
            + "WHERE SHOP_ID = :shopId AND GOODS_ID = :goodsId AND COLOR_ID = :colorId AND INVENTORY_SIZE_ID = :inventorySizeId AND TEXTURE_ID = :textureId ", nativeQuery = true)
    public void updateGoodsInventoryNum(@Param("inventoryNum")Long inventoryNum, @Param("shopId")Long shopId, @Param("goodsId")Long goodsId
            , @Param("colorId")Long colorId, @Param("inventorySizeId")Long inventorySizeId, @Param("textureId")Long textureId);
    
	
}