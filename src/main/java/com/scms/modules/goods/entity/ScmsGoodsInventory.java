/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ScmsGoodsInventory数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_goods_inventory")
public class ScmsGoodsInventory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsGoodsInventoryIdGenerator")
	@TableGenerator(name = "ScmsGoodsInventoryIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_GOODS_INVENTORY_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 所属店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
	
	// 商品ID
	@Column(name = "GOODS_ID", precision = 10, scale = 0)
	private Long goodsId;
	
	// 商品条码
	@Column(name = "GOODS_BARCODE", length = 64)
	private String goodsBarcode;
	
	// 商品颜色ID
	@Column(name = "COLOR_ID", precision = 10, scale = 0)
	private Long colorId;
	
	// 商品颜色名称（冗余）
	@Column(name = "COLOR_NAME", length = 32)
	private String colorName;
	
	// 库存商品尺码
	@Column(name = "INVENTORY_SIZE", length = 32)
	private String inventorySize;
	
	// 商品材质ID
	@Column(name = "TEXTURE_ID", precision = 10, scale = 0)
	private Long textureId;
	
	// 商品材质名称
	@Column(name = "TEXTURE_NAME", length = 128)
	private String textureName;
	
	// 库存数量
	@Column(name = "INVENTORY_NUM", precision = 10, scale = 0)
	private Long inventoryNum;
	
	// 库存商品图片
	@Column(name = "INVENTORY_PHOTO", length = 1024)
	private String inventoryPhoto;
	
}
