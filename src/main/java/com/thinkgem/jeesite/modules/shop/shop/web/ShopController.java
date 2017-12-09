/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.shop.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.base.bean.FileUploadResult;
import com.thinkgem.jeesite.modules.shop.shop.bean.UploadFileRecord;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;
import com.thinkgem.jeesite.modules.shop.shop.srv.ShopService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 商户管理Controller
 *
 * @author xiangnick
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shop/shop")
public class ShopController extends BaseController {


    @Autowired
    private ShopService shopService;

    @ModelAttribute
    public Shop get(@RequestParam(required = false) String id) {
        Shop entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = shopService.get(id);
        }
        if (entity == null) {
            entity = new Shop();
        }
        return entity;
    }

    @RequiresPermissions("shop:shop:shop:view")
    @RequestMapping(value = {"list", ""})
    public String list(Shop shop, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Shop> page = shopService.findPage(new Page<Shop>(request, response), shop);
        model.addAttribute("page", page);
        return "modules/shop/shop/shopList";
    }

    @RequiresPermissions("shop:shop:shop:view")
    @RequestMapping(value = "form")
    public String form(Shop shop, Model model) {
        model.addAttribute("shop", shop);
        String idPhotosJsonStr = shop.getIdPhotos();
        if (StringUtils.isNotBlank(idPhotosJsonStr)) {
            Map map = JSON.parseObject(idPhotosJsonStr, Map.class);
            model.addAllAttributes(map);
        }
        return "modules/shop/shop/shopForm";
    }

    @RequiresPermissions("shop:shop:shop:edit")
    @RequestMapping(value = "save")
    public String save(Shop shop, MultipartFile license, MultipartFile idCardFront, MultipartFile idCardContrary, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, shop)) {
            return form(shop, model);
        }
        FileUploadResult result;
        UploadFileRecord record = new UploadFileRecord();
        if (!isNewRecord(shop)) {
            //说明是修改记录,查出原先的json值
            JSONObject jsonObject = JSON.parseObject(shop.getIdPhotos());
            record.setLicense((String) jsonObject.get("license"));
            record.setIdCardFront((String) jsonObject.get("idCardFront"));
            record.setIdCardContrary((String) jsonObject.get("idCardContrary"));
        }
        result = shopService.process(record, shop, license, idCardFront, idCardContrary);
        addMessage(redirectAttributes, result.getMsg());
        return "redirect:" + Global.getAdminPath() + "/shop/shop/shop/?repage";
    }

    @RequiresPermissions("shop:shop:shop:edit")
    @RequestMapping(value = "delete")
    public String delete(Shop shop, RedirectAttributes redirectAttributes) {
        shopService.delete(shop);
        addMessage(redirectAttributes, "删除商户成功");
        return "redirect:" + Global.getAdminPath() + "/shop/shop/shop/?repage";
    }


    private boolean isNewRecord(Shop shop) {
        return StringUtils.isBlank(shop.getId());
    }
}