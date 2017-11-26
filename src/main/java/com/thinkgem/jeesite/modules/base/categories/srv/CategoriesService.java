/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.categories.srv;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.base.categories.dal.dao.CategoriesDao;
import com.thinkgem.jeesite.modules.base.categories.dal.domain.Categories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 行业分类Service
 * @author xiangnick
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class CategoriesService extends TreeService<CategoriesDao, Categories> {

	public Categories get(String id) {
		return super.get(id);
	}
	
	public List<Categories> findList(Categories categories) {
		if (StringUtils.isNotBlank(categories.getParentIds())){
			categories.setParentIds(","+categories.getParentIds()+",");
		}
		return super.findList(categories);
	}
	
	@Transactional(readOnly = false)
	public void save(Categories categories) {
		super.save(categories);
	}
	
	@Transactional(readOnly = false)
	public void delete(Categories categories) {
		super.delete(categories);
	}
	
}