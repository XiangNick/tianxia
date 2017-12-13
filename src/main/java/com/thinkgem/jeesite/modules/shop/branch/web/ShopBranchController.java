/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.branch.web;

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
import com.thinkgem.jeesite.modules.shop.branch.dal.domain.ShopBranch;
import com.thinkgem.jeesite.modules.shop.branch.srv.ShopBranchService;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;
import com.thinkgem.jeesite.modules.shop.shop.srv.ShopService;
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
 * 门店管理Controller
 *
 * @author xiangnick
 * @version 2017-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/branch/shopBranch")
public class ShopBranchController extends BaseController {

    @Autowired
    private ShopBranchService shopBranchService;

    @Autowired
    private ShopService shopService;
    @Autowired
    private BusinessCircleService businessCircleService;
    @Autowired
    private BusinessMarketService businessMarketService;
    @Autowired
    private BusinessMarketFloorService businessMarketFloorService;

    @ModelAttribute
    public ShopBranch get(@RequestParam(required = false) String id) {
        ShopBranch entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = shopBranchService.get(id);
        }
        if (entity == null) {
            entity = new ShopBranch();
        }
        return entity;
    }

    @RequiresPermissions("shop:branch:shopBranch:view")
    @RequestMapping(value = {"list", ""})
    public String list(ShopBranch shopBranch, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ShopBranch> page = shopBranchService.findPage(new Page<ShopBranch>(request, response), shopBranch);
        model.addAttribute("page", page);
        return "modules/shop/branch/shopBranchList";
    }

    @RequiresPermissions("shop:branch:shopBranch:view")
    @RequestMapping(value = "form")
    public String form(ShopBranch shopBranch, Model model) {
        model.addAttribute("shopBranch", shopBranch);
        model.addAttribute("shopList", shopService.findList(new Shop()));
        model.addAttribute("circleList", businessCircleService.findList(new BusinessCircle()));
        addData(shopBranch,model);
        return "modules/shop/branch/shopBranchForm";
    }

    @RequiresPermissions("shop:branch:shopBranch:edit")
    @RequestMapping(value = "save")
    public String save(ShopBranch shopBranch, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, shopBranch)) {
            return form(shopBranch, model);
        }
        //处理数据
        if(shopBranch!=null){
            String side = shopBranch.getSide();
            switch (side){
                case "1":{
                    shopBranch.setMarketFloor(null);
                    break;
                }
                case "2":{
                    shopBranch.setCircle(null);
                    shopBranch.setMarket(null);
                    shopBranch.setMarketFloor(null);
                    break;
                }
                default:{
                    break;
                }
            }
        }
        shopBranchService.save(shopBranch);
        addMessage(redirectAttributes, "保存门店成功");
        return "redirect:" + Global.getAdminPath() + "/shop/branch/shopBranch/?repage";
    }

    @RequiresPermissions("shop:branch:shopBranch:edit")
    @RequestMapping(value = "delete")
    public String delete(ShopBranch shopBranch, RedirectAttributes redirectAttributes) {
        shopBranchService.delete(shopBranch);
        addMessage(redirectAttributes, "删除门店成功");
        return "redirect:" + Global.getAdminPath() + "/shop/branch/shopBranch/?repage";
    }

    private void addData(ShopBranch shopBranch, Model model) {
        BusinessCircle circle = shopBranch.getCircle();
        if (circle != null) {
            String circleId = circle.getId();
            if (StringUtils.isNotBlank(circleId)) {
                List<BusinessMarket> marketList = businessMarketService.getMarketByCircleId(circleId);
                model.addAttribute("marketList", marketList);
                BusinessMarket market = shopBranch.getMarket();
                if (market != null) {
                    String marketId = market.getId();
                    if (StringUtils.isNotBlank(marketId)) {
                        List<BusinessMarketFloor> marketFloorList = businessMarketFloorService.getFloorListByMarketId(marketId);
                        model.addAttribute("marketFloorList", marketFloorList);
                    }
                }
            }
        }
    }
}