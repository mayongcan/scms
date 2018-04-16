/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.entity;

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
 * ScmsMerchantsInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_merchants_info")
public class ScmsMerchantsInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsMerchantsInfoIdGenerator")
	@TableGenerator(name = "ScmsMerchantsInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_MERCHANTS_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户所属人绑定用户ID
	@Column(name = "USER_ID", nullable = false, precision = 10, scale = 0)
	private Long userId;
	
	// 商户名称
	@Column(name = "MERCHANTS_NAME", length = 256)
	private String merchantsName;
	
	// 商户备注
	@Column(name = "MERCHANTS_MEMO", length = 512)
	private String merchantsMemo;
	
	// 经营范围
	@Column(name = "BUSS_SCOPE", length = 512)
	private String bussScope;
	
	// 开业时间
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;
	
	// 联系电话
	@Column(name = "PHONE", length = 64)
	private String phone;
	
	// 联系地址
	@Column(name = "ADDR", length = 512)
	private String addr;
	
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
