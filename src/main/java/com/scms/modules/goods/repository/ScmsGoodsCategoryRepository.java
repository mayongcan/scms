/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import com.scms.modules.goods.entity.ScmsGoodsCategory;
import com.scms.modules.goods.repository.custom.ScmsGoodsCategoryRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface ScmsGoodsCategoryRepository extends JpaRepository<ScmsGoodsCategory, Long>, JpaSpecificationExecutor<ScmsGoodsCategory>, ScmsGoodsCategoryRepositoryCustom {

    List<ScmsGoodsCategory> findByParentIdAndCategoryNameAndType(Long parentId, String categoryName, String type);
    
    List<ScmsGoodsCategory> findByParentIdAndCategoryNameAndTypeAndMerchantsId(Long parentId, String categoryName, String type, Long merchantsId);
    
//	/**
//	 * 获取树列表
//	 * @return
//	 */
//	@Query(value = "SELECT tb.* "
//			+ "FROM scms_goods_category tb "
//			+ "WHERE tb.IS_VALID = 'Y' "
//			+ "ORDER BY PARENT_ID, DISP_ORDER, ID", nativeQuery = true)
//    public List<ScmsGoodsCategory> getTreeList();  

	/**
	 * 根据父ID列表获取ID列表
	 * @param idList
	 * @return
	 */
	@Query(value = "SELECT * FROM scms_goods_category WHERE IS_VALID='Y' AND PARENT_ID IN (:idList)", nativeQuery = true)
    public List<ScmsGoodsCategory> getListByParentIds(@Param("idList")List<Long> idList); 
	
	/**
	 * 根据父ID列表获取ID列表
	 * @param idList
	 * @return
	 */
	@Query(value = "SELECT * FROM scms_goods_category WHERE IS_VALID='Y' AND PARENT_ID is null", nativeQuery = true)
    public List<ScmsGoodsCategory> getListByRoot();  
	
	/**
	 * 删除信息（将信息的IS_VALID设置为N）
	 * @param isValid
	 * @param idList
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE scms_goods_category "
			+ "SET IS_VALID = :isValid "
			+ "WHERE ID IN (:idList)", nativeQuery = true)
	public void delEntity(@Param("isValid")String isValid, @Param("idList")List<Long> idList);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE scms_goods_category "
            + "SET DISP_ORDER = :dispOrder "
            + "WHERE ID = :id ", nativeQuery = true)
    public void updateDispOrder(@Param("dispOrder")Long dispOrder, @Param("id")Long id);
	
}