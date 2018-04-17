/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.user.entity.ScmsCommissionRule;
import com.scms.modules.user.repository.custom.ScmsCommissionRuleRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsCommissionRuleRepository extends JpaRepository<ScmsCommissionRule, Long>, JpaSpecificationExecutor<ScmsCommissionRule>, ScmsCommissionRuleRepositoryCustom {
	
	
	/**
	 * 删除信息（将信息的IS_VALID设置为N）
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE scms_commission_rule "
			+ "SET IS_VALID = :isValid "
			+ "WHERE ID IN (:idList)", nativeQuery = true)
	public void delEntity(@Param("isValid")String isValid, @Param("idList")List<Long> idList);

    /**
     * 保存scms_user_commission
     * @param userId
     * @param commissionId
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO scms_user_commission (USER_ID, COMMISSION_ID) VALUES (:userId, :commissionId) ", nativeQuery = true)
    public void saveUserCommission(@Param("userId")Long userId, @Param("commissionId")Long commissionId);


    /**
     * 删除scms_user_commission关联表
     * @param commissionId
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM scms_user_commission WHERE COMMISSION_ID =:commissionId ", nativeQuery = true)
    public void delUserCommissionByCommissionId(@Param("commissionId")Long commissionId);

}