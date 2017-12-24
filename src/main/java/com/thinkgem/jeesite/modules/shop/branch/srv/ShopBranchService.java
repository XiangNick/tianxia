/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.branch.srv;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.branch.dal.dao.ShopBranchDao;
import com.thinkgem.jeesite.modules.shop.branch.dal.domain.ShopBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 门店管理Service
 * @author xiangnick
 * @version 2017-12-10
 */
@Service
@Transactional(readOnly = true)
public class ShopBranchService extends CrudService<ShopBranchDao, ShopBranch> {

	@Autowired
	private ShopBranchDao shopBranchDao;

	public ShopBranch get(String id) {
		return super.get(id);
	}
	
	public List<ShopBranch> findList(ShopBranch shopBranch) {
		return super.findList(shopBranch);
	}
	
	public Page<ShopBranch> findPage(Page<ShopBranch> page, ShopBranch shopBranch) {
		return super.findPage(page, shopBranch);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopBranch shopBranch) {
		super.save(shopBranch);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopBranch shopBranch) {
		super.delete(shopBranch);
	}

	public List<ShopBranch> getListByShopId(String shopId) {
		return shopBranchDao.getListByShopId(shopId);
	}
}