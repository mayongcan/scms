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
 * ScmsVenderInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_vender_info")
public class ScmsVenderInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsVenderInfoIdGenerator")
	@TableGenerator(name = "ScmsVenderInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_VENDER_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 厂家名称
	@Column(name = "VENDER_NAME", nullable = false, length = 256)
	private String venderName;
	
	// 厂家简介
	@Column(name = "VENDER_DESC", length = 512)
	private String venderDesc;
	
	// 厂家图片
	@Column(name = "VENDER_PHOTO", length = 1024)
	private String venderPhoto;
	
	// 厂家地址
	@Column(name = "VENDER_ADDR", length = 256)
	private String venderAddr;
	
	// 厂家联系电话
	@Column(name = "CONTACT_PHONE", length = 64)
	private String contactPhone;
	
	// 厂家联系人名称
	@Column(name = "CONTACT_NAME", length = 64)
	private String contactName;
	
	// 类型
	@Column(name = "TYPE", length = 2)
	private String type;
	
	// 商户自定义时的归属ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
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
