package com.thinkgem.jeesite.modules.shop.shop.dal.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.shop.dal.domain.ShopUser;

@MyBatisDao
public interface ShopUserDao {
    int addShopUser(ShopUser shopUser);
}
