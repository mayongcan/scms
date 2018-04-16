/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.scms.modules.base.entity.ScmsVenderInfo;
import com.scms.modules.base.repository.custom.ScmsVenderInfoRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsVenderInfoRepository extends JpaRepository<ScmsVenderInfo, Long>, JpaSpecificationExecutor<ScmsVenderInfo>, ScmsVenderInfoRepositoryCustom {

    List<ScmsVenderInfo> findByVenderNameAndTypeAndIsValid(String venderName, String type, String isValid);
    
    List<ScmsVenderInfo> findByVenderNameAndTypeAndMerchantsIdAndIsValid(String venderName, String type, Long merchantsId, String isValid);
	
	/**
	 * 删除信息（将信息的IS_VALID设置为N）
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE scms_vender_info "
			+ "SET IS_VALID = :isValid "
			+ "WHERE ID IN (:idList)", nativeQuery = true)
	public void delEntity(@Param("isValid")String isValid, @Param("idList")List<Long> idList);
	
}