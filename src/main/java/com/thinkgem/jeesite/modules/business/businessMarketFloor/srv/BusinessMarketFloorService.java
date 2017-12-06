/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarketFloor.srv;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.dao.BusinessMarketFloorDao;
import com.thinkgem.jeesite.modules.business.businessMarketFloor.dal.domain.BusinessMarketFloor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商场楼层管理Service
 * @author xiangnick
 * @version 2017-12-03
 */
@Service
@Transactional(readOnly = true)
public class BusinessMarketFloorService extends CrudService<BusinessMarketFloorDao, BusinessMarketFloor> {

	@Autowired
	private BusinessMarketFloorDao businessMarketFloorDao;
	public BusinessMarketFloor get(String id) {
		return super.get(id);
	}
	
	public List<BusinessMarketFloor> findList(BusinessMarketFloor businessMarketFloor) {
		return super.findList(businessMarketFloor);
	}
	
	public Page<BusinessMarketFloor> findPage(Page<BusinessMarketFloor> page, BusinessMarketFloor businessMarketFloor) {
		return super.findPage(page, businessMarketFloor);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessMarketFloor businessMarketFloor) {
		super.save(businessMarketFloor);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessMarketFloor businessMarketFloor) {
		super.delete(businessMarketFloor);
	}

	public List<BusinessMarketFloor> getFloorListByMarketId(String marketId) {
		return 	businessMarketFloorDao.getFloorListByMarketId(marketId);
	}

	@Transactional(readOnly = false)
	public void updateFloorByCondition(BusinessMarketFloor businessMarketFloor) {
		businessMarketFloorDao.updateByCondition(businessMarketFloor);
	}

	public void deleteByMarketId(String marketId) {
		businessMarketFloorDao.deleteByMarketId(marketId);
	}
}