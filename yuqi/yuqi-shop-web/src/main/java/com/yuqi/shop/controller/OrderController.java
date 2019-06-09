package com.yuqi.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuqi.pojo.TbOrder;
import com.yuqi.sellergoods.service.OrderService;
import entity.PageResult;
import entity.Result;
import group.Goods;
import group.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<Order> findAll() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return orderService.findAll(name);

    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows,@RequestParam("name") String name) {
        String name1 = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name1);
        return orderService.findPage(page, rows,name1);
    }



    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbOrder findOne(Long id){
        return orderService.findOne(id);
    }



    /**
     * 查询+分页
     * @param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbOrder order, int page, int rows){
        return orderService.findPage(order, page, rows);
    }
        @RequestMapping("/updateStatus")
        public Result updateStatus(Long id,String status){
        try {
            orderService.updateStatus(id,status);
            if(status.equals("2"))
            return new Result(true, "发货成功");
            else{
                return new Result(true, "取消成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "发货失败");
        }
    }
}
