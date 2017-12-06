/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.domain.BusinessMarketFloor;

import java.util.List;

/**
 * 商场楼层管理DAO接口
 * @author xiangnick
 * @version 2017-12-03
 */
@MyBatisDao
public interface BusinessMarketFloorDao extends CrudDao<BusinessMarketFloor> {

    List<BusinessMarketFloor> getFloorListByMarketId(String marketId);

    int updateByCondition(BusinessMarketFloor businessMarketFloor);

    int deleteByMarketId(String marketId);
}