package com.yuqi.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuqi.mapper.TbOrderItemMapper;
import com.yuqi.mapper.TbOrderMapper;
import com.yuqi.mapper.TbSellerMapper;
import com.yuqi.pojo.*;
import com.yuqi.search.service.TbOrderService;
import entity.PageResult;
import group.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbOrderServiceImpl implements TbOrderService {


    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbSellerMapper tbSellerMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;



    public List<Order> orderList(String name){

        TbOrderItem tbOrderItem =new TbOrderItem();


        //根据用户姓名查询订单表 获取所有的订单信息
        TbOrderExample tbOrderExample =new TbOrderExample();
        TbOrderExample.Criteria criteria= tbOrderExample.createCriteria();
        criteria.andUserIdEqualTo(name);
        System.out.println(name);
        List<TbOrder> tbOrderList= orderMapper.selectByExample(tbOrderExample);

        //遍历订单表 得到订单的详细信息;
        List<Order> orderList = new ArrayList<Order>();
        for (TbOrder t:tbOrderList) {
            Order order = new Order();
            order.setOrderId(t.getOrderId().toString());
            order.setCreateTime(t.getCreateTime().toString());
            order.setStatus(t.getStatus());

            //通过tborderitem  进行设置值

            TbOrderItemExample tbOrderItemExample =new TbOrderItemExample();
            TbOrderItemExample.Criteria criteria1 = tbOrderItemExample .createCriteria();
            criteria1.andOrderIdEqualTo(t.getOrderId());
            System.out.println(t.getOrderId());
            List<TbOrderItem> tbOrderItemList = new  ArrayList<TbOrderItem>();
            tbOrderItemList = tbOrderItemMapper.selectByExample(tbOrderItemExample);

            for (TbOrderItem tb:tbOrderItemList) {

                order.setPrice(tb.getPrice());
                order.setPicPath(tb.getPicPath());
                order.setNum(tb.getNum());
                order.setTitle(tb.getTitle());
                order.setGoodsId(tb.getGoodsId().toString());
                if(tb.getSellerId()!=null)
                {
                    TbSellerExample tbSellerExample = new TbSellerExample();
                    TbSellerExample.Criteria criteria2 = tbSellerExample.createCriteria();
                    criteria2.andSellerIdEqualTo(tb.getSellerId());
                    List<TbSeller> tbSellerList = tbSellerMapper.selectByExample(tbSellerExample);
                    for(TbSeller tbSeller:tbSellerList){
                        order.setSellerName(tbSeller.getNickName());
                    }

                }
            }
            orderList.add(order);

        }



        return orderList;
    }
    /**
     * 查询全部
     */
    @Override
    public List<Order> findAll(String name) {
        return orderList(name);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize,String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderList(name);

        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList,5);
        orderPageInfo.setPageNum(pageNum);
        orderPageInfo.setPageSize(pageSize);
        orderPageInfo.setList(orderList);
        orderPageInfo.setTotal(orderList.size());
        return new PageResult(orderPageInfo.getTotal(), orderPageInfo.getList());
    }























    @Override
    public PageResult findPage(TbOrder order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbOrderExample example=new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();

        if(order!=null){
            if(order.getPaymentType()!=null && order.getPaymentType().length()>0){
                criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
            }
            if(order.getPostFee()!=null && order.getPostFee().length()>0){
                criteria.andPostFeeLike("%"+order.getPostFee()+"%");
            }
            if(order.getStatus()!=null && order.getStatus().length()>0){
                criteria.andStatusLike("%"+order.getStatus()+"%");
            }
            if(order.getShippingName()!=null && order.getShippingName().length()>0){
                criteria.andShippingNameLike("%"+order.getShippingName()+"%");
            }
            if(order.getShippingCode()!=null && order.getShippingCode().length()>0){
                criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
            }
            if(order.getUserId()!=null && order.getUserId().length()>0){
                criteria.andUserIdLike("%"+order.getUserId()+"%");
            }
            if(order.getBuyerMessage()!=null && order.getBuyerMessage().length()>0){
                criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
            }
            if(order.getBuyerNick()!=null && order.getBuyerNick().length()>0){
                criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
            }
            if(order.getBuyerRate()!=null && order.getBuyerRate().length()>0){
                criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
            }
            if(order.getReceiverAreaName()!=null && order.getReceiverAreaName().length()>0){
                criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
            }
            if(order.getReceiverMobile()!=null && order.getReceiverMobile().length()>0){
                criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
            }
            if(order.getReceiverZipCode()!=null && order.getReceiverZipCode().length()>0){
                criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
            }
            if(order.getReceiver()!=null && order.getReceiver().length()>0){
                criteria.andReceiverLike("%"+order.getReceiver()+"%");
            }
            if(order.getInvoiceType()!=null && order.getInvoiceType().length()>0){
                criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
            }
            if(order.getSourceType()!=null && order.getSourceType().length()>0){
                criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
            }
            if(order.getSellerId()!=null && order.getSellerId().length()>0){
                criteria.andSellerIdLike("%"+order.getSellerId()+"%");
            }

        }

        Page<TbOrder> page= (Page<TbOrder>)orderMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }



    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public TbOrder findOne(Long id){
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateStatus(Long id, String status) {
        TbOrder tbOrder=orderMapper.selectByPrimaryKey(id);
        tbOrder.setStatus(status);
        orderMapper.updateByPrimaryKey(tbOrder);
    }

    @Override
    public void updateEastimate(Long id, String est) {
        TbOrder tbOrder=orderMapper.selectByPrimaryKey(id);
        tbOrder.setStatus("5");
        tbOrder.setPostFee(est);
        tbOrder.setBuyerMessage(est);
        orderMapper.updateByPrimaryKey(tbOrder);
    }
}
