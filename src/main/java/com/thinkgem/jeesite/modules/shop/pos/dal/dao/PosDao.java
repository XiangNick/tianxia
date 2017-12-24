/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.pos.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.pos.dal.domain.Pos;

import java.util.List;

/**
 * pos机管理DAO接口
 * @author xiangnick
 * @version 2017-12-24
 */
@MyBatisDao
public interface PosDao extends CrudDao<Pos> {

    List<Pos> findListByShopId(String shopId);
}