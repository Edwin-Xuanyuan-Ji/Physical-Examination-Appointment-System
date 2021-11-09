package com.jxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.entity.PageResult;
import com.entity.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jxy.dao.CheckGroupDao;
import com.jxy.service.CheckGroupService;
import com.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 检查组服务
* */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;
    //新增检查组，同时需要让检查组关联检查项
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }
    //分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentSize = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        //使用分页助手
        PageHelper.startPage(currentSize,pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //根据ID查询检查组
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }
    //根据检查组ID查询关联的检查项ID
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }
    //编辑检查组信息，同时关联检查项
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息,操作检查组t_checkgroup表
        checkGroupDao.edit(checkGroup);
        //清理当前检查组关联的检查项,操作中间关系表
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //重新建立当前检查组和检查项的关联关系
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    public void deleteById(Integer id) {
        checkGroupDao.deleteAssociation(id);
        checkGroupDao.deleteById(id);
    }


    //建立检查组和检查项多对多关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for(Integer checkitemid : checkitemIds){
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("checkgroupid",checkGroupId);
                map.put("checkitemid",checkitemid);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
