/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.shop.srv;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.MD5Util;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.base.bean.FileUploadResult;
import com.thinkgem.jeesite.modules.base.constant.FileUploadConstant;
import com.thinkgem.jeesite.modules.shop.shop.bean.UploadFileRecord;
import com.thinkgem.jeesite.modules.shop.shop.dal.dao.ShopDao;
import com.thinkgem.jeesite.modules.shop.shop.dal.dao.ShopUserDao;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.ShopUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

/**
 * 商户管理Service
 * @author xiangnick
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ShopService extends CrudService<ShopDao, Shop> {

	private static final String NOURMAL_SHOP_ROLE = "2";

	@Autowired
	private ShopUserDao shopUserDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Shop get(String id) {
		return super.get(id);
	}
	
	public List<Shop> findList(Shop shop) {
		return super.findList(shop);
	}
	
	public Page<Shop> findPage(Page<Shop> page, Shop shop) {
		return super.findPage(page, shop);
	}
	
	@Transactional(readOnly = false)
	public void save(Shop shop) {
		super.save(shop);
	}
	
	@Transactional(readOnly = false)
	public void delete(Shop shop) {
		super.delete(shop);
	}

	@Transactional(readOnly = false)
	public FileUploadResult process(UploadFileRecord record,Shop shop, MultipartFile license, MultipartFile idCardFront, MultipartFile idCardContrary) {
		FileUploadResult result = new FileUploadResult();
		if (StringUtils.isNotBlank(license.getOriginalFilename())) {
			//传了证件图片
			result = validatePic(license);
			if (!result.isSuccess()) {
				return result;
			} else {
				//校验成功，上传证件图片
				String licenseUrl = uploadFile(license);
				//更新证件的url
				record.setLicense(licenseUrl);
			}
		}
		if (StringUtils.isNotBlank(idCardFront.getOriginalFilename())) {
			//传了身份证正面图片
			result = validatePic(idCardFront);
			if (!result.isSuccess()) {
				return result;
			} else {
				//校验成功，上传身份证正面图片
				String idCardFrontUrl = uploadFile(idCardFront);
				//更新身份证正面的url
				record.setIdCardFront(idCardFrontUrl);
			}
		}
		if (StringUtils.isNotBlank(idCardContrary.getOriginalFilename())) {
			//传了身份证反面图片
			result = validatePic(idCardContrary);
			if (!result.isSuccess()) {
				return result;
			} else {
				//校验成功，上传身份证反面图片
				String idCardContraryUrl = uploadFile(idCardContrary);
				//更新身份证反面的url
				record.setIdCardContrary(idCardContraryUrl);
			}
		}
		String idPhotos = JSON.toJSONString(record);
		shop.setIdPhotos(idPhotos);
		//新纪录才在shopUser表新增一条登录账户
		if(isNewRecord(shop)&&StringUtils.isNotBlank(shop.getPhone())){
			ShopUser shopUser = new ShopUser();
			shopUser.setId(IdGen.uuid());
			shopUser.setCreatetime(new Date());
			shopUser.setName(shop.getName());
			shopUser.setPhone(shop.getPhone());
			String salt = MD5Util.getRandomSalt(5);
			shopUser.setPassword(MD5Util.md5(MD5Util.DEFAULT_PASSWORD,salt ));
			shopUser.setSalt(salt);
			shopUser.setRoleid(NOURMAL_SHOP_ROLE);
			shopUser.setStatus(1);
			shopUser.setVersion(1);

			shopUserDao.addShopUser(shopUser);
		}
		save(shop);
		result.setSuccess(true);
		result.setMsg("保存商户成功");
		return result;
	}

	private boolean isNewRecord(Shop shop) {
		return StringUtils.isBlank(shop.getId());
	}

	private FileUploadResult validatePic(MultipartFile file) {
		FileUploadResult result = new FileUploadResult();
		boolean flag = false;
		// 校验后缀
		String oname = file.getOriginalFilename();
		for (String type : FileUploadConstant.TYPE) {
			// 如果后缀是要求的格式结尾，标志位设置为true，跳出循环
			if (StringUtils.endsWithIgnoreCase(oname, type)) {
				flag = true;
				break;
			}
		}
		// 如果后缀都不符合，直接返回，后面不用校验了
		if (!flag){
			result.setMsg("请上传图片格式的文件！");
			return result;
		}
		// 重置标志位，开始校验是否为图片
		flag = false;
		// 图片内容校验
		try{
			BufferedImage image = ImageIO.read(file.getInputStream());
			if (image == null) {
				result.setMsg(file.getOriginalFilename() + "，不是图片！请上传图片！");
				return result;
			} else {
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
		} catch (Exception e){
			e.printStackTrace();
		}
		result.setMsg("上传图片成功");
		result.setSuccess(flag);
		return result;
	}

	/**
	 * TODO：上传图片到OSS
	 * @param uploadFile
	 * @return
	 */
	private String uploadFile(MultipartFile uploadFile) {
		if(uploadFile!=null&&StringUtils.isNotBlank(uploadFile.getOriginalFilename())){
			return "https://aliyun.oss.com/" + uploadFile.getOriginalFilename();
		}
		return "";
	}
}