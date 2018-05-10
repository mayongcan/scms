/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.entity;

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
 * ScmsFinanceFlow数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_finance_flow")
public class ScmsFinanceFlow implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsFinanceFlowIdGenerator")
	@TableGenerator(name = "ScmsFinanceFlowIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_FINANCE_FLOW_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 所属店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
	
	// 账目流水类型
	@Column(name = "FLOW_TYPE", length = 64)
	private String flowType;
	
	// 关联日常支出ID
	@Column(name = "EXPENSES_ID", precision = 10, scale = 0)
	private Long expensesId;
	
	// 订单号
	@Column(name = "ORDER_NUM", length = 64)
	private String orderNum;
	
	// 订单类型
	@Column(name = "ORDER_TYPE", length = 10)
	private String orderType;
	
	// 支付方式
	@Column(name = "PAY_TYPE_ID", precision = 10, scale = 0)
	private Long payTypeId;
	
	// 支付方式名称
	@Column(name = "PAY_TYPE_NAME", length = 128)
	private String payTypeName;
	
	// 支付金额
	@Column(name = "PAY_AMOUNT", precision = 10, scale = 2)
	private Double payAmount;
	
	// 支付备注
	@Column(name = "PAY_MEMO", length = 512)
	private String payMemo;
	
	// 创建人
	@Column(name = "CREATE_BY", precision = 10, scale = 0)
	private Long createBy;
	
	// 创建人名称
	@Column(name = "CREATE_BY_NAME", length = 128)
	private String createByName;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	// 无效原因
	@Column(name = "VALID_REASON", length = 128)
	private String validReason;
	
	// 是否有效
	@Column(name = "IS_VALID", length = 2)
	private String isValid;
	
}
