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
 * ScmsSupplierInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_supplier_info")
public class ScmsSupplierInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsSupplierInfoIdGenerator")
	@TableGenerator(name = "ScmsSupplierInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_SUPPLIER_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 供货商名称
	@Column(name = "SUPPLIER_NAME", length = 128)
	private String supplierName;
	
	// 供货商负责人
	@Column(name = "SUPPLIER_ADMIN", length = 128)
	private String supplierAdmin;
	
	// 负责人手机号码
	@Column(name = "SUPPLIER_PHONE", length = 64)
	private String supplierPhone;
	
	// 客户余额
	@Column(name = "SUPPLIER_BALANCE", precision = 10, scale = 2)
	private Double supplierBalance;
	
	// 电子邮箱
	@Column(name = "SUPPLIER_EMAIL", length = 64)
	private String supplierEmail;
	
	// 邮政编码
	@Column(name = "SUPPLIER_ZIP", length = 12)
	private String supplierZip;
	
	// 详细地址
	@Column(name = "SUPPLIER_ADDR", length = 256)
	private String supplierAddr;
	
	// 供货商头像
	@Column(name = "SUPPLIER_PHOTO", length = 512)
	private String supplierPhoto;
	
	// 供货商备注
	@Column(name = "SUPPLIER_MEMO", length = 512)
	private String supplierMemo;
	
	// 所属区域编码
	@Column(name = "AREA_CODE", length = 50)
	private String areaCode;
	
	// 所属区域名称
	@Column(name = "AREA_NAME", length = 128)
	private String areaName;
	
	// 供货商网址
	@Column(name = "WEB_SITE", length = 512)
	private String webSite;
	
	// 开户银行1
	@Column(name = "BANK_NAME_1", length = 128)
	private String bankName1;
	
	// 银行卡号1
	@Column(name = "BANK_CARD_1", length = 128)
	private String bankCard1;
	
	// 开户银行2
	@Column(name = "BANK_NAME_2", length = 128)
	private String bankName2;
	
	// 银行卡号2
	@Column(name = "BANK_CARD_2", length = 128)
	private String bankCard2;
	
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
