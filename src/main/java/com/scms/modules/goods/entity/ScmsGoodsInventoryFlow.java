/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gimplatform.core.annotation.CustomerDateAndTimeDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ScmsGoodsInventoryFlow数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_goods_inventory_flow")
public class ScmsGoodsInventoryFlow implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsGoodsInventoryFlowIdGenerator")
	@TableGenerator(name = "ScmsGoodsInventoryFlowIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_GOODS_INVENTORY_FLOW_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
    
    // 商户ID
    @Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
    private Long merchantsId;
	
	// 所属店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
	
	// 商品ID
	@Column(name = "GOODS_ID", precision = 10, scale = 0)
	private Long goodsId;
	
	// 商品条码（冗余）
	@Column(name = "GOODS_BARCODE", length = 64)
	private String goodsBarcode;
	
	// 商品颜色ID（冗余）
	@Column(name = "COLOR_ID", precision = 10, scale = 0)
	private Long colorId;
	
	// 商品颜色名称（冗余）
	@Column(name = "COLOR_NAME", length = 32)
	private String colorName;
	
	// 商品材质ID（冗余）
	@Column(name = "TEXTURE_ID", precision = 10, scale = 0)
	private Long textureId;
	
	// 商品材质名称（冗余）
	@Column(name = "TEXTURE_NAME", length = 128)
	private String textureName;
	
	// 商品商品尺码ID（冗余）
	@Column(name = "SIZE_ID", precision = 10, scale = 0)
	private Long sizeId;
	
	// 商品商品尺码名称（冗余）
	@Column(name = "SIZE_NAME", length = 32)
	private String sizeName;
	
	// 库存旧数量
	@Column(name = "OLD_NUM", precision = 10, scale = 0)
	private Long oldNum;
	
	// 库存新数量
	@Column(name = "NEW_NUM", precision = 10, scale = 0)
	private Long newNum;
	
	// 操作名称
	@Column(name = "OPERATE_NAME", length = 128)
	private String operateName;
	
	// 订单号
	@Column(name = "ORDER_NUM", length = 64)
	private String orderNum;
	
	// 订单类型
	@Column(name = "ORDER_TYPE", length = 10)
	private String orderType;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
}
