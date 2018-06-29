package com.scms.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.DictService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.scms.modules.base.entity.ScmsColorInfo;
import com.scms.modules.base.entity.ScmsPayInfo;
import com.scms.modules.base.entity.ScmsPrintInfo;
import com.scms.modules.base.entity.ScmsSizeInfo;
import com.scms.modules.base.entity.ScmsTextureInfo;
import com.scms.modules.base.entity.ScmsTransportInfo;
import com.scms.modules.base.entity.ScmsVenderInfo;
import com.scms.modules.base.repository.ScmsColorInfoRepository;
import com.scms.modules.base.repository.ScmsPayInfoRepository;
import com.scms.modules.base.repository.ScmsPrintInfoRepository;
import com.scms.modules.base.repository.ScmsShopInfoRepository;
import com.scms.modules.base.repository.ScmsSizeInfoRepository;
import com.scms.modules.base.repository.ScmsTextureInfoRepository;
import com.scms.modules.base.repository.ScmsTransportInfoRepository;
import com.scms.modules.base.repository.ScmsVenderInfoRepository;
import com.scms.modules.customer.entity.ScmsCustomerLevel;
import com.scms.modules.customer.entity.ScmsCustomerType;
import com.scms.modules.customer.repository.ScmsCustomerLevelRepository;
import com.scms.modules.customer.repository.ScmsCustomerTypeRepository;
import com.scms.modules.goods.service.ScmsGoodsCategoryService;

@RestController
@RequestMapping(value = "/api/scms/app")
public class AppRestful {
    
    protected static final Logger logger = LogManager.getLogger(AppRestful.class);

    @Autowired
    private DictService dictService;
    
    @Autowired
    private ScmsShopInfoRepository scmsShopInfoRepository;
    
    @Autowired
    private ScmsCustomerTypeRepository scmsCustomerTypeRepository;
    
    @Autowired
    private ScmsCustomerLevelRepository scmsCustomerLevelRepository;
    
    @Autowired
    private ScmsColorInfoRepository scmsColorInfoRepository;
    
    @Autowired
    private ScmsSizeInfoRepository scmsSizeInfoRepository;
    
    @Autowired
    private ScmsTextureInfoRepository scmsTextureInfoRepository;
    
    @Autowired
    private ScmsVenderInfoRepository scmsVenderInfoRepository;
    
    @Autowired
    private ScmsTransportInfoRepository scmsTransportInfoRepository;
    
    @Autowired
    private ScmsPayInfoRepository scmsPayInfoRepository;
    
    @Autowired
    private ScmsGoodsCategoryService scmsGoodsCategoryService;
    
    @Autowired
    private ScmsPrintInfoRepository scmsPrintInfoRepository;

    /**
     * 获取APP初始化信息
     * @param request
     * @return
     */
    @RequestMapping(value="/getAppInit",method=RequestMethod.GET)
    public JSONObject getMerchantsInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json.put("dictSexType", dictService.getDictDataByDictTypeValue("SYS_SEX_TYPE", userInfo));
                json.put("dictTagType", dictService.getDictDataByDictTypeValue("SCMS_TAG_TYPE", userInfo));
                json.put("dictGoodsYear", dictService.getDictDataByDictTypeValue("SCMS_GOODS_YEAR", userInfo));
                json.put("dictGoodsSeason", dictService.getDictDataByDictTypeValue("SCMS_GOODS_SEASON", userInfo));
                json.put("dictGoodsBuyStatus", dictService.getDictDataByDictTypeValue("SCMS_GOODS_BUY_STATUS", userInfo));
                json.put("dictGoodsShelfStatus", dictService.getDictDataByDictTypeValue("SCMS_GOODS_SHELF_STATUS", userInfo));
                json.put("dictGoodsUseStatus", dictService.getDictDataByDictTypeValue("SCMS_GOODS_USE_STATUS", userInfo));
                json.put("dictOrderStatus", dictService.getDictDataByDictTypeValue("SCMS_ORDER_STATUS", userInfo));
                json.put("dictOrderType", dictService.getDictDataByDictTypeValue("SCMS_ORDER_TYPE", userInfo));
                json.put("dictOrderPay", dictService.getDictDataByDictTypeValue("SCMS_ORDER_PAY_STATUS", userInfo));
                json.put("dictOrderSendStatus", dictService.getDictDataByDictTypeValue("SCMS_ORDER_SEND_STATUS", userInfo));
                json.put("dictOrderReceiveStatus", dictService.getDictDataByDictTypeValue("SCMS_ORDER_RECEIVE_STATUS", userInfo));
                
