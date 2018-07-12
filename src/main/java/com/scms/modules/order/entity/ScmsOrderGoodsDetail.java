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
@Table(name = "scms_order_goods_detail")
public class ScmsOrderGoodsDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsOrderGoodsDetailIdGenerator")
	@TableGenerator(name = "ScmsOrderGoodsDetailIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_ORDER_GOODS_DETAIL_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
    
    // 订单ID
    @Column(name = "ORDER_ID", precision = 10, scale = 0)
    private Long orderId;
	
	// 订单商品ID
	@Column(name = "DETAIL_ID", precision = 10, scale = 0)
	private Long detailId;
	
	// 商品条码（冗余）
	@Column(name = "GOODS_BARCODE", length = 64)
	private String goodsBarcode;
	
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
	
	// 商品销售价单价（冗余）
	@Column(name = "GOODS_SALE_PRICE", precision = 10, scale = 2)
	private Double goodsSalePrice;
	
	// 商品进货单价（冗余）
	@Column(name = "GOODS_PURCHASE_PRICE", precision = 10, scale = 2)
	private Double goodsPurchasePrice;
	
	// 订单实际单价
	@Column(name = "GOODS_ORDER_PRICE", precision = 10, scale = 2)
	private Double goodsOrderPrice;

    @Column(name = "GOODS_DISCOUNT", precision = 10, scale = 0)
    private Long goodsDiscount;
	
	// 单个商品利润
	@Column(name = "GOODS_ORDER_PROFIT", precision = 10, scale = 2)
	private Double goodsOrderProfit;
	
	// 商品数量(下单数量)
	@Column(name = "GOODS_ORDER_NUM", precision = 10, scale = 0)
	private Long goodsOrderNum;

    @Column(name = "SEND_STATUS", length = 2)
    private String sendStatus;

    @Column(name = "RECEIVE_STATUS", length = 2)
    private String receiveStatus;
}
