/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.shop.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.Shop;

/**
 * 商户管理DAO接口
 * @author xiangnick
 * @version 2017-12-09
 */
@MyBatisDao
public interface ShopDao extends CrudDao<Shop> {
	
}