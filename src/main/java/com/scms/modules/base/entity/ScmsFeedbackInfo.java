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
 * ScmsFeedbackInfo数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_feedback_info")
public class ScmsFeedbackInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsFeedbackInfoIdGenerator")
	@TableGenerator(name = "ScmsFeedbackInfoIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_FEEDBACK_INFO_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", nullable = false, precision = 10, scale = 0)
	private Long merchantsId;
	
	// 用户ID
    @Column(name = "USER_ID", nullable = false, precision = 10, scale = 0)
	private Long userId;
	
	// 反馈标题
	@Column(name = "TITLE", length = 128)
	private String title;
	
	// 反馈内容
	@Column(name = "CONTENT", length = 1024)
	private String content;
	
	// 是否已读
	@Column(name = "IS_READ", length = 2)
	private String isRead;
	
	// 是否处理
	@Column(name = "IS_HANDLE", length = 2)
	private String isHandle;
	
	// 创建日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	// 已读日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "READ_DATE")
	private Date readDate;
	
	// 处理日期
    @JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HANDLE_DATE")
	private Date handleDate;
	
}
