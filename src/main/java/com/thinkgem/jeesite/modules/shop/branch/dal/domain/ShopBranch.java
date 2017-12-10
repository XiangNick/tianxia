/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.branch.dal.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.business.businessCircle.dal.domain.BusinessCircle;
import com.thinkgem.jeesite.modules.business.businessMarket.dal.domain.BusinessMarket;
import com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.domain.BusinessMarketFloor;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;
import org.hibernate.validator.constraints.Length;

/**
 * 门店管理Entity
 * @author xiangnick
 * @version 2017-12-10
 */
public class ShopBranch extends DataEntity<ShopBranch> {
	
	private static final long serialVersionUID = 1L;
	private Shop shop;		// 商户id
	private BusinessCircle circle;		// 商圈id
	private BusinessMarket market;		// 商场id
	private BusinessMarketFloor marketFloor;		// 商场楼层id
	private String side;		// 周边：0=商场内 1=商场周边 2=无商场
	private String name;		// 门店名称
	private String locationDetail;		// 详细地址
	private String contactName;		// 联系人姓名
	private String phone;		// 电话
	
	public ShopBranch() {
		super();
	}

	public ShopBranch(String id){
		super(id);
	}


	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public BusinessCircle getCircle() {
		return circle;
	}

	public void setCircle(BusinessCircle circle) {
		this.circle = circle;
	}

	public BusinessMarket getMarket() {
		return market;
	}

	public void setMarket(BusinessMarket market) {
		this.market = market;
	}

	public BusinessMarketFloor getMarketFloor() {
		return marketFloor;
	}

	public void setMarketFloor(BusinessMarketFloor marketFloor) {
		this.marketFloor = marketFloor;
	}

	@Length(min=1, max=2, message="周边：0=商场内 1=商场周边 2=无商场长度必须介于 1 和 2 之间")
	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}
	
	@Length(min=1, max=50, message="门店名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="详细地址长度必须介于 0 和 100 之间")
	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	
	@Length(min=0, max=10, message="联系人姓名长度必须介于 0 和 10 之间")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}