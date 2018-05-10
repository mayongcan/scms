/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.entity;

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
 * ScmsDailyExpenses数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_daily_expenses")
public class ScmsDailyExpenses implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsDailyExpensesIdGenerator")
	@TableGenerator(name = "ScmsDailyExpensesIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_DAILY_EXPENSES_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
    
    // 商户自定义时的归属ID
    @Column(name = "MERCHANTS_ID", precision = 10, scale = 0)
    private Long merchantsId;
	
	// 费用名称
	@Column(name = "EXPENSES_NAME", nullable = false, length = 128)
	private String expensesName;
	
	// 支出费用
	@Column(name = "EXPENSES_NUM", precision = 10, scale = 2)
	private Double expensesNum;
	
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
