/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.restful;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.annotation.LogConf;
import com.gimplatform.core.annotation.LogConfOperateType;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.scms.modules.goods.entity.ScmsGoodsCategory;
import com.scms.modules.goods.entity.ScmsGoodsExtraDiscount;
import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;
import com.scms.modules.goods.entity.ScmsGoodsInfo;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.entity.ScmsGoodsInventoryFlow;
import com.scms.modules.goods.entity.ScmsGoodsModifyLog;
import com.scms.modules.goods.service.ScmsGoodsCategoryService;
import com.scms.modules.goods.service.ScmsGoodsExtraDiscountService;
import com.scms.modules.goods.service.ScmsGoodsExtraPriceService;
import com.scms.modules.goods.service.ScmsGoodsInfoService;
import com.scms.modules.goods.service.ScmsGoodsInventoryFlowService;
import com.scms.modules.goods.service.ScmsGoodsInventoryService;
import com.scms.modules.goods.service.ScmsGoodsModifyLogService;

/**
 * Restful接口
 * @version 1.0
 * @author 
 */
@RestController
@RequestMapping("/api/scms/goods")
public class GoodsRestful {

	protected static final Logger logger = LogManager.getLogger(GoodsRestful.class);
    
    @Autowired
    private ScmsGoodsCategoryService scmsGoodsCategoryService;
    
    @Autowired
    private ScmsGoodsInfoService scmsGoodsInfoService;
    
    @Autowired
    private ScmsGoodsExtraPriceService scmsGoodsExtraPriceService;
    
    @Autowired
    private ScmsGoodsExtraDiscountService scmsGoodsExtraDiscountService;
    
    @Autowired
    private ScmsGoodsInventoryService scmsGoodsInventoryService;
    
    @Autowired
    private ScmsGoodsModifyLogService scmsGoodsModifyLogService;
    
    @Autowired
    private ScmsGoodsInventoryFlowService scmsGoodsInventoryFlowService;
    
