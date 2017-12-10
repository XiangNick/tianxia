/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarket.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.business.businessMarket.dal.domain.BusinessMarket;

import java.util.List;

/**
 * 商场管理DAO接口
 * @author xiangnick
 * @version 2017-12-02
 */
@MyBatisDao
public interface BusinessMarketDao extends CrudDao<BusinessMarket> {

    List<BusinessMarket> getMarketByCircleId(String circleId);
}