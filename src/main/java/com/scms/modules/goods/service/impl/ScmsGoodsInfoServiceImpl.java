/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.Date;
import java.util.Iterator;
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
import com.scms.modules.goods.service.ScmsGoodsInventoryService;
import com.scms.modules.goods.service.ScmsGoodsModifyLogService;
import com.scms.modules.base.entity.ScmsShopInfo;
import com.scms.modules.base.repository.ScmsShopInfoRepository;
import com.scms.modules.goods.entity.ScmsGoodsExtraDiscount;
import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;
import com.scms.modules.goods.entity.ScmsGoodsInfo;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.entity.ScmsGoodsModifyLog;
import com.scms.modules.goods.repository.ScmsGoodsExtraDiscountRepository;
import com.scms.modules.goods.repository.ScmsGoodsExtraPriceRepository;
import com.scms.modules.goods.repository.ScmsGoodsInfoRepository;

@Service
public class ScmsGoodsInfoServiceImpl implements ScmsGoodsInfoService {
	
    @Autowired
    private ScmsGoodsInfoRepository scmsGoodsInfoRepository;
    
    @Autowired
    private ScmsGoodsExtraPriceRepository scmsGoodsExtraPriceRepository;
    
    @Autowired
    private ScmsGoodsExtraDiscountRepository scmsGoodsExtraDiscountRepository;
    
    @Autowired
    private ScmsGoodsModifyLogService scmsGoodsModifyLogService;
    
    @Autowired
    private ScmsShopInfoRepository scmsShopInfoRepository;
    
