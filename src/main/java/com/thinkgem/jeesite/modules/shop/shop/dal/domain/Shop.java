/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.shop.dal.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.base.categories.dal.domain.Categories;
import org.hibernate.validator.constraints.Length;

/**
 * 商户管理Entity
 * @author xiangnick
 * @version 2017-12-09
 */
public class Shop extends DataEntity<Shop> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商户名称
	private String contactName;		// 联系人姓名
	private String phone;		// 电话
	private Categories category;		// 所属行业
	private String idPhotos;		// 以json格式存储的证件照地址
	private String openTime;		// 营业时间
	
	public Shop() {
		super();
	}

	public Shop(String id){
		super(id);
	}

	@Length(min=1, max=200, message="商户名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=10, message="联系人姓名长度必须介于 1 和 10 之间")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@Length(min=1, max=50, message="电话长度必须介于 1 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	@Length(min=0, max=2000, message="以json格式存储的证件照地址长度必须介于 0 和 2000 之间")
	public String getIdPhotos() {
		return idPhotos;
	}

	public void setIdPhotos(String idPhotos) {
		this.idPhotos = idPhotos;
	}
	
	@Length(min=0, max=50, message="营业时间长度必须介于 0 和 50 之间")
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	
}