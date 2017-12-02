/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarket.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.business.businessCircle.dal.domain.BusinessCircle;
import com.thinkgem.jeesite.modules.business.businessCircle.srv.BusinessCircleService;
import com.thinkgem.jeesite.modules.business.businessMarket.dal.domain.BusinessMarket;
import com.thinkgem.jeesite.modules.business.businessMarket.srv.BusinessMarketService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商场管理Controller
 * @author xiangnick
 * @version 2017-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/business/businessmarket/businessMarket")
public class BusinessMarketController extends BaseController {

	@Autowired
	private BusinessMarketService businessMarketService;

	@Autowired
	private BusinessCircleService businessCircleService;

	@ModelAttribute
	public BusinessMarket get(@RequestParam(required=false) String id) {
		BusinessMarket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessMarketService.get(id);
		}
		if (entity == null){
			entity = new BusinessMarket();
		}
		return entity;
	}
	
	@RequiresPermissions("business:businessmarket:businessMarket:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessMarket businessMarket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessMarket> page = businessMarketService.findPage(new Page<BusinessMarket>(request, response), businessMarket);
		model.addAttribute("page", page);
		return "modules/business/businessmarket/businessMarketList";
	}

	@RequiresPermissions("business:businessmarket:businessMarket:view")
	@RequestMapping(value = "form")
	public String form(BusinessMarket businessMarket, Model model) {
		model.addAttribute("businessMarket", businessMarket);
		List<BusinessCircle> list = businessCircleService.findList(new BusinessCircle());
		model.addAttribute("businessCircleList",list);
		return "modules/business/businessmarket/businessMarketForm";
	}

	@RequiresPermissions("business:businessmarket:businessMarket:edit")
	@RequestMapping(value = "save")
	public String save(BusinessMarket businessMarket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessMarket)){
			return form(businessMarket, model);
		}
		businessMarketService.save(businessMarket);
		addMessage(redirectAttributes, "保存商场成功");
		return "redirect:"+ Global.getAdminPath()+"/business/businessmarket/businessMarket/?repage";
	}
	
	@RequiresPermissions("business:businessmarket:businessMarket:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessMarket businessMarket, RedirectAttributes redirectAttributes) {
		businessMarketService.delete(businessMarket);
		addMessage(redirectAttributes, "删除商场成功");
		return "redirect:"+ Global.getAdminPath()+"/business/businessmarket/businessMarket/?repage";
	}

}