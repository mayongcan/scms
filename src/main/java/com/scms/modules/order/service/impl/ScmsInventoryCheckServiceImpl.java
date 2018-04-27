/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

import java.util.Date;
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
import com.gimplatform.core.utils.DateUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.scms.modules.order.service.ScmsInventoryCheckService;
import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.repository.ScmsMerchantsInfoRepository;
import com.scms.modules.goods.repository.ScmsGoodsInventoryRepository;
import com.scms.modules.order.entity.ScmsInventoryCheck;
import com.scms.modules.order.entity.ScmsInventoryCheckGoods;
import com.scms.modules.order.entity.ScmsInventoryCheckGoodsDetail;
import com.scms.modules.order.repository.ScmsInventoryCheckGoodsDetailRepository;
import com.scms.modules.order.repository.ScmsInventoryCheckGoodsRepository;
import com.scms.modules.order.repository.ScmsInventoryCheckRepository;

@Service
public class ScmsInventoryCheckServiceImpl implements ScmsInventoryCheckService {
	
    @Autowired
    private ScmsInventoryCheckRepository scmsInventoryCheckRepository;
    
    @Autowired
    private ScmsMerchantsInfoRepository scmsMerchantsInfoRepository;
    
    @Autowired
    private ScmsInventoryCheckGoodsRepository scmsInventoryCheckGoodsRepository;
    
    @Autowired
    private ScmsInventoryCheckGoodsDetailRepository scmsInventoryCheckGoodsDetailRepository;
    
    @Autowired
    private ScmsGoodsInventoryRepository scmsGoodsInventoryRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsInventoryCheck scmsInventoryCheck, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsInventoryCheckRepository.getList(scmsInventoryCheck, params, page.getPageNumber(), page.getPageSize());
		int count = scmsInventoryCheckRepository.getListCount(scmsInventoryCheck, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsInventoryCheck scmsInventoryCheck = (ScmsInventoryCheck) BeanUtils.mapToBean(params, ScmsInventoryCheck.class);
        //设置商户名称
        ScmsMerchantsInfo scmsMerchantsInfo = scmsMerchantsInfoRepository.findOne(scmsInventoryCheck.getMerchantsId());
        scmsInventoryCheck.setMerchantsName(scmsMerchantsInfo.getMerchantsName());
        //设置订单编号，格式：订单类型+年月日时分秒毫秒
        scmsInventoryCheck.setOrderNum("pdd" + DateUtils.getDate("yyyyMMddHHmmssSSS"));
		scmsInventoryCheck.setIsValid(Constants.IS_VALID_VALID);
		scmsInventoryCheck.setCreateBy(userInfo.getUserId());
		scmsInventoryCheck.setCreateByName(userInfo.getUserName());
		scmsInventoryCheck.setCreateDate(new Date());
		scmsInventoryCheck = scmsInventoryCheckRepository.saveAndFlush(scmsInventoryCheck);

        //保存订单商品关联信息,并更新仓库信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                //先保存ScmsInventoryCheckGoods
                ScmsInventoryCheckGoods scmsInventoryCheckGoods = JSONObject.toJavaObject(json, ScmsInventoryCheckGoods.class);
                if(scmsInventoryCheckGoods != null) {
                    scmsInventoryCheckGoods.setOrderId(scmsInventoryCheck.getId());
                    scmsInventoryCheckGoods = scmsInventoryCheckGoodsRepository.save(scmsInventoryCheckGoods);
                    //保存详细信息表ScmsInventoryCheckGoodsDetail
                    JSONArray InventoryCheckGoodsDetailListJsonArray = json.getJSONArray("goodsDataList");
                    if(InventoryCheckGoodsDetailListJsonArray != null && InventoryCheckGoodsDetailListJsonArray.size() > 0) {
                        for(int j = 0; j < InventoryCheckGoodsDetailListJsonArray.size(); j++) {
                            //先保存ScmsInventoryCheckGoodsDetail
                            ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail = JSONObject.toJavaObject(InventoryCheckGoodsDetailListJsonArray.getJSONObject(j), ScmsInventoryCheckGoodsDetail.class);
                            if(scmsInventoryCheckGoodsDetail != null) {
                                scmsInventoryCheckGoodsDetail.setOrderId(scmsInventoryCheck.getId());
                                scmsInventoryCheckGoodsDetail.setDetailId(scmsInventoryCheckGoods.getId());
                                scmsInventoryCheckGoodsDetailRepository.save(scmsInventoryCheckGoodsDetail);
                                
                                //更新库存，直接更新为盘点后的库存
                                scmsGoodsInventoryRepository.updateGoodsInventoryNum(scmsInventoryCheckGoodsDetail.getGoodsAfterNum(), scmsInventoryCheck.getShopId(), 
                                        scmsInventoryCheckGoods.getGoodsId(), scmsInventoryCheckGoodsDetail.getGoodsColorId(), scmsInventoryCheckGoodsDetail.getGoodsSizeId(), scmsInventoryCheckGoodsDetail.getGoodsTextureId());                                
                            }
                        }
                    }
                }
            }
        }

		return RestfulRetUtils.getRetSuccess();
	}
}
