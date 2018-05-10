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
    
}