	@RequestMapping(value="/goodsCategoryIndex", method=RequestMethod.GET)
	public JSONObject goodsCategoryIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/goodsInfoIndex", method=RequestMethod.GET)
    public JSONObject goodsInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/hotGoodsIndex", method=RequestMethod.GET)
    public JSONObject hotGoodsIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/inventoryListIndex", method=RequestMethod.GET)
    public JSONObject inventoryListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/inventoryFlosIndex", method=RequestMethod.GET)
    public JSONObject inventoryFlosIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/inventoryNoticeIndex", method=RequestMethod.GET)
    public JSONObject inventoryNoticeIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getGoodsCategoryList",method=RequestMethod.GET)
	public JSONObject getGoodsCategoryList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
				ScmsGoodsCategory scmsGoodsCategory = (ScmsGoodsCategory)BeanUtils.mapToBean(params, ScmsGoodsCategory.class);				
				json = scmsGoodsCategoryService.getList(pageable, scmsGoodsCategory, params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	@RequestMapping(value="/getGoodsCategoryTreeList",method=RequestMethod.GET)
	public JSONObject getGoodsCategoryTreeList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = scmsGoodsCategoryService.getTreeList(params);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51001","获取树列表失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 新增信息
	 * @param request
	 * @param params
	 * @return
	 */
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增商品分类")
	@RequestMapping(value="/addGoodsCategory",method=RequestMethod.POST)
	public JSONObject addGoodsCategory(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = scmsGoodsCategoryService.add(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 编辑信息
	 * @param request
	 * @param params
	 * @return
	 */
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑商品分类")
	@RequestMapping(value="/editGoodsCategory",method=RequestMethod.POST)
	public JSONObject editGoodsCategory(HttpServletRequest request, @RequestBody Map<String, Object> params){
		JSONObject json = new JSONObject();
		try{
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = scmsGoodsCategoryService.edit(params, userInfo);
			}
		}catch(Exception e){
			json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 删除信息
	 * @param request
	 * @param idsList
	 * @return
	 */
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除商品分类")
	@RequestMapping(value="/delGoodsCategory",method=RequestMethod.POST)
	public JSONObject delGoodsCategory(HttpServletRequest request,@RequestBody String idsList){
		JSONObject json = new JSONObject();
		try {
			UserInfo userInfo = SessionUtils.getUserInfo();
			if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
			else {
				json = scmsGoodsCategoryService.del(idsList, userInfo);
			}
		} catch (Exception e) {
			json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
			logger.error(e.getMessage(), e);
		}
		return json;
	}

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsInfoList",method=RequestMethod.GET)
    public JSONObject getGoodsInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsInfo scmsGoodsInfo = (ScmsGoodsInfo)BeanUtils.mapToBean(params, ScmsGoodsInfo.class);              
                json = scmsGoodsInfoService.getList(pageable, scmsGoodsInfo, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    /**
     * 获取列表明细
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsInfoList2",method=RequestMethod.GET)
    public JSONObject getGoodsInfoList2(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsInfo scmsGoodsInfo = (ScmsGoodsInfo)BeanUtils.mapToBean(params, ScmsGoodsInfo.class);              
                json = scmsGoodsInfoService.getList2(pageable, scmsGoodsInfo, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取仓库菜单栏
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getWarehouseMenu",method=RequestMethod.GET)
    public JSONObject getWarehouseMenu(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {           
                json = scmsGoodsInfoService.getWarehouseMenu(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 新增信息
     * @param request
     * @param params
     * @return
     */
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增商品")
    @RequestMapping(value="/addGoodsInfo",method=RequestMethod.POST)
    public JSONObject addGoodsInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsGoodsInfoService.add(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 编辑信息
     * @param request
     * @param params
     * @return
     */
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑商品")
    @RequestMapping(value="/editGoodsInfo",method=RequestMethod.POST)
    public JSONObject editGoodsInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsGoodsInfoService.edit(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 删除信息
     * @param request
     * @param idsList
     * @return
     */
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除商品")
    @RequestMapping(value="/delGoodsInfo",method=RequestMethod.POST)
    public JSONObject delGoodsInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsGoodsInfoService.del(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsExtraPriceList",method=RequestMethod.GET)
    public JSONObject getGoodsExtraPriceList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsExtraPrice scmsGoodsExtraPrice = (ScmsGoodsExtraPrice)BeanUtils.mapToBean(params, ScmsGoodsExtraPrice.class);              
                json = scmsGoodsExtraPriceService.getList(pageable, scmsGoodsExtraPrice, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsExtraDiscountList",method=RequestMethod.GET)
    public JSONObject getGoodsExtraDiscountList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsExtraDiscount scmsGoodsExtraDiscount = (ScmsGoodsExtraDiscount)BeanUtils.mapToBean(params, ScmsGoodsExtraDiscount.class);              
                json = scmsGoodsExtraDiscountService.getList(pageable, scmsGoodsExtraDiscount, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsInventoryList",method=RequestMethod.GET)
    public JSONObject getGoodsInventoryList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsInventory scmsGoodsInventory = (ScmsGoodsInventory)BeanUtils.mapToBean(params, ScmsGoodsInventory.class);              
                json = scmsGoodsInventoryService.getList(pageable, scmsGoodsInventory, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取单个商品的库存统计(包括店铺数量，店铺库存总数，总成本)
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getStatisticsGoodsInventory",method=RequestMethod.GET)
    public JSONObject getStatisticsGoodsInventory(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {           
                json = scmsGoodsInventoryService.getStatisticsGoodsInventory(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsModifyLogList",method=RequestMethod.GET)
    public JSONObject getGoodsModifyLogList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsModifyLog scmsGoodsModifyLog = (ScmsGoodsModifyLog)BeanUtils.mapToBean(params, ScmsGoodsModifyLog.class);              
                json = scmsGoodsModifyLogService.getList(pageable, scmsGoodsModifyLog, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取商品库存流水列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getGoodsInventoryFlowList",method=RequestMethod.GET)
    public JSONObject getGoodsInventoryFlowList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsGoodsInventoryFlow scmsGoodsInventoryFlow = (ScmsGoodsInventoryFlow)BeanUtils.mapToBean(params, ScmsGoodsInventoryFlow.class);              
                json = scmsGoodsInventoryFlowService.getList(pageable, scmsGoodsInventoryFlow, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取所有商品的库存统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllGoodsInventoryStatistics",method=RequestMethod.GET)
    public JSONObject getAllGoodsInventoryStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {           
                json = scmsGoodsInfoService.getAllGoodsInventoryStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
