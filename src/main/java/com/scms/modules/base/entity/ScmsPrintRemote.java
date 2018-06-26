/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * ScmsPrintRemote数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_print_remote")
public class ScmsPrintRemote implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsPrintRemoteIdGenerator")
	@TableGenerator(name = "ScmsPrintRemoteIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_PRINT_REMOTE_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 订单号
	@Column(name = "ORDER_NUM", length = 64)
	private String orderNum;
	
	// 订单列数据
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ORDER_ROW")
	private String orderRow;
	
	// 商品列表
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GOODS_LIST")
	private String goodsList;
	
	// 商品详细列表
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GOODS_DETAIL_LIST")
	private String goodsDetailList;
	
	// 支付列表
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ORDER_PAY_LIST")
	private String orderPayList;
	
	// 其他信息
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "OTHER_INFO")
	private String otherInfo;
	
	// 打印状态
	@Column(name = "STATUS", length = 10)
	private String status;
	
	// 创建人
	@Column(name = "CREATE_BY", precision = 10, scale = 0)
	private Long createBy;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	// 创建人
	@Column(name = "PRINT_BY", precision = 10, scale = 0)
	private Long printBy;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRINT_DATE")
	private Date printDate;
	
}
