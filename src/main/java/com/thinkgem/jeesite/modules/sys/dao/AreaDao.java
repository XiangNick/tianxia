/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.List;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

    /** 获取第一条数据
     * @return 获取第一条数据
     */
    Area getFirst();

    /**
     * 根据区域类型获取区域数据列表
     * @param type
     * @return
     */
    List<Area> findByType(String type);

    /**
     * 省市区三级联动，根据父级id获取区域列表
     * @param parentId
     * @return
     */
    List<Area> findByLinkage(String parentId);
}
