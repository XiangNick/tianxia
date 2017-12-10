/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.branch.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.branch.dal.domain.ShopBranch;

/**
 * 门店管理DAO接口
 * @author xiangnick
 * @version 2017-12-10
 */
@MyBatisDao
public interface ShopBranchDao extends CrudDao<ShopBranch> {
	
}