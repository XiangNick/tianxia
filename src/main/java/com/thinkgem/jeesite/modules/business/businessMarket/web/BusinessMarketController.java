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
import com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.domain.BusinessMarketFloor;
import com.thinkgem.jeesite.modules.business.businessMarketFloor.srv.BusinessMarketFloorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

	@Autowired
	private BusinessMarketFloorService businessMarketFloorService;

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

	@RequestMapping(value = "saveMarketFloor")
	public String save(BusinessMarketFloor businessMarketFloor,BusinessMarket businessMarket, HttpServletRequest request, HttpServletResponse response, Model model) {
		businessMarketFloorService.save(businessMarketFloor);
		return "redirect:/a/business/businessmarket/businessMarket/list";
	}

	@RequestMapping(value = "delFloor",method = RequestMethod.GET)
	public String delFloor(String floorId){
		BusinessMarketFloor floor = new BusinessMarketFloor();
		floor.setId(floorId);
		businessMarketFloorService.delete(floor);
		return "redirect:/a/business/businessmarket/businessMarket/list";
	}

	@RequestMapping(value = "updateFloorByCondition",method = RequestMethod.POST)
	public String updateFloorByCondition(BusinessMarketFloor businessMarketFloor){
		businessMarketFloorService.updateFloorByCondition(businessMarketFloor);
		return "redirect:/a/business/businessmarket/businessMarket/list";
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

	@RequestMapping(value = "getFloorListByMarketId",method = RequestMethod.GET)
	@ResponseBody
	public List<BusinessMarketFloor> getFloorListByMarketId(String marketId){
		return businessMarketFloorService.getFloorListByMarketId(marketId);
	}

	@RequestMapping(value = "getFloorById",method = RequestMethod.GET)
	@ResponseBody
	public BusinessMarketFloor getFloorById(String floorId){
		return businessMarketFloorService.get(floorId);
	}

	@RequestMapping(value = "getMarketByCircleId",method = RequestMethod.POST)
	@ResponseBody
	public List<BusinessMarket> getMarketByCircleId(String circleId){
		if(StringUtils.isNotBlank(circleId)){
			return businessMarketService.getMarketByCircleId(circleId);
		}
		return new ArrayList<BusinessMarket>();
	}

}