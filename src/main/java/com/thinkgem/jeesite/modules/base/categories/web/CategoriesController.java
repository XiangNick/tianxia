/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.categories.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.base.categories.dal.domain.Categories;
import com.thinkgem.jeesite.modules.base.categories.srv.CategoriesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 行业分类Controller
 * @author xiangnick
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/base/categories/categories")
public class CategoriesController extends BaseController {

	@Autowired
	private CategoriesService categoriesService;
	
	@ModelAttribute
	public Categories get(@RequestParam(required=false) String id) {
		Categories entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = categoriesService.get(id);
		}
		if (entity == null){
			entity = new Categories();
		}
		return entity;
	}
	
	@RequiresPermissions("base:categories:categories:view")
	@RequestMapping(value = {"list", ""})
	public String list(Categories categories, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Categories> list = categoriesService.findList(categories); 
		model.addAttribute("list", list);
		return "modules/base/categories/categoriesList";
	}

	@RequiresPermissions("base:categories:categories:view")
	@RequestMapping(value = "form")
	public String form(Categories categories, Model model) {
		if (categories.getParent()!=null && StringUtils.isNotBlank(categories.getParent().getId())){
			categories.setParent(categoriesService.get(categories.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(categories.getId())){
				Categories categoriesChild = new Categories();
				categoriesChild.setParent(new Categories(categories.getParent().getId()));
				List<Categories> list = categoriesService.findList(categories); 
				if (list.size() > 0){
					categories.setSort(list.get(list.size()-1).getSort());
					if (categories.getSort() != null){
						categories.setSort(categories.getSort() + 30);
					}
				}
			}
		}
		if (categories.getSort() == null){
			categories.setSort(30);
		}
		model.addAttribute("categories", categories);
		return "modules/base/categories/categoriesForm";
	}

	@RequiresPermissions("base:categories:categories:edit")
	@RequestMapping(value = "save")
	public String save(Categories categories, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, categories)){
			return form(categories, model);
		}
		categoriesService.save(categories);
		addMessage(redirectAttributes, "保存行业分类成功");
		return "redirect:"+ Global.getAdminPath()+"/base/categories/categories/?repage";
	}
	
	@RequiresPermissions("base:categories:categories:edit")
	@RequestMapping(value = "delete")
	public String delete(Categories categories, RedirectAttributes redirectAttributes) {
		categoriesService.delete(categories);
		addMessage(redirectAttributes, "删除行业分类成功");
		return "redirect:"+ Global.getAdminPath()+"/base/categories/categories/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Categories> list = categoriesService.findList(new Categories());
		for (int i=0; i<list.size(); i++){
			Categories e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}