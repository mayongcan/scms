/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.entity;

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
 * ScmsCustomerInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_customer_info")
public class ScmsCustomerInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsCustomerInfoIdGenerator")
	@TableGenerator(name = "ScmsCustomerInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_CUSTOMER_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", nullable = false, precision = 10, scale = 0)
	private Long merchantsId;
	
	// 客户类型ID
	@Column(name = "TYPE_ID", precision = 10, scale = 0)
	private Long typeId;
	
	// 客户等级ID
	@Column(name = "LEVEL_ID", precision = 10, scale = 0)
	private Long levelId;
	
	// 客户名称
	@Column(name = "CUSTOMER_NAME", length = 128)
	private String customerName;
	
	// 手机号码
	@Column(name = "CUSTOMER_PHONE", length = 64)
	private String customerPhone;
	
	// 客户余额
	@Column(name = "CUSTOMER_BALANCE", precision = 10, scale = 2)
	private Double customerBalance;
	
	// 电子邮箱
	@Column(name = "CUSTOMER_EMAIL", length = 64)
	private String customerEmail;
	
	// 邮政编码
	@Column(name = "CUSTOMER_ZIP", length = 12)
	private String customerZip;
	
	// 详细地址
	@Column(name = "CUSTOMER_ADDR", length = 256)
	private String customerAddr;
	
	// 客户头像
	@Column(name = "CUSTOMER_PHOTO", length = 512)
	private String customerPhoto;
	
	// 客户备注
	@Column(name = "CUSTOMER_MEMO", length = 512)
	private String customerMemo;
	
	// 所属区域编码
	@Column(name = "AREA_CODE", length = 50)
	private String areaCode;
	
	// 所属区域名称
	@Column(name = "AREA_NAME", length = 128)
	private String areaName;
	
	// 创建人
	@Column(name = "CREATE_BY", precision = 10, scale = 0)
	private Long createBy;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	// 是否有效
	@Column(name = "IS_VALID", length = 2)
	private String isValid;
	
}
