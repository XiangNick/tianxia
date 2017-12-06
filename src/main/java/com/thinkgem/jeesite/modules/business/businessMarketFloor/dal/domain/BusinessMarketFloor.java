/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 商场楼层管理Entity
 * @author xiangnick
 * @version 2017-12-03
 */
public class BusinessMarketFloor extends DataEntity<BusinessMarketFloor> {
	
	private static final long serialVersionUID = 1L;
	private String marketId;		// 商场id
	private Integer floor;		// 楼层号
	private String name;		// 楼层名
	private String category;		// 该楼层类别
	
	public BusinessMarketFloor() {
		super();
	}

	public BusinessMarketFloor(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商场id长度必须介于 1 和 64 之间")
	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	
	@NotNull(message="楼层号不能为空")
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
	@Length(min=0, max=50, message="楼层名长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="该楼层类别长度必须介于 0 和 50 之间")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}