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
 * ScmsOrderReceiveLog数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_order_receive_log")
public class ScmsOrderReceiveLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsOrderReceiveLogIdGenerator")
	@TableGenerator(name = "ScmsOrderReceiveLogIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_ORDER_RECEIVE_LOG_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 订单ID
	@Column(name = "ORDER_ID", precision = 10, scale = 0)
	private Long orderId;
	
	// 收货人
	@Column(name = "RECEIVE_BY", precision = 10, scale = 0)
	private Long receiveBy;
	
	// 收货人名称(冗余)
	@Column(name = "RECEIVE_BY_NAME", length = 128)
	private String receiveByName;
	
	// 收货备注
	@Column(name = "RECEIVE_MEMO", length = 512)
	private String receiveMemo;
	
	// 收货日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVE_DATE")
	private Date receiveDate;
	
}
