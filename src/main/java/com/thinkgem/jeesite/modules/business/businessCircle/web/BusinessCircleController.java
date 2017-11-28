/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessCircle.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.constant.Area.AreaType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.business.businessCircle.dal.domain.BusinessCircle;
import com.thinkgem.jeesite.modules.business.businessCircle.srv.BusinessCircleService;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
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

/**
 * 商圈管理Controller
 * @author xiangnick
 * @version 2017-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/business/businesscircle/businessCircle")
public class BusinessCircleController extends BaseController {

	@Autowired
	private BusinessCircleService businessCircleService;
	@Autowired
	private AreaService areaService;

	@ModelAttribute
	public BusinessCircle get(@RequestParam(required=false) String id) {
		BusinessCircle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessCircleService.get(id);
		}
		if (entity == null){
			entity = new BusinessCircle();
		}
		return entity;
	}
	
	@RequiresPermissions("business:businesscircle:businessCircle:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessCircle businessCircle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessCircle> page = businessCircleService.findPage(new Page<BusinessCircle>(request, response), businessCircle);
		model.addAttribute("page", page);
		return "modules/business/businesscircle/businessCircleList";
	}

	@RequiresPermissions("business:businesscircle:businessCircle:view")
	@RequestMapping(value = "form")
	public String form(BusinessCircle businessCircle, Model model) {
		model.addAttribute("businessCircle", businessCircle);
		model.addAttribute("provinceList",areaService.findByType(AreaType.PROVINCE.getCode()));
		return "modules/business/businesscircle/businessCircleForm";
	}

	@RequiresPermissions("business:businesscircle:businessCircle:edit")
	@RequestMapping(value = "save")
	public String save(BusinessCircle businessCircle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessCircle)){
			return form(businessCircle, model);
		}
		businessCircleService.save(businessCircle);
		addMessage(redirectAttributes, "保存商圈成功");
		return "redirect:"+ Global.getAdminPath()+"/business/businesscircle/businessCircle/?repage";
	}
	
	@RequiresPermissions("business:businesscircle:businessCircle:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessCircle businessCircle, RedirectAttributes redirectAttributes) {
		businessCircleService.delete(businessCircle);
		addMessage(redirectAttributes, "删除商圈成功");
		return "redirect:"+ Global.getAdminPath()+"/business/businesscircle/businessCircle/?repage";
	}

}