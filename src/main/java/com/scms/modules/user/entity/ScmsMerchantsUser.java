/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.entity;

import java.io.Serializable;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ScmsMerchantsUser数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_merchants_user")
public class ScmsMerchantsUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsMerchantsUserIdGenerator")
	@TableGenerator(name = "ScmsMerchantsUserIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_MERCHANTS_USER_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", nullable = false, precision = 10, scale = 0)
	private Long merchantsId;
	
	// 用户ID
    @Column(name = "USER_ID", precision = 10, scale = 0)
	private Long userId;
	
	// 所属店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
    
    // 是否店铺管理员
    @Column(name = "IS_ADMIN", length = 2)
    private String isAdmin;

    @Column(name = "IS_BLOCK", length = 2)
    private String isBlock;

    // 权限内容
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PRIVILEGE_CONTENT")
    private String privilegeContent;
}
