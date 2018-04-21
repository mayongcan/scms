/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.scms.modules.goods.service.ScmsGoodsExtraDiscountService;
import com.scms.modules.goods.entity.ScmsGoodsExtraDiscount;
import com.scms.modules.goods.repository.ScmsGoodsExtraDiscountRepository;

@Service
public class ScmsGoodsExtraDiscountServiceImpl implements ScmsGoodsExtraDiscountService {
	
    @Autowired
    private ScmsGoodsExtraDiscountRepository scmsGoodsExtraDiscountRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsExtraDiscount scmsGoodsExtraDiscount, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsExtraDiscountRepository.getList(scmsGoodsExtraDiscount, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsExtraDiscountRepository.getListCount(scmsGoodsExtraDiscount, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
}
