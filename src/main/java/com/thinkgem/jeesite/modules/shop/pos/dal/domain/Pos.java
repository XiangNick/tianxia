/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.pos.dal.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.shop.branch.dal.domain.ShopBranch;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;
import org.hibernate.validator.constraints.Length;

/**
 * pos机管理Entity
 * @author xiangnick
 * @version 2017-12-24
 */
public class Pos extends DataEntity<Pos> {
	
	private static final long serialVersionUID = 1L;
	private String posNo;		// pos终端号
	private Shop shop;		// 商户
	private ShopBranch branch;		// 门店
	
	public Pos() {
		super();
	}

	public Pos(String id){
		super(id);
	}

	@Length(min=1, max=200, message="pos终端号长度必须介于 1 和 200 之间")
	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public ShopBranch getBranch() {
		return branch;
	}

	public void setBranch(ShopBranch branch) {
		this.branch = branch;
	}
}