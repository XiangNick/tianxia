/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.businessCircle.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.business.businessCircle.dal.domain.BusinessCircle;

/**
 * 商圈管理DAO接口
 * @author xiangnick
 * @version 2017-11-30
 */
@MyBatisDao
public interface BusinessCircleDao extends CrudDao<BusinessCircle> {
	
}