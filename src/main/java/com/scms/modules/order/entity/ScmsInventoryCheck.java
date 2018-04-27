/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.entity;

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
 * ScmsInventoryCheck数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_inventory_check")
public class ScmsInventoryCheck implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsInventoryCheckIdGenerator")
	@TableGenerator(name = "ScmsInventoryCheckIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_INVENTORY_CHECK_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 订单归属商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 商户名称(冗余)
	@Column(name = "MERCHANTS_NAME", length = 128)
	private String merchantsName;
	
	// 盘点的店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
	
	// 盘点的铺名称
	@Column(name = "SHOP_NAME", length = 128)
	private String shopName;
	
	// 订单号
	@Column(name = "ORDER_NUM", length = 64)
	private String orderNum;
	
	// 商品总数
	@Column(name = "TOTAL_NUM", precision = 10, scale = 0)
	private Long totalNum;
	
	// 备注
	@Column(name = "MEMO", length = 512)
	private String memo;
	
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
