/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.tree.Tree;
import com.gimplatform.core.tree.TreeNode;
import com.gimplatform.core.tree.TreeNodeExtend;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.goods.service.ScmsGoodsCategoryService;
import com.scms.modules.goods.entity.ScmsGoodsCategory;
import com.scms.modules.goods.entity.ScmsGoodsCategoryRecur;
import com.scms.modules.goods.repository.ScmsGoodsCategoryRepository;

@Service
public class ScmsGoodsCategoryServiceImpl implements ScmsGoodsCategoryService {
	
    @Autowired
    private ScmsGoodsCategoryRepository scmsGoodsCategoryRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsCategoryRepository.getList(scmsGoodsCategory, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsCategoryRepository.getListCount(scmsGoodsCategory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsGoodsCategory scmsGoodsCategory = (ScmsGoodsCategory) BeanUtils.mapToBean(params, ScmsGoodsCategory.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsGoodsCategory);
        if(json != null) return json;
		scmsGoodsCategory.setIsValid(Constants.IS_VALID_VALID);
		scmsGoodsCategory.setCreateBy(userInfo.getUserId());
		scmsGoodsCategory.setCreateDate(new Date());
		scmsGoodsCategory = scmsGoodsCategoryRepository.saveAndFlush(scmsGoodsCategory);
		if(scmsGoodsCategory.getDispOrder() == null) {
		    scmsGoodsCategoryRepository.updateDispOrder(scmsGoodsCategory.getId(), scmsGoodsCategory.getId());
		}
		//更新递归表
		addCategoryRecur(scmsGoodsCategory);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsGoodsCategory scmsGoodsCategory = (ScmsGoodsCategory) BeanUtils.mapToBean(params, ScmsGoodsCategory.class);
		ScmsGoodsCategory scmsGoodsCategoryInDb = scmsGoodsCategoryRepository.findOne(scmsGoodsCategory.getId());
		if(scmsGoodsCategoryInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsGoodsCategory.getCategoryName().equals(scmsGoodsCategoryInDb.getCategoryName())) {
            JSONObject json = judgeExist(scmsGoodsCategory);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsGoodsCategory, scmsGoodsCategoryInDb);
		scmsGoodsCategoryRepository.save(scmsGoodsCategoryInDb);
        //更新递归表
        addCategoryRecur(scmsGoodsCategoryInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		Long id = StringUtils.toLong(idsList);
		List<Long> pids = new ArrayList<Long>();
		List<Long> allIds = new ArrayList<Long>();
		pids.add(id);
		allIds.add(id);
		int deep = Constants.DEFAULT_TREE_DEEP;
		while (!pids.isEmpty() && pids.size() > 0 && deep > 0) {
			List<ScmsGoodsCategory> list = scmsGoodsCategoryRepository.getListByParentIds(pids);
			pids.clear();
			for (ScmsGoodsCategory obj : list) {
				pids.add(obj.getId());
				allIds.add(obj.getId());
			}
			deep--;
		}
		// 批量更新（设置IsValid 为N）
		if (allIds.size() > 0) {
			scmsGoodsCategoryRepository.delEntity(Constants.IS_VALID_INVALID, allIds);
		}
		return RestfulRetUtils.getRetSuccess();
	}

	public JSONObject getTreeList(Map<String, Object> params){
		//List<ScmsGoodsCategory> list = scmsGoodsCategoryRepository.getTreeList();
	    List<Map<String, Object>> list = scmsGoodsCategoryRepository.getList(null, params, 0, 10000);
		return getJsonTree(list);
	}

	public List<ScmsGoodsCategory> getListByParentIds(ScmsGoodsCategory scmsGoodsCategory){
		List<Long> idList = new ArrayList<>();
		Long parentId = (long) 0;
		if (scmsGoodsCategory != null) {
			parentId = scmsGoodsCategory.getParentId();

		}
		List<ScmsGoodsCategory> treeList = new ArrayList<>();
		if (parentId == 0) {
			treeList = scmsGoodsCategoryRepository.getListByRoot();
		} else {
			idList.add(parentId);
			treeList = scmsGoodsCategoryRepository.getListByParentIds(idList);
		}
		return treeList;
	}
    
    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsGoodsCategory scmsGoodsCategory) {
        if("1".equals(scmsGoodsCategory.getType())) {
            List<ScmsGoodsCategory> list = scmsGoodsCategoryRepository.findByParentIdAndCategoryNameAndType(scmsGoodsCategory.getParentId(), scmsGoodsCategory.getCategoryName(), scmsGoodsCategory.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","同级节点不允许有相同的名称！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsGoodsCategory> list = scmsGoodsCategoryRepository.findByParentIdAndCategoryNameAndType(scmsGoodsCategory.getParentId(), scmsGoodsCategory.getCategoryName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","同级节点不允许有相同的名称！");
            }
            list = scmsGoodsCategoryRepository.findByParentIdAndCategoryNameAndTypeAndMerchantsId(scmsGoodsCategory.getParentId(), scmsGoodsCategory.getCategoryName(), scmsGoodsCategory.getType(), scmsGoodsCategory.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","同级节点不允许有相同的名称！");
            }
        }
        return null;
    }
	
	/**
	 * 获取json格式的树
	 * @param list
	 * @return
	 */
	private JSONObject getJsonTree(List<Map<String, Object>> list) {
		TreeNode root = new TreeNode("root", "all", null, false);
		Map<String, String> mapAttr = null;
		TreeNodeExtend treeNode = null;
		String id = "", text = "", parent = "";
		Tree tree = new Tree(true);
		// 添加一个自定义的根节点
		if (list == null || list.isEmpty()) {
			treeNode = new TreeNodeExtend("-1", "虚拟节点", "", false, null);
			tree.addNode(treeNode);
		}else{
		    Long tmpId = null;
			for (Map<String, Object> obj : list) {
			    tmpId = MapUtils.getLong(obj, "id", null);
				if (obj == null || tmpId == null) continue;
				id = tmpId + "";
				text = MapUtils.getString(obj, "categoryName");
				parent = MapUtils.getString(obj, "parentId", "");
				
				//Map<String, Object>转Map<String, String>
				mapAttr = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : obj.entrySet()) {
				    mapAttr.put(entry.getKey(), StringUtils.toString(entry.getValue(), ""));
				}
				treeNode = new TreeNodeExtend(id, text, parent, false, mapAttr);
				tree.addNode(treeNode);
			}
		}
		String strTree = tree.getTreeJson(tree, root);
		return RestfulRetUtils.getRetSuccess(JSONArray.parseArray(strTree));
	}

    private void addCategoryRecur(ScmsGoodsCategory scmsGoodsCategory) {
        scmsGoodsCategoryRepository.delCategoryRecurByCategoryChildId(scmsGoodsCategory.getId());
        // 保存当前节点信息
        ScmsGoodsCategoryRecur scmsGoodsCategoryRecur = null;
        scmsGoodsCategoryRepository.saveCategoryRecur(scmsGoodsCategory.getId(), scmsGoodsCategory.getId());

        Long parentId = scmsGoodsCategory.getParentId();
        if (parentId != null) {
            int deep = 0;
            ScmsGoodsCategory parentObj = null;
            while (parentId != null && deep < Constants.DEFAULT_TREE_DEEP) {
                parentObj = scmsGoodsCategoryRepository.findOne(parentId);
                if (parentObj != null) {
                    scmsGoodsCategoryRecur = new ScmsGoodsCategoryRecur();
                    scmsGoodsCategoryRecur.setCategoryId(parentObj.getId());
                    scmsGoodsCategoryRecur.setCategoryChildId(scmsGoodsCategory.getId());
                    parentId = parentObj.getParentId();
                    scmsGoodsCategoryRepository.saveCategoryRecur(scmsGoodsCategoryRecur.getCategoryId(), scmsGoodsCategoryRecur.getCategoryChildId());
                } else {
                    parentId = null;
                }
                deep++;
            }
        }
    }
}
