package com.scms.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.scms.modules.finance.entity.ScmsDailyExpenses;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.service.ScmsDailyExpensesService;
import com.scms.modules.finance.service.ScmsFinanceFlowService;

@RestController
@RequestMapping(value = "/api/scms/finance")
public class FinanceRestful {
    
    protected static final Logger logger = LogManager.getLogger(FinanceRestful.class);
    
    @Autowired
    private ScmsDailyExpensesService scmsDailyExpensesService;
    
    @Autowired
    private ScmsFinanceFlowService scmsFinanceFlowService;

    @RequestMapping(value="/dailyExpensensIndex", method=RequestMethod.GET)
    public JSONObject dailyExpensensIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/flowIndex", method=RequestMethod.GET)
    public JSONObject flowIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/customerCheckIndex", method=RequestMethod.GET)
    public JSONObject customerCheckIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/supplierCheckIndex", method=RequestMethod.GET)
    public JSONObject supplierCheckIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getDailyExpensesList",method=RequestMethod.GET)
    public JSONObject getDailyExpensesList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsDailyExpenses scmsDailyExpenses = (ScmsDailyExpenses)BeanUtils.mapToBean(params, ScmsDailyExpenses.class);              
                json = scmsDailyExpensesService.getList(pageable, scmsDailyExpenses, params);
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增日常支出")
    @RequestMapping(value="/addDailyExpenses",method=RequestMethod.POST)
    public JSONObject addDailyExpenses(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsDailyExpensesService.add(params, userInfo);
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑日常支出")
    @RequestMapping(value="/editDailyExpenses",method=RequestMethod.POST)
    public JSONObject editDailyExpenses(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsDailyExpensesService.edit(params, userInfo);
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除日常支出")
    @RequestMapping(value="/delDailyExpenses",method=RequestMethod.POST)
    public JSONObject delDailyExpenses(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsDailyExpensesService.del(idsList, userInfo);
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
    @RequestMapping(value="/getFinanceFlowList",method=RequestMethod.GET)
    public JSONObject getFinanceFlowList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsFinanceFlow scmsFinanceFlow = (ScmsFinanceFlow)BeanUtils.mapToBean(params, ScmsFinanceFlow.class);              
                json = scmsFinanceFlowService.getList(pageable, scmsFinanceFlow, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取财务流水统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getFinanceFlowStatistics",method=RequestMethod.GET)
    public JSONObject getFinanceFlowStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsFinanceFlowService.getFinanceFlowStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
