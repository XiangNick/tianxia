/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.pos.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.branch.dal.domain.ShopBranch;
import com.thinkgem.jeesite.modules.shop.branch.srv.ShopBranchService;
import com.thinkgem.jeesite.modules.shop.pos.dal.domain.Pos;
import com.thinkgem.jeesite.modules.shop.pos.srv.PosService;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;
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
 * pos机管理Controller
 * @author xiangnick
 * @version 2017-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/pos/pos")
public class PosController extends BaseController {

	@Autowired
	private PosService posService;
	@Autowired
	private ShopBranchService shopBranchService;
	
	@ModelAttribute
	public Pos get(@RequestParam(required=false) String id) {
		Pos entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = posService.get(id);
		}
		if (entity == null){
			entity = new Pos();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shop:shop:view")
	@RequestMapping(value = {"list", ""})
	public String list(Pos pos, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Pos> page = posService.findPage(new Page<Pos>(request, response), pos);
		model.addAttribute("page", page);
		return "modules/shop/pos/posList";
	}

	@RequestMapping(value = "posList")
	public String posList(String shopId,String shopName,Model model){
		List<Pos> postList = posService.findPostListByShopId(shopId);
		model.addAttribute("shopId",shopId);
		model.addAttribute("shopName",shopName);
		model.addAttribute("postList", postList);
		return "modules/shop/pos/posList";
	}


	@RequiresPermissions("shop:shop:shop:view")
	@RequestMapping(value = "form")
	public String form(Pos pos, Model model) {
		model.addAttribute("pos", pos);
		//添加门店list
		if(pos!=null){
			Shop shop = pos.getShop();
			if(shop!=null){
				String shopId = shop.getId();
				if(StringUtils.isNotBlank(shopId)){
					List<ShopBranch> branchList = shopBranchService.getListByShopId(shopId);
					model.addAttribute("branchList",branchList);
				}
			}
		}
		return "modules/shop/pos/posForm";
	}

	@RequiresPermissions("shop:shop:shop:edit")
	@RequestMapping(value = "save")
	public String save(Pos pos, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pos)){
			return form(pos, model);
		}
		posService.save(pos);
		addMessage(redirectAttributes, "保存pos机成功");
		return "redirect:"+ Global.getAdminPath()+"/shop/pos/pos/posList?shopId="+pos.getShop().getId();
	}
	
	@RequiresPermissions("shop:shop:shop:edit")
	@RequestMapping(value = "delete")
	public String delete(Pos pos, RedirectAttributes redirectAttributes) {
		posService.delete(pos);
		addMessage(redirectAttributes, "删除pos机成功");
		return "redirect:"+ Global.getAdminPath()+"/shop/pos/pos/?repage";
	}

}