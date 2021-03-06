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
@Table(name = "scms_inventory_transfer_goods")
public class ScmsInventoryTransferGoods implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsInventoryTransferGoodsIdGenerator")
	@TableGenerator(name = "ScmsInventoryTransferGoodsIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_INVENTORY_TRANSFER_GOODS_PK", allocationSize = 1)
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
	
	// 商品图片
	@Column(name = "GOODS_PHOTO", length = 1024)
	private String goodsPhoto;
	
}
