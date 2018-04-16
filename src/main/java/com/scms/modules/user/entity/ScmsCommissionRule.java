/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.entity;

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
 * ScmsCommissionRule数据库映射实体类
 * @version 1.0
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_commission_rule")
public class ScmsCommissionRule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 标识
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ScmsCommissionRuleIdGenerator")
	@TableGenerator(name = "ScmsCommissionRuleIdGenerator", table = "sys_tb_generator", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "SCMS_COMMISSION_RULE_PK", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	// 商户ID
	@Column(name = "MERCHANTS_ID", nullable = false, precision = 10, scale = 0)
	private Long merchantsId;
	
	// 所属店铺ID
	@Column(name = "SHOP_ID", precision = 10, scale = 0)
	private Long shopId;
	
	// 规则名称
	@Column(name = "RULE_NAME", length = 128)
	private String ruleName;
	
	// 佣金结算类型
	@Column(name = "CLEARING_TYPE", length = 2)
	private String clearingType;
	
	// 佣金结算周期
	@Column(name = "CLEARING_PERIOD", length = 2)
	private String clearingPeriod;
	
	// 佣金结算起算日
	@Column(name = "CLEARING_START", length = 10)
	private String clearingStart;
	
	// 提成比例
	@Column(name = "COMMISSION_PERCENT", length = 1024)
	private String commissionPercent;
	
	// 修改人
	@Column(name = "MODIFY_BY", precision = 10, scale = 0)
	private Long modifyBy;
	
	// 修改日期
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE")
	private Date modifyDate;
	
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
