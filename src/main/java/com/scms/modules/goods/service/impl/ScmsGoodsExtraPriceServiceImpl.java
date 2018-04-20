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
import com.scms.modules.goods.service.ScmsGoodsExtraPriceService;
import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;
import com.scms.modules.goods.repository.ScmsGoodsExtraPriceRepository;

@Service
public class ScmsGoodsExtraPriceServiceImpl implements ScmsGoodsExtraPriceService {
	
    @Autowired
    private ScmsGoodsExtraPriceRepository scmsGoodsExtraPriceRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsExtraPrice scmsGoodsExtraPrice, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsExtraPriceRepository.getList(scmsGoodsExtraPrice, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsExtraPriceRepository.getListCount(scmsGoodsExtraPrice, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
}