                int page = 0, size = 1000;
                List<Map<String, Object>> list = null, keyValList = null;
                Map<String, Object> keyValMap = null;
                //获取店铺列表
                json.put("shopList", scmsShopInfoRepository.getShopKeyVal(MapUtils.getLong(params, "merchantsId")));
                
                //获取客户类型
                ScmsCustomerType scmsCustomerType = (ScmsCustomerType)BeanUtils.mapToBean(params, ScmsCustomerType.class);  
                list = scmsCustomerTypeRepository.getList(scmsCustomerType, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "typeName"));
                    keyValList.add(keyValMap);
                }
                json.put("customerType", keyValList);
                
                //获取客户等级
                ScmsCustomerLevel scmsCustomerLevel = (ScmsCustomerLevel)BeanUtils.mapToBean(params, ScmsCustomerLevel.class); 
                list = scmsCustomerLevelRepository.getList(scmsCustomerLevel, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "levelName"));
                    keyValList.add(keyValMap);
                }
                json.put("customerLevel", keyValList);
                
                //获取颜色
                ScmsColorInfo scmsColorInfo = (ScmsColorInfo)BeanUtils.mapToBean(params, ScmsColorInfo.class);
                list = scmsColorInfoRepository.getList(scmsColorInfo, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "colorName"));
                    keyValList.add(keyValMap);
                }
                json.put("colorList", keyValList);
                
                //材质
                ScmsTextureInfo scmsTextureInfo = (ScmsTextureInfo)BeanUtils.mapToBean(params, ScmsTextureInfo.class);
                list = scmsTextureInfoRepository.getList(scmsTextureInfo, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "textureName"));
                    keyValList.add(keyValMap);
                }
                json.put("textureList", keyValList);
                
                //尺码
                ScmsSizeInfo scmsSizeInfo = (ScmsSizeInfo)BeanUtils.mapToBean(params, ScmsSizeInfo.class);
                list = scmsSizeInfoRepository.getList(scmsSizeInfo, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "sizeName"));
                    keyValList.add(keyValMap);
                }
                json.put("sizeList", keyValList);
                
                //厂家
                ScmsVenderInfo scmsVenderInfo = (ScmsVenderInfo)BeanUtils.mapToBean(params, ScmsVenderInfo.class);
                list = scmsVenderInfoRepository.getList(scmsVenderInfo, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "venderName"));
                    keyValList.add(keyValMap);
                }
                json.put("venderInfoList", keyValList);
                
                //运输方式
                ScmsTransportInfo scmsTransportInfo = (ScmsTransportInfo)BeanUtils.mapToBean(params, ScmsTransportInfo.class);
                list = scmsTransportInfoRepository.getList(scmsTransportInfo, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "transportName"));
                    keyValList.add(keyValMap);
                }
                json.put("transportList", keyValList);
                
                //支付方式
                ScmsPayInfo scmsPayInfo = (ScmsPayInfo)BeanUtils.mapToBean(params, ScmsPayInfo.class);
                list = scmsPayInfoRepository.getList(scmsPayInfo, params, page, size);
                keyValList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> map : list) {
                    keyValMap = new HashMap<String, Object>();
                    keyValMap.put("ID", MapUtils.getString(map, "id"));
                    keyValMap.put("NAME", MapUtils.getString(map, "payName"));
                    keyValList.add(keyValMap);
                }
                json.put("payInfoList", keyValList);
                
                //获取商品分类列表 
                json.put("goodsCategoryTree", scmsGoodsCategoryService.getTreeList(params).get("RetData"));
                
                //打印信息
                ScmsPrintInfo scmsPrintInfo = (ScmsPrintInfo)BeanUtils.mapToBean(params, ScmsPrintInfo.class);
                json.put("printInfoList", scmsPrintInfoRepository.getList(scmsPrintInfo, params, page, size));
                
                json.put("RetCode", "000000");
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取APP初始化信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