    @Autowired
    private ScmsGoodsInventoryService scmsGoodsInventoryService;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsInfoRepository.getList(scmsGoodsInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsInfoRepository.getListCount(scmsGoodsInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
		        			
	@Override
	public JSONObject getList2(Pageable page, ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params) {
		//查出所有数据（查出所有数据下面处理得到的数据才能正确）
		//params.put("size", 5000);
		if(params.get("size").equals("5000")){
			params.put("goodsSerialNum", "");
			params.put("goodsName", "");
		}
		List<Map<String, Object>> list = scmsGoodsInfoRepository.getList2(scmsGoodsInfo, params, page.getPageNumber(), page.getPageSize());
		List<Map<String, Object>> Imei = scmsGoodsInfoRepository.getImei(params);
		int count = scmsGoodsInfoRepository.getListCount(scmsGoodsInfo, params);
		
		JSONObject json=RestfulRetUtils.getRetSuccessWithPage(list, count);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(json+"");
		JSONObject jsonData = new JSONObject();
		JSONArray jsonObject2 =(JSONArray) jsonObject.get("rows");
		JSONObject Imeis = new JSONObject();
		
		//通过单号整合imei
		for(int s = 0; s < Imei.size(); s++){
			String shopImei=Imei.get(s).get("goodsSerialNum")+":"+Imei.get(s).get("shopId");
    		if(Imeis.get(shopImei)==null){
    			Imeis.put(shopImei,Imei.get(s).get("imei"));
    		}else{
    			Imeis.put(shopImei,Imeis.get(shopImei)+","+Imei.get(s).get("imei"));
    		}
    	}
		
		if(jsonObject2.size()>0){
			  for(int i=0;i<jsonObject2.size();i++){
			    JSONObject job = jsonObject2.getJSONObject(i);
			    
		    	//算出总库存总额
		    	int num=Integer.parseInt(job.get("goodsInventoryNum")+"");
		    	double salary= Double.parseDouble(job.get("purchasePrice")+"") ;
		    	double salaryC= num*salary;
		    	job.put("zkcSalaryC",salaryC);
		    	String dada=(String) job.get("shopName");
		    	
			    if(dada!=null){
			    	//为了对应页面显示设置key用shopId作为为一值区分对应页面显示
			    	job.put("shopName"+job.get("shopId"), job.get("shopName"));
			    	job.put("inventoryNum"+job.get("shopId"), job.get("inventoryNum"));
			    	job.put("shopId", job.get("shopId"));
			    	//算出库存总额
			    	 num=Integer.parseInt(job.get("inventoryNum")+"");
			    	 salary= Double.parseDouble(job.get("purchasePrice")+"") ;
			    	 salaryC= num*salary;
			    	job.put("SalaryC"+job.get("shopId"),salaryC);
			    	
			    	job.put("imei"+job.get("shopId"), Imeis.get(job.get("goodsSerialNum")+":"+job.get("shopId")));
			    	
		    	}
			     //把相同goodsSerialNum不同店铺的数据拼成一条数据 放进新的json
			    String aa=(String) job.get("goodsSerialNum");
		    	if(jsonData.get(aa)==null){
		    		jsonData.put(aa, job);
		    	}else{
		    		JSONObject  datas=(JSONObject) jsonData.get(job.get("goodsSerialNum"));
		    	       Iterator iterator = job.keySet().iterator();
		    	       while(iterator.hasNext()){
		    	               String key = (String) iterator.next();
		    	               if(datas.get(key)==null){
		    	            	   datas.put(key, job.get(key));
		    	               }
		    	       }
		    	}
			  }
		}
		//遍历出新拼接的数据放到原数据中返回
		Iterator iterator = jsonData.keySet().iterator();
		String datass="[";
	       while(iterator.hasNext()){
	               String key = (String) iterator.next();
	              datass+= jsonData.getString(key)+",";
	       }
	      datass= datass.substring(0,datass.length() - 1)+"]";
	      JSONArray jsonObject11 = (JSONArray) JSONObject.parse(datass);
	      
	      int pages=Integer.parseInt(params.get("page")+"");
	      int size=Integer.parseInt(params.get("size")+"");
	      int begin=pages*size;
	     
	      //分页
	      if(params.get("size").equals("5000")||params.get("goodsSerialNum")!=""||params.get("goodsName")!=""){
	    	  jsonObject.put("rows", jsonObject11);
	      }else{
	    	  String datas="[";
		      for(int i=begin;i<begin+size;i++){
		    	  datas+=  jsonObject11.get(i)+",";
		      }
		      datas= datas.substring(0,datas.length() - 1)+"]";
		      JSONArray jsonObject22 = (JSONArray) JSONObject.parse(datas);
	    	  jsonObject.put("rows", jsonObject22);
	      }
		return jsonObject;	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsGoodsInfo scmsGoodsInfo = (ScmsGoodsInfo) BeanUtils.mapToBean(params, ScmsGoodsInfo.class);
	    //判断商品货号是否已存在
	    List<ScmsGoodsInfo> list = scmsGoodsInfoRepository.findByMerchantsIdAndGoodsSerialNum(scmsGoodsInfo.getMerchantsId(), scmsGoodsInfo.getGoodsSerialNum());
	    if(list != null && list.size() > 0) {
            return RestfulRetUtils.getErrorMsg("51006","商品货号已存在，请重新输入！");
	    }
	    scmsGoodsInfo.setUseStatus("1");//设置为启用状态
		scmsGoodsInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsGoodsInfo.setCreateBy(userInfo.getUserId());
		scmsGoodsInfo.setCreateByName(userInfo.getUserName());
		scmsGoodsInfo.setCreateDate(new Date());
		if(scmsGoodsInfo.getSalePrice() == null) scmsGoodsInfo.setSalePrice(0d);
		if(scmsGoodsInfo.getPurchasePrice() == null) scmsGoodsInfo.setPurchasePrice(0d);
		scmsGoodsInfo = scmsGoodsInfoRepository.saveAndFlush(scmsGoodsInfo);
		//保存商品库存信息
		String goodsInventoryList = MapUtils.getString(params, "goodsInventoryList");
		JSONArray jsonArray = JSONObject.parseArray(goodsInventoryList);
		if(jsonArray != null && jsonArray.size() > 0) {
		    ScmsGoodsInventory obj = null;
		    for(int i = 0; i < jsonArray.size(); i++) {
		        obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsGoodsInventory.class);
		        if(obj != null) {
		            obj.setMerchantsId(scmsGoodsInfo.getMerchantsId());
		            obj.setGoodsId(scmsGoodsInfo.getId());
		            if(obj.getInventoryNum() == null) obj.setInventoryNum(0L);
		            scmsGoodsInventoryService.saveInventory(obj);
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
        //需要判断有没有修改库存商品的规格
        String inventoryIsChange = MapUtils.getString(params, "inventoryIsChange");
        if(!StringUtils.isBlank(inventoryIsChange)) {
            //将新增的规格库存补零
            String colorIdList[] = scmsGoodsInfoInDb.getColorIdList().split(",");
            String colorNameList[] = scmsGoodsInfoInDb.getColorNameList().split(",");
            String sizeIdList[] = scmsGoodsInfoInDb.getSizeIdList().split(",");
            String sizeNameList[] = scmsGoodsInfoInDb.getSizeNameList().split(",");
            List<ScmsShopInfo> shopList = scmsShopInfoRepository.findByMerchantsIdAndIsValid(scmsGoodsInfoInDb.getMerchantsId(), "Y");
            for(ScmsShopInfo shop : shopList) {
                for(int colorIndex = 0; colorIndex < colorIdList.length; colorIndex++) {
                    for(int sizeIndex = 0; sizeIndex < sizeIdList.length; sizeIndex++) {
                        Long colorId = StringUtils.toLong(colorIdList[colorIndex], null);
                        Long sizeId = StringUtils.toLong(sizeIdList[sizeIndex], null);
                        if(colorId == null || sizeId == null) continue;
                        //判断库存是否有记录，有则不用插入
                        List<ScmsGoodsInventory> tmpList = scmsGoodsInventoryService.findInventory(shop.getId(), scmsGoodsInfoInDb.getId(), colorId , sizeId);
                        if(tmpList == null || tmpList.size() == 0) {
                            ScmsGoodsInventory scmsGoodsInventory = new ScmsGoodsInventory();
                            scmsGoodsInventory.setMerchantsId(scmsGoodsInfo.getMerchantsId());
                            scmsGoodsInventory.setShopId(shop.getId());
                            scmsGoodsInventory.setGoodsId(scmsGoodsInfoInDb.getId());
                            scmsGoodsInventory.setGoodsBarcode("");
                            scmsGoodsInventory.setColorId(colorId);
                            scmsGoodsInventory.setColorName(colorNameList[colorIndex]);
                            scmsGoodsInventory.setInventorySizeId(sizeId);
                            scmsGoodsInventory.setInventorySize(sizeNameList[sizeIndex]);
                            scmsGoodsInventory.setInventoryNum(0L);
                            scmsGoodsInventoryService.saveInventory(scmsGoodsInventory);
                        }
                    }
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

    @Override
    public JSONObject getAllGoodsInventoryStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = scmsGoodsInfoRepository.getAllGoodsInventoryStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());
    }
    
    @Override
    public JSONObject getWarehouseMenu(Map<String, Object> params) {
        List<Map<String, Object>> list = scmsGoodsInfoRepository.getWarehouseMenu(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());
    }

}
