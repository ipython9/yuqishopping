package com.yuqi.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuqi.pojo.TbOrder;
import com.yuqi.search.service.TbOrderService;
import entity.PageResult;
import entity.Result;
import group.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private TbOrderService tbOrderService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<Order> findAll() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return tbOrderService.findAll(name);
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows,@RequestParam("name") String name) {
        return tbOrderService.findPage(page, rows,name);
    }



    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbOrder findOne(Long id){
        return tbOrderService.findOne(id);
    }



    /**
     * 查询+分页
     * @param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbOrder order, int page, int rows  ){
        return tbOrderService.findPage(order, page, rows);
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(Long id, String status){
        try {
            tbOrderService.updateStatus(id,status);
            if(status.equals("3"))
            {
                return new Result(true, "确认收获成功");
            }else{
                return new Result(true, "取消成功请等待商家确认");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "确认收获成功");
        }
    }

    @RequestMapping("/updateEastimate")
    public Result updateEastimate(Long id, String est){
        try {
            tbOrderService.updateEastimate(id,est);

            return new Result(true, "评价成功");


        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "确认收获成功");
        }
    }

}
