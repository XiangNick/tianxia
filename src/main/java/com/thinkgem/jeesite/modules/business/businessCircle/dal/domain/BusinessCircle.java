/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessCircle.dal.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商圈管理Entity
 * @author xiangnick
 * @version 2017-11-30
 */
public class BusinessCircle extends DataEntity<BusinessCircle> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商圈名称
	private String description;		// 商圈介绍
	private String province;		// 商圈所属省id
	private String city;		// 商圈所属市id
	private String region;		// 商圈所属地区id
	private String detailLocation;		// 详细地址
	private String location;		// 存储省市区名称的冗余字段
	
	public BusinessCircle() {
		super();
	}

	public BusinessCircle(String id){
		super(id);
	}

	@Length(min=1, max=200, message="商圈名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=1, max=64, message="商圈所属省id长度必须介于 1 和 64 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=1, max=64, message="商圈所属市id长度必须介于 1 和 64 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=1, max=64, message="商圈所属地区id长度必须介于 1 和 64 之间")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@Length(min=0, max=200, message="详细地址长度必须介于 0 和 200 之间")
	public String getDetailLocation() {
		return detailLocation;
	}

	public void setDetailLocation(String detailLocation) {
		this.detailLocation = detailLocation;
	}
	
	@Length(min=0, max=64, message="存储省市区名称的冗余字段长度必须介于 0 和 64 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}