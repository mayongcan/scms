/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.entity;

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
 * ScmsOrderGoods数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_order_goods")
public class ScmsOrderGoods implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsOrderGoodsIdGenerator")
	@TableGenerator(name = "ScmsOrderGoodsIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_ORDER_GOODS_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 订单ID
	@Column(name = "ORDER_ID", precision = 10, scale = 0)
	private Long orderId;
	
	// 商品ID
	@Column(name = "GOODS_ID", precision = 10, scale = 0)
	private Long goodsId;
	
	// 商品名称（冗余）
	@Column(name = "GOODS_NAME", length = 256)
	private String goodsName;
	
	// 商品货号（冗余）
	@Column(name = "GOODS_SERIAL_NUM", length = 64)
	private String goodsSerialNum;
	
	// 商品条码（冗余）
	@Column(name = "GOODS_BARCODE", length = 64)
	private String goodsBarcode;
	
	// 商品图片
	@Column(name = "GOODS_PHOTO", length = 1024)
	private String goodsPhoto;
	
	// 商品颜色ID（冗余）
	@Column(name = "GOODS_COLOR_ID", precision = 10, scale = 0)
	private Long goodsColorId;
	
	// 商品颜色名称（冗余）
	@Column(name = "GOODS_COLOR_NAME", length = 32)
	private String goodsColorName;

    @Column(name = "GOODS_SIZE_ID", precision = 10, scale = 0)
    private Long goodsSizeId;
    
	// 商品商品尺码（冗余）
	@Column(name = "GOODS_SIZE_NAME", length = 32)
	private String goodsSizeName;
	
	// 商品材质ID（冗余）
	@Column(name = "GOODS_TEXTURE_ID", precision = 10, scale = 0)
	private Long goodsTextureId;
	
	// 商品材质名称（冗余）
	@Column(name = "GOODS_TEXTURE_NAME", length = 128)
	private String goodsTextureName;
	
	// 商品销售价单价（冗余）
	@Column(name = "GOODS_SALE_PRICE", precision = 10, scale = 2)
	private Double goodsSalePrice;
	
	// 商品进货单价（冗余）
	@Column(name = "GOODS_PURCHASE_PRICE", precision = 10, scale = 2)
	private Double goodsPurchasePrice;
	
	// 订单实际单价
	@Column(name = "GOODS_ORDER_PRICE", precision = 10, scale = 2)
	private Double goodsOrderPrice;
	
	// 单个商品利润
	@Column(name = "GOODS_ORDER_PROFIT", precision = 10, scale = 2)
	private Double goodsOrderProfit;
	
	// 商品数量(下单数量)
	@Column(name = "GOODS_ORDER_NUM", precision = 10, scale = 0)
	private Long goodsOrderNum;
	
}
