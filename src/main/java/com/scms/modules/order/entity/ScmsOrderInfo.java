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
 * ScmsOrderInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_order_info")
public class ScmsOrderInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsOrderInfoIdGenerator")
	@TableGenerator(name = "ScmsOrderInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_ORDER_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
    
    // 商户名称(冗余)
    @Column(name = "MERCHANTS_NAME", length = 128)
    private String merchantsName;
	
	// 所属店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
    
    // 店铺名称(冗余)
    @Column(name = "SHOP_NAME", length = 128)
    private String shopName;
	
	// 订单类型
	@Column(name = "ORDER_TYPE", length = 10)
	private String orderType;
	
	// 订单状态
	@Column(name = "ORDER_STATUS", length = 2)
	private String orderStatus;
	
	// 订单付款状态
	@Column(name = "ORDER_PAY_STATUS", length = 2)
	private String orderPayStatus;
	
	// 订单发货状态
	@Column(name = "ORDER_SEND_STATUS", length = 2)
	private String orderSendStatus;

    @Column(name = "ORDER_RECEIVE_STATUS", length = 2)
    private String orderReceiveStatus;
	
	// 订单号
	@Column(name = "ORDER_NUM", length = 64)
	private String orderNum;
	
	// 订单总金额
	@Column(name = "TOTAL_AMOUNT", precision = 10, scale = 2)
	private Double totalAmount;

    @Column(name = "TOTAL_UN_PAY", precision = 10, scale = 2)
    private Double totalUnPay;

    @Column(name = "TOTAL_PROFIT", precision = 10, scale = 2)
    private Double totalProfit;

    @Column(name = "TOTAL_NUM", precision = 10, scale = 0)
    private Long totalNum;
	
	// 客户类型
	@Column(name = "CUSTOMER_TYPE_ID", precision = 10, scale = 0)
	private Long customerTypeId;
    
    // 客户类型名称(冗余)
    @Column(name = "CUSTOMER_TYPE_NAME", length = 128)
    private String customerTypeName;
    
    // 客户等级
    @Column(name = "CUSTOMER_LEVEL_ID", precision = 10, scale = 0)
    private Long customerLevelId;
    
    // 客户等级名称(冗余)
    @Column(name = "CUSTOMER_LEVEL_NAME", length = 128)
    private String customerLevelName;
	
	// 客户ID
	@Column(name = "CUSTOMER_ID", precision = 10, scale = 0)
	private Long customerId;
	
	// 客户名称
	@Column(name = "CUSTOMER_NAME", length = 128)
	private String customerName;
    
    @Column(name = "TRANSPORT_ID", precision = 10, scale = 0)
    private Long transportId;
    
    @Column(name = "TRANSPORT_NAME", length = 128)
    private String transportName;
	
	// 订单销售人
	@Column(name = "SELLER_BY", precision = 10, scale = 0)
	private Long sellerBy;
	
	// 订单销售人名称
	@Column(name = "SELLER_BY_NAME", length = 128)
	private String sellerByName;
	
	// 订单业绩归属人
	@Column(name = "PERFORMANCE_BY", precision = 10, scale = 0)
	private Long performanceBy;
	
	// 订单业绩归属人名称
	@Column(name = "PERFORMANCE_BY_NAME", length = 128)
	private String performanceByName;
	
	// 订单备注
	@Column(name = "ORDER_MEMO", length = 512)
	private String orderMemo;
	
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
