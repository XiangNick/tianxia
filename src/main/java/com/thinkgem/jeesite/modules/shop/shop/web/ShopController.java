/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.shop.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.base.bean.FileUploadResult;
import com.thinkgem.jeesite.modules.base.constant.FileUploadConstant;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
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
        if(StringUtils.isNotBlank(idPhotosJsonStr)){
            Map map = JSON.parseObject(idPhotosJsonStr, Map.class);
//            JSONObject obj = JSONObject.parseObject(idPhotosJsonStr);
//            String license = (String) obj.get("license");
//            String idCardFront = (String) obj.get("idCardFront");
//            String idCardContrary = (String) obj.get("idCardContrary");
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
        FileUploadResult result = validatePic(license, idCardFront, idCardContrary);
        if (!result.isSuccess()) {
            addMessage(redirectAttributes, result.getMsg());
            return "redirect:" + Global.getAdminPath() + "/shop/shop/shop/?repage";
        }
        Map<String, MultipartFile> uploadFileMap = new LinkedHashMap<String, MultipartFile>();
        uploadFileMap.put("license", license);
        uploadFileMap.put("idCardFront", idCardFront);
        uploadFileMap.put("idCardContrary", idCardContrary);
        Map<String, String> uploadUrlMap = uploadFile(uploadFileMap);
        shop.setIdPhotos(JSONUtils.toJSONString(uploadUrlMap));
        shop.setBusinessId("记得输入businessId");
        shopService.save(shop);
        addMessage(redirectAttributes, "保存商户成功");
        return "redirect:" + Global.getAdminPath() + "/shop/shop/shop/?repage";
    }

    @RequiresPermissions("shop:shop:shop:edit")
    @RequestMapping(value = "delete")
    public String delete(Shop shop, RedirectAttributes redirectAttributes) {
        shopService.delete(shop);
        addMessage(redirectAttributes, "删除商户成功");
        return "redirect:" + Global.getAdminPath() + "/shop/shop/shop/?repage";
    }

    private FileUploadResult validatePic(MultipartFile... files) {
        FileUploadResult result = new FileUploadResult();
        boolean flag = false;
        for (MultipartFile file : files) {
            // 校验后缀
            for (String type : FileUploadConstant.TYPE) {
                String oname = file.getOriginalFilename();
                // 如果后缀是要求的格式结尾，标志位设置为true，跳出循环
                if (StringUtils.endsWithIgnoreCase(oname, type)) {
                    flag = true;
                    break;
                }
            }
            // 如果后缀都不符合，直接返回，后面不用校验了
            if (!flag) {
                result.setMsg("请上传图片格式的文件！");
                return result;
            }
            // 重置标志位，开始校验是否为图片
            flag = false;
            // 图片内容校验
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image == null) {
                    result.setMsg(file.getOriginalFilename() + "，不是图片！请上传图片！");
                    return result;
                } else {
                    int height = image.getHeight();
                    int width = image.getWidth();
                    if (image.getHeight() > FileUploadConstant.MAXHEIGHT) {
                        result.setMsg(file.getOriginalFilename() + "的高度超过:" + FileUploadConstant.MAXWEIGHT + "的限制！");
                        return result;
                    }
                    if (image.getWidth() > FileUploadConstant.MAXWEIGHT) {
                        result.setMsg(file.getOriginalFilename() + "的宽度超过:" + FileUploadConstant.MAXWEIGHT + "的限制！");
                        return result;
                    }
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.setMsg("上传图片成功");
        result.setSuccess(flag);
        return result;
    }

    /**
     * TODO：上传图片到OSS
     *
     * @param uploadFileMap
     * @return
     */
    private Map<String, String> uploadFile(Map<String, MultipartFile> uploadFileMap) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        if (uploadFileMap != null && uploadFileMap.size() > 0) {
            for (Map.Entry<String, MultipartFile> map : uploadFileMap.entrySet()) {
                result.put(map.getKey(), "https://aliyun.oss.com/" + map.getValue().getOriginalFilename());
            }
        }
        return result;
    }
}