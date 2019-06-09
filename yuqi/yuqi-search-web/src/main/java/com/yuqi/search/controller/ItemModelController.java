package com.yuqi.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.yuqi.pojo.ItemModel;

import com.yuqi.pojo.Recommend;
import com.yuqi.pojo.TbUser;
import com.yuqi.recommend.service.RecommendService;
import com.yuqi.search.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 23:24 2019/5/5
 */
@RestController
@RequestMapping("/itemModel")
public class ItemModelController {


    @Reference
    private RecommendService recommendService;

    @Reference
    private UserService userService;
    /**
     * 增加
     * @param
     * @return
     */
    @RequestMapping("/add")
    public String  add(@RequestParam("goodsid") Long goodsid){
        ItemModel itemModel =new ItemModel();
        itemModel.setGoodsid(goodsid);
        //获取当前登录人账号
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TbUser user =userService.findUserByUserid(username);
        itemModel.setUid(user.getId());
        itemModel.setGoodsid(goodsid);
        itemModel.setPf(1);
        recommendService.add(itemModel);
        return "true";
    }

    @RequestMapping("/findContent")
    public List<Recommend> findContent(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return  recommendService.findItemAll(name);
    }
}
