package com.yuqi.sellergoods.service;

import com.yuqi.pojo.TbOrder;
import entity.PageResult;
import entity.Result;
import group.Order;

import java.util.List;

/**
* 服务层接口
* @author Administrator
*
*/
public interface OrderService {

/**
* 返回全部列表
* @return
*/
public List<Order> findAll(String name);


/**
* 返回分页列表
* @return
*/
public PageResult findPage(int pageNum, int pageSize, String name);


/**
* 分页
* @param pageNum 当前页 码
* @param pageSize 每页记录数
* @return
*/
public PageResult findPage(TbOrder order, int pageNum, int pageSize);



    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public TbOrder findOne(Long id);





    //    更新订单状态
    public void updateStatus(Long id,String status);
}

