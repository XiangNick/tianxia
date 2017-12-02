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
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商圈管理Controller
 * @author xiangnick
 * @version 2017-11-30
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
		model.addAttribute("provinceList",areaService.findByType(AreaType.PROVINCE.getCode()));
		addPCRData(businessCircle,model);
		return "modules/business/businesscircle/businessCircleList";
	}

	/**
	 * 添加省市区到页面model
	 * @param businessCircle
	 * @param model
	 */
	private void addPCRData(BusinessCircle businessCircle,Model model){
		String provinceId = businessCircle.getProvince();
		if(StringUtils.isNotBlank(provinceId)){
			List<Area> cityList = areaService.findByLinkage(provinceId);
			model.addAttribute("cityList",cityList);
			String cityId = businessCircle.getCity();
			if(StringUtils.isNotBlank(cityId)){
				List<Area> regionList = areaService.findByLinkage(cityId);
				model.addAttribute("regionList",regionList);
			}
		}
	}
	@RequiresPermissions("business:businesscircle:businessCircle:view")
	@RequestMapping(value = "form")
	public String form(BusinessCircle businessCircle, Model model) {
		model.addAttribute("businessCircle", businessCircle);
		model.addAttribute("provinceList",areaService.findByType(AreaType.PROVINCE.getCode()));
		addPCRData(businessCircle,model);
		return "modules/business/businesscircle/businessCircleForm";
	}


	@RequiresPermissions("business:businesscircle:businessCircle:edit")
	@RequestMapping(value = "save")
	public String save(BusinessCircle businessCircle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessCircle)){
			return form(businessCircle, model);
		}
		User user = UserUtils.getUser();
		businessCircle.setUpdateBy(user);
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

	@RequestMapping(value = "linkage",method = RequestMethod.POST)
	@ResponseBody
	public List<Area> linkage(String parentId){
		return areaService.findByLinkage(parentId);
	}

	/**
	 * 根据商圈id获取商圈的省市区冗余字段
	 * @param circleId
	 * @return
	 */
	@RequestMapping(value = "getCirclePCRByCircleId",method = RequestMethod.POST)
	@ResponseBody
	public String getCirclePCRByCircleId(String circleId){
		return	businessCircleService.getCirclePCRByCircleId(circleId);
	}
}