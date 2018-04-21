/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.goods.service.ScmsGoodsInfoService;
import com.scms.modules.goods.service.ScmsGoodsModifyLogService;
import com.scms.modules.goods.entity.ScmsGoodsExtraDiscount;
import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;
import com.scms.modules.goods.entity.ScmsGoodsInfo;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.entity.ScmsGoodsModifyLog;
import com.scms.modules.goods.repository.ScmsGoodsExtraDiscountRepository;
import com.scms.modules.goods.repository.ScmsGoodsExtraPriceRepository;
import com.scms.modules.goods.repository.ScmsGoodsInfoRepository;
import com.scms.modules.goods.repository.ScmsGoodsInventoryRepository;

@Service
public class ScmsGoodsInfoServiceImpl implements ScmsGoodsInfoService {
	
    @Autowired
    private ScmsGoodsInfoRepository scmsGoodsInfoRepository;
    
    @Autowired
    private ScmsGoodsInventoryRepository scmsGoodsInventoryRepository;
    
    @Autowired
    private ScmsGoodsExtraPriceRepository scmsGoodsExtraPriceRepository;
    
    @Autowired
    private ScmsGoodsExtraDiscountRepository scmsGoodsExtraDiscountRepository;
    
    @Autowired
    private ScmsGoodsModifyLogService scmsGoodsModifyLogService;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsInfoRepository.getList(scmsGoodsInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsInfoRepository.getListCount(scmsGoodsInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsGoodsInfo scmsGoodsInfo = (ScmsGoodsInfo) BeanUtils.mapToBean(params, ScmsGoodsInfo.class);
	    scmsGoodsInfo.setUseStatus("1");//设置为启用状态
		scmsGoodsInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsGoodsInfo.setCreateBy(userInfo.getUserId());
		scmsGoodsInfo.setCreateByName(userInfo.getUserName());
		scmsGoodsInfo.setCreateDate(new Date());
		scmsGoodsInfo = scmsGoodsInfoRepository.saveAndFlush(scmsGoodsInfo);
		//保存商品库存信息
		String goodsInventoryList = MapUtils.getString(params, "goodsInventoryList");
		JSONArray jsonArray = JSONObject.parseArray(goodsInventoryList);
		if(jsonArray != null && jsonArray.size() > 0) {
		    ScmsGoodsInventory obj = null;
		    for(int i = 0; i < jsonArray.size(); i++) {
		        obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsGoodsInventory.class);
		        if(obj != null) {
		            obj.setGoodsId(scmsGoodsInfo.getId());
		            scmsGoodsInventoryRepository.save(obj);
		        }
		    }
		}
		
		//保存商品额外信息表
		String extraPriceList = MapUtils.getString(params, "extraPriceList");
		jsonArray = JSONObject.parseArray(extraPriceList);
        if(jsonArray != null && jsonArray.size() > 0) {
            ScmsGoodsExtraPrice obj = null;
            for(int i = 0; i < jsonArray.size(); i++) {
                obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsGoodsExtraPrice.class);
                if(obj != null) {
                    obj.setGoodsId(scmsGoodsInfo.getId());
                    scmsGoodsExtraPriceRepository.save(obj);
                }
            }
        }

        //保存商品折扣信息表
        String extraDiscountList = MapUtils.getString(params, "extraDiscountList");
        jsonArray = JSONObject.parseArray(extraDiscountList);
        if(jsonArray != null && jsonArray.size() > 0) {
            ScmsGoodsExtraDiscount obj = null;
            for(int i = 0; i < jsonArray.size(); i++) {
                obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsGoodsExtraDiscount.class);
                if(obj != null) {
                    obj.setGoodsId(scmsGoodsInfo.getId());
                    scmsGoodsExtraDiscountRepository.save(obj);
                }
            }
        }
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsGoodsInfo scmsGoodsInfo = (ScmsGoodsInfo) BeanUtils.mapToBean(params, ScmsGoodsInfo.class);
		ScmsGoodsInfo scmsGoodsInfoInDb = scmsGoodsInfoRepository.findOne(scmsGoodsInfo.getId());
		if(scmsGoodsInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsGoodsInfo, scmsGoodsInfoInDb);
		if(StringUtils.isBlank(scmsGoodsInfo.getGoodsPhoto())) {
		    scmsGoodsInfoInDb.setGoodsPhoto("");
		}
		scmsGoodsInfoRepository.save(scmsGoodsInfoInDb);

        //保存商品额外信息表
        String extraPriceList = MapUtils.getString(params, "extraPriceList");
        JSONArray jsonArray = JSONObject.parseArray(extraPriceList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //先删除旧的额外价格
            scmsGoodsExtraPriceRepository.delByGoodsId(scmsGoodsInfoInDb.getId());
            ScmsGoodsExtraPrice obj = null;
            for(int i = 0; i < jsonArray.size(); i++) {
                obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsGoodsExtraPrice.class);
                if(obj != null) {
                    obj.setGoodsId(scmsGoodsInfoInDb.getId());
                    scmsGoodsExtraPriceRepository.save(obj);
                }
            }
        }

        //保存商品折扣信息表
        String extraDiscountList = MapUtils.getString(params, "extraDiscountList");
        jsonArray = JSONObject.parseArray(extraDiscountList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //先删除旧的额外价格
            scmsGoodsExtraDiscountRepository.delByGoodsId(scmsGoodsInfoInDb.getId());
            ScmsGoodsExtraDiscount obj = null;
            for(int i = 0; i < jsonArray.size(); i++) {
                obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsGoodsExtraDiscount.class);
                if(obj != null) {
                    obj.setGoodsId(scmsGoodsInfoInDb.getId());
                    scmsGoodsExtraDiscountRepository.save(obj);
                }
            }
        }
        //保存修改记录scmsGoodsModifyLogRepository
        ScmsGoodsModifyLog modifyLog = new ScmsGoodsModifyLog();
        modifyLog.setGoodsId(scmsGoodsInfoInDb.getId());
        modifyLog.setModifyMemo(MapUtils.getString(params, "modifyMemo"));
        scmsGoodsModifyLogService.add(modifyLog, userInfo);
        
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		//判断是否需要移除
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < ids.length; i++) {
			idList.add(StringUtils.toLong(ids[i]));
		}
		//批量更新（设置IsValid 为N）
		if(idList.size() > 0){
			scmsGoodsInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
