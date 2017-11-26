/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.categories.dal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.base.categories.dal.domain.Categories;

/**
 * 行业分类DAO接口
 * @author xiangnick
 * @version 2017-11-25
 */
@MyBatisDao
public interface CategoriesDao extends CrudDao<Categories> {
	
}