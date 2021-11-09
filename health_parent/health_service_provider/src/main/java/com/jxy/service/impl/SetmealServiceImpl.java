package com.jxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.constant.MessageConstant;
import com.constant.RedisConstant;
import com.entity.PageResult;
import com.entity.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jxy.dao.SetmealDao;
import com.jxy.service.SetmealService;
import com.pojo.CheckGroup;
import com.pojo.Setmeal;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;
import sun.security.mscapi.CPublicKey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 体检套餐服务
* */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath; //从属性文件中读取要生成的html的目录

    //新增套餐信息，同时关联检查组
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(),checkGroupIds);
        //将图片名称保存到redis集合中
        String setmealImg = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmealImg);

        //当添加套餐后需要生成静态页面（套餐列表页面，套餐详情页面）
        generateMobileStaticHtml();
    }
    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list){
        Map map = new HashMap<String,Object>();
        //为模板提供数据，用于生成静态页面
        map.put("setmealList",list);
        generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }
    //生成套餐详情静态页面（多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for(Setmeal setmeal:list){
            Map map = new HashMap<String,Object>();
            //为模板提供数据，用于生成静态页面
            map.put("setmeal",setmealDao.findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }
    }
    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml(){
        //生成静态页面之前需要查询数据
        List<Setmeal> list = setmealDao.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(list);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(list);
    }
    //通用的方法，用于生成静态页面
    public void generateHtml(String templateName,String htmlPageName,Map<String,Object> map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            //构造输出流
            out = new FileWriter(new File(outPutPath + "/" + htmlPageName));
            //输出文件
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentSize = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        //使用分页助手
        PageHelper.startPage(currentSize,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    //查询套餐预约占比数据
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup
    public void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length>0){
            for (Integer checkgroupId:checkgroupIds){
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
