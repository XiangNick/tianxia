/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.pos.srv;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.pos.dal.dao.PosDao;
import com.thinkgem.jeesite.modules.shop.pos.dal.domain.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * pos机管理Service
 * @author xiangnick
 * @version 2017-12-24
 */
@Service
@Transactional(readOnly = true)
public class PosService extends CrudService<PosDao, Pos> {

	@Autowired
	private PosDao posDao;

	public Pos get(String id) {
		return super.get(id);
	}
	
	public List<Pos> findList(Pos pos) {
		return super.findList(pos);
	}
	
	public Page<Pos> findPage(Page<Pos> page, Pos pos) {
		return super.findPage(page, pos);
	}
	
	@Transactional(readOnly = false)
	public void save(Pos pos) {
		super.save(pos);
	}
	
	@Transactional(readOnly = false)
	public void delete(Pos pos) {
		super.delete(pos);
	}

    public List<Pos> findPostListByShopId(String shopId) {
    	return StringUtils.isBlank(shopId)?new ArrayList<Pos>():posDao.findListByShopId(shopId);
	}
}