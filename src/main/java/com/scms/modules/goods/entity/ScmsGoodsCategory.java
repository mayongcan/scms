/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.entity;

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
 * ScmsGoodsCategory数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_goods_category")
public class ScmsGoodsCategory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsGoodsCategoryIdGenerator")
	@TableGenerator(name = "ScmsGoodsCategoryIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_GOODS_CATEGORY_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 父ID
	@Column(name = "PARENT_ID", precision = 10, scale = 0)
	private Long parentId;
	
	// 分类名称
	@Column(name = "CATEGORY_NAME", length = 256)
	private String categoryName;
	
	// 分类简介
	@Column(name = "CATEGORY_DESC", length = 512)
	private String categoryDesc;
	
	// 分类图片
	@Column(name = "CATEGORY_PHOTO", length = 1024)
	private String categoryPhoto;
	
	// 类型
	@Column(name = "TYPE", length = 2)
	private String type;
	
	// 商户自定义时的归属ID
	@Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
	private Long merchantsId;
	
	// 显示顺序
	@Column(name = "DISP_ORDER", precision = 10, scale = 0)
	private Long dispOrder;
	
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
