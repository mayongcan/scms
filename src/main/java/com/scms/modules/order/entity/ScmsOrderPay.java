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
 * ScmsOrderPay数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_order_pay")
public class ScmsOrderPay implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsOrderPayIdGenerator")
	@TableGenerator(name = "ScmsOrderPayIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_ORDER_PAY_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 订单ID
	@Column(name = "ORDER_ID", precision = 10, scale = 0)
	private Long orderId;
	
	// 支付方式
	@Column(name = "PAY_TYPE_ID", precision = 10, scale = 0)
	private Long payTypeId;
    
    @Column(name = "PAY_TYPE_NAME", length = 128)
    private String payTypeName;

    //费用类型1收入 2支出
    @Column(name = "INCOME_TYPE", length = 10)
    private String incomeType;
	
	// 支付金额
	@Column(name = "PAY_AMOUNT", precision = 10, scale = 2)
	private Double payAmount;
	
	// 支付备注
	@Column(name = "PAY_MEMO", length = 512)
	private String payMemo;
	
	// 支付时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PAY_DATE")
	private Date payDate;
	
	// 操作用户ID
	@Column(name = "OPERATE_USER_ID", precision = 10, scale = 0)
	private Long operateUserId;
	
	// 操作用户名称
	@Column(name = "OPERATE_USER_NAME", length = 128)
	private String operateUserName;
	
}
