/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessMarket.srv;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.business.businessMarket.dal.dao.BusinessMarketDao;
import com.thinkgem.jeesite.modules.business.businessMarket.dal.domain.BusinessMarket;
import com.thinkgem.jeesite.modules.business.businessMarketFloor.srv.BusinessMarketFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商场管理Service
 * @author xiangnick
 * @version 2017-12-02
 */
@Service
@Transactional(readOnly = true)
public class BusinessMarketService extends CrudService<BusinessMarketDao, BusinessMarket> {

	@Autowired
	private BusinessMarketFloorService businessMarketFloorService;

	@Autowired
	private BusinessMarketDao businessMarketDao;

	public BusinessMarket get(String id) {
		BusinessMarket businessMarket = super.get(id);
		return businessMarket;
	}
	
	public List<BusinessMarket> findList(BusinessMarket businessMarket) {
		return super.findList(businessMarket);
	}
	
	public Page<BusinessMarket> findPage(Page<BusinessMarket> page, BusinessMarket businessMarket) {
		return super.findPage(page, businessMarket);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessMarket businessMarket) {
		super.save(businessMarket);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessMarket businessMarket) {
		String marketId = businessMarket.getId();
		//先逻辑删除子级楼层
		businessMarketFloorService.deleteByMarketId(marketId);
		super.delete(businessMarket);
	}

	@Transactional(readOnly = false)
	public List<BusinessMarket> getMarketByCircleId(String circleId) {
		return businessMarketDao.getMarketByCircleId(circleId);
	}
}