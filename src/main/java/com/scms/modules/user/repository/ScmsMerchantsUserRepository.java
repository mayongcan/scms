/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.user.entity.ScmsMerchantsUser;
import com.scms.modules.user.repository.custom.ScmsMerchantsUserRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsMerchantsUserRepository extends JpaRepository<ScmsMerchantsUser, Long>, JpaSpecificationExecutor<ScmsMerchantsUser>, ScmsMerchantsUserRepositoryCustom {
	
	List<ScmsMerchantsUser> findByUserId(Long userId);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_merchants_user "
            + "SET IS_ADMIN = :isAdmin "
            + "WHERE ID IN (:idList)", nativeQuery = true)
    public void setShopAdmin(@Param("isAdmin")String isAdmin, @Param("idList")List<Long> idList);
}