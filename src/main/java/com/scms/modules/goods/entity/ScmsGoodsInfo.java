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
 * ScmsGoodsInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_goods_info")
public class ScmsGoodsInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsGoodsInfoIdGenerator")
	@TableGenerator(name = "ScmsGoodsInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_GOODS_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 分类ID
	@Column(name = "CATEGORY_ID", precision = 10, scale = 0)
	private Long categoryId;
	
	// 厂家ID
	@Column(name = "VENDER_ID", precision = 10, scale = 0)
	private Long venderId;
	
	// 商品名称
	@Column(name = "GOODS_NAME", length = 256)
	private String goodsName;
	
	// 商品货号
	@Column(name = "GOODS_SERIAL_NUM", length = 64)
	private String goodsSerialNum;
	
	// 商品销售价格
	@Column(name = "SALE_PRICE", precision = 10, scale = 2)
	private Double salePrice;
	
	// 商品进货价格
	@Column(name = "PURCHASE_PRICE", precision = 10, scale = 2)
	private Double purchasePrice;
	
	// 商品默认折扣
	@Column(name = "DEF_DISCOUNT", precision = 10, scale = 0)
	private Long defDiscount;
	
	// 包装数
	@Column(name = "PACKING_NUM", precision = 10, scale = 0)
	private Long packingNum;
	
	// 商品描述
	@Column(name = "GOODS_DESC", length = 512)
	private String goodsDesc;
	
	// 商品图片
	@Column(name = "GOODS_PHOTO", length = 1024)
	private String goodsPhoto;
	
	// 商品所属年份
	@Column(name = "GOODS_YEAR", length = 10)
	private String goodsYear;
	
	// 商品所属季节
	@Column(name = "GOODS_SEASON", length = 2)
	private String goodsSeason;
	
	// 商品热卖状态
	@Column(name = "BUY_STATUS", length = 2)
	private String buyStatus;
	
	// 商品上下架状态
	@Column(name = "SHELF_STATUS", length = 2)
	private String shelfStatus;
	
	// 商品使用状态
	@Column(name = "USE_STATUS", length = 2)
	private String useStatus;
	
    @Column(name = "COLOR_ID_LIST", length = 256)
    private String colorIdList;
    
    @Column(name = "COLOR_NAME_LIST", length = 512)
    private String colorNameList;
    
    @Column(name = "SIZE_ID_LIST", length = 256)
    private String sizeIdList;
    
    @Column(name = "SIZE_NAME_LIST", length = 512)
    private String sizeNameList;
    
    @Column(name = "TEXTURE_ID_LIST", length = 256)
    private String textureIdList;
    
    @Column(name = "TEXTURE_NAME_LIST", length = 1024)
    private String textureNameList;
	
	// 创建人
	@Column(name = "CREATE_BY", precision = 10, scale = 0)
	private Long createBy;
	
	// 创建人名称(冗余)
	@Column(name = "CREATE_BY_NAME", length = 128)
	private String createByName;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	// 是否有效
	@Column(name = "IS_VALID", length = 2)
	private String isValid;
	
}
