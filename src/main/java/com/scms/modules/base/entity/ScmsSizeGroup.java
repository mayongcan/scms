/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ScmsSizeGroup数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_size_group")
public class ScmsSizeGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsSizeGroupIdGenerator")
	@TableGenerator(name = "ScmsSizeGroupIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_SIZE_GROUP_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 分组名称
	@Column(name = "GROUP_NAME", length = 64)
	private String groupName;
	
	// 规格-尺寸ID列表
	@Column(name = "SIZE_ID_LIST", length = 256)
	private String sizeIdList;
	
	// 规格-尺寸名称列表
	@Column(name = "SIZE_NAME_LIST", length = 512)
	private String sizeNameList;
	
}
