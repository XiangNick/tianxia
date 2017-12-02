/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarket.dal.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商场管理Entity
 * @author xiangnick
 * @version 2017-12-02
 */
public class BusinessMarket extends DataEntity<BusinessMarket> {
	
	private static final long serialVersionUID = 1L;
	private String circleId;		// 商圈id
	private String circleName;   // 商圈名称
	private String name;		// 商场名称
	private String description;		// 商场介绍
	private String location;		// 省市区
	private String locationDetail;		// 详细地址
	private String openTime;		// 营业时间
	private String phone;		// 电话
	
	public BusinessMarket() {
		super();
	}

	public BusinessMarket(String id){
		super(id);
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	@Length(min=0, max=64, message="商圈id长度必须介于 0 和 64 之间")
	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	
	@Length(min=1, max=200, message="商场名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=500, message="商场介绍长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=50, message="省市区长度必须介于 0 和 50 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Length(min=0, max=100, message="详细地址长度必须介于 0 和 100 之间")
	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	
	@Length(min=1, max=50, message="营业时间长度必须介于 1 和 50 之间")
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	
	@Length(min=1, max=50, message="电话长度必须介于 1 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}