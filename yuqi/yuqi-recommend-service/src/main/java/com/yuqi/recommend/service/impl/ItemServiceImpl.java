package com.yuqi.recommend.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuqi.mapper.RecommendMapper;
import com.yuqi.pojo.Recommend;
import com.yuqi.recommend.service.ItemService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author:
 * @Description:
 * @Date: Create in 17:27 2019/5/6
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private RecommendMapper recommendMapper;
    @Override
    public PageResult findPage(Recommend recommend, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

//        RecommendExample example=new RecommendExample();
//        RecommendExample.Criteria criteria = example.createCriteria();
//        criteria.andIdIsNotNull();
//        if(recommend!=null){
//            if(recommend.getUserid()!=null){
//                criteria.andUseridEqualTo(recommend.getUserid());
//            }
//
//        }

        Page<Recommend> page= (Page<Recommend>)recommendMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
