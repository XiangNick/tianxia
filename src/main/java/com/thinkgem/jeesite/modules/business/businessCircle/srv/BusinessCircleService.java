/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessCircle.srv;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.business.businessCircle.dal.dao.BusinessCircleDao;
import com.thinkgem.jeesite.modules.business.businessCircle.dal.domain.BusinessCircle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商圈管理Service
 * @author xiangnick
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class BusinessCircleService extends CrudService<BusinessCircleDao, BusinessCircle> {

	public BusinessCircle get(String id) {
		return super.get(id);
	}
	
	public List<BusinessCircle> findList(BusinessCircle businessCircle) {
		return super.findList(businessCircle);
	}
	
	public Page<BusinessCircle> findPage(Page<BusinessCircle> page, BusinessCircle businessCircle) {
		return super.findPage(page, businessCircle);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessCircle businessCircle) {
		super.save(businessCircle);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessCircle businessCircle) {
		super.delete(businessCircle);
	}
	
}