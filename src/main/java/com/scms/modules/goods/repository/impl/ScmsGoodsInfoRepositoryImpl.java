/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.goods.entity.ScmsGoodsInfo;
import com.scms.modules.goods.repository.custom.ScmsGoodsInfoRepositoryCustom;

public class ScmsGoodsInfoRepositoryImpl extends BaseRepository implements ScmsGoodsInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.CATEGORY_ID as \"categoryId\", tb.VENDER_ID as \"venderId\", "
	        + "tb.GOODS_NAME as \"goodsName\", tb.GOODS_SERIAL_NUM as \"goodsSerialNum\", tb.SALE_PRICE as \"salePrice\", tb.PURCHASE_PRICE as \"purchasePrice\", "
	        + "tb.DEF_DISCOUNT as \"defDiscount\", tb.PACKING_NUM as \"packingNum\", tb.GOODS_DESC as \"goodsDesc\", tb.GOODS_PHOTO as \"goodsPhoto\", tb.GOODS_YEAR as \"goodsYear\", "
	        + "tb.COLOR_ID_LIST as \"colorIdList\", tb.COLOR_NAME_LIST as \"colorNameList\", tb.SIZE_ID_LIST as \"sizeIdList\", tb.SIZE_NAME_LIST as \"sizeNameList\", "
	        + "tb.GOODS_SEASON as \"goodsSeason\", tb.BUY_STATUS as \"buyStatus\", tb.SHELF_STATUS as \"shelfStatus\", tb.USE_STATUS as \"useStatus\", tb.GOODS_TEXTURE as \"goodsTexture\", tb.CREATE_BY as \"createBy\", "
	        + "tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", sgc.CATEGORY_NAME as \"categoryName\", svi.VENDER_NAME as \"venderName\", "
	        + "(select sum(tmpsgi.INVENTORY_NUM) from scms_goods_inventory tmpsgi where tmpsgi.GOODS_ID = tb.ID ) as \"goodsInventoryNum\" "
			+ "FROM scms_goods_info tb left join scms_goods_category sgc on sgc.ID = tb.CATEGORY_ID "
			+ "left join scms_vender_info svi on svi.ID = tb.VENDER_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
	        + "FROM scms_goods_info tb left join scms_goods_category sgc on sgc.ID = tb.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = tb.VENDER_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";
	

    private static final String SQL_GET_ALL_GOODS_INVENTORY_STATISTICS = "SELECT " + 
            " sum(t.totalGoods) as \"totalGoods\", " + 
            " sum(t.totalGoodsInventory) as \"totalGoodsInventory\", " + 
            " sum(t.totalPurchase) as \"totalPurchase\" " + 
            " FROM " + 
                " (SELECT count(DISTINCT(tb.ID)) as \"totalGoods\" , " + 
                " sum(sgi.INVENTORY_NUM) as \"totalGoodsInventory\", " + 
                " (select sum(tmpsgi.INVENTORY_NUM) * tb.PURCHASE_PRICE from scms_goods_inventory tmpsgi where tmpsgi.GOODS_ID = tb.ID) as \"totalPurchase\" " + 
                " FROM scms_goods_info tb left join scms_goods_inventory sgi on tb.ID = sgi.GOODS_ID " + 
                " WHERE 1 = 1 ";
                
	
	public List<Map<String, Object>> getList(ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long categoryId = MapUtils.getLong(params, "categoryId", null);
        boolean findChilds = MapUtils.getBooleanValue(params, "findChilds", false);
        String goodsName = MapUtils.getString(params, "goodsName");
        String goodsSerialNum = MapUtils.getString(params, "goodsSerialNum");
        Long venderId = MapUtils.getLong(params, "venderId", null);
        String goodsYear = MapUtils.getString(params, "goodsYear");
        String goodsSeason = MapUtils.getString(params, "goodsSeason");
        String buyStatus = MapUtils.getString(params, "buyStatus");
        String shelfStatus = MapUtils.getString(params, "shelfStatus");
        String useStatus = MapUtils.getString(params, "useStatus");
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        if (categoryId != null) {
            if(findChilds){
                sqlParams.querySql.append(" AND tb.CATEGORY_ID in(select distinct(CATEGORY_CHILD_ID) from scms_goods_category_recur where CATEGORY_ID = :categoryId ) ");
                sqlParams.paramsList.add("categoryId");
                sqlParams.valueList.add(categoryId);
            }else{
                sqlParams.querySql.append(" AND tb.CATEGORY_ID = :categoryId ");
                sqlParams.paramsList.add("categoryId");
                sqlParams.valueList.add(categoryId);
            }
        }
        if(!StringUtils.isBlank(goodsName)) {
            sqlParams.querySql.append(getLikeSql("tb.GOODS_NAME", ":goodsName"));
            sqlParams.paramsList.add("goodsName");
            sqlParams.valueList.add(goodsName);
        }
        if(!StringUtils.isBlank(goodsSerialNum)) {
            sqlParams.querySql.append(getLikeSql("tb.GOODS_SERIAL_NUM", ":goodsSerialNum"));
            sqlParams.paramsList.add("goodsSerialNum");
            sqlParams.valueList.add(goodsSerialNum);
        }
        if(venderId != null) {
            sqlParams.querySql.append(" AND tb.VENDER_ID = :venderId ");
            sqlParams.paramsList.add("venderId");
            sqlParams.valueList.add(venderId);
        }
        if(!StringUtils.isBlank(goodsYear)) {
            sqlParams.querySql.append(" AND tb.GOODS_YEAR = :goodsYear ");
            sqlParams.paramsList.add("goodsYear");
            sqlParams.valueList.add(goodsYear);
        }
        if(!StringUtils.isBlank(goodsSeason)) {
            sqlParams.querySql.append(" AND tb.GOODS_SEASON = :goodsSeason ");
            sqlParams.paramsList.add("goodsSeason");
            sqlParams.valueList.add(goodsSeason);
        }
        if(!StringUtils.isBlank(buyStatus)) {
            sqlParams.querySql.append(" AND tb.BUY_STATUS = :buyStatus ");
            sqlParams.paramsList.add("buyStatus");
            sqlParams.valueList.add(buyStatus);
        }
        if(!StringUtils.isBlank(shelfStatus)) {
            sqlParams.querySql.append(" AND tb.SHELF_STATUS = :shelfStatus ");
            sqlParams.paramsList.add("shelfStatus");
            sqlParams.valueList.add(shelfStatus);
        }
        if(!StringUtils.isBlank(useStatus)) {
            sqlParams.querySql.append(" AND tb.USE_STATUS = :useStatus ");
            sqlParams.paramsList.add("useStatus");
            sqlParams.valueList.add(useStatus);
        }
        //混合查询
        String mixtureValue = MapUtils.getString(params, "mixtureValue");
        if(!StringUtils.isBlank(mixtureValue)) {
            sqlParams.querySql.append(" AND (tb.GOODS_NAME like concat('%', :mixtureValue , '%') OR tb.GOODS_SERIAL_NUM like concat('%', :mixtureValue , '%')) ");
            sqlParams.paramsList.add("mixtureValue");
            sqlParams.valueList.add(mixtureValue);
        }
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getAllGoodsInventoryStatistics(Map<String, Object> params) {
      //生成查询条件
        SqlParams sqlParams = genListWhere(SQL_GET_ALL_GOODS_INVENTORY_STATISTICS, params);
        sqlParams.querySql.append(" GROUP BY tb.ID  ) t");
        return getResultList(sqlParams);
    }
}