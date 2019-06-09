package com.yuqi.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuqi.pojo.TbItemCat;
import com.yuqi.sellergoods.service.ItemCatService;
import group.FirstItemCatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 21:34 2019/5/3
 */

@RestController
@RequestMapping("/ItemCat")
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;
    @RequestMapping("/findItemCat")
   public  List<TbItemCat> getMenu() {
       return itemCatService.getMenu();
    }
}
