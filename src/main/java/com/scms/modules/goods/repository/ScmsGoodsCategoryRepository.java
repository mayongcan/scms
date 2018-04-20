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
    


    /**
     * 根据ID查找所有子ID
     * @param categoryId
     * @return
     */
    @Query(value = "SELECT ext.CATEGORY_CHILD_ID "
            + "FROM scms_goods_category org inner join scms_goods_category_recur ext on org.CATEGORY_ID = ext.CATEGORY_ID "
            + "WHERE org.CATEGORY_ID = :categoryId", nativeQuery = true)
    public List<Object> getAllChildIdByCategoryId(@Param("categoryId")Long categoryId);


    /**
     * 删除CategoryRecur
     * @param categoryChildId
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM scms_goods_category_recur "
            + "WHERE CATEGORY_CHILD_ID = :categoryChildId ", nativeQuery = true)
    public void delCategoryRecurByCategoryChildId(@Param("categoryChildId")Long categoryChildId);
    

    /**
     * 删除Category
     * @param categoryId
     * @param categoryChildId
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM scms_goods_category_recur "
            + "WHERE CATEGORY_ID =:categoryId and CATEGORY_CHILD_ID = :categoryChildId ", nativeQuery = true)
    public void delCategoryRecur(@Param("categoryId")Long categoryId, @Param("categoryChildId")Long categoryChildId);
    
    
    /**
     * 保存Category
     * @param categoryId
     * @param categoryChildId
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO scms_goods_category_recur (CATEGORY_ID, CATEGORY_CHILD_ID) VALUES (:categoryId, :categoryChildId) ", nativeQuery = true)
    public void saveCategoryRecur(@Param("categoryId")Long categoryId, @Param("categoryChildId")Long categoryChildId);
    
	
}