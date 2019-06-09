package com.yuqi.recommend.service;

import com.yuqi.pojo.ItemModel;
import com.yuqi.pojo.Recommend;
import entity.PageResult;
import org.apache.mahout.cf.taste.common.TasteException;

import java.io.IOException;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 19:48 2019/4/7
 */
public interface RecommendService {
    //创建推荐库
    void createRecommendDataBase() throws IOException,TasteException;
    //删除推荐库
    void deleteRecommendDataBase();
    //提供推荐内容
    List<Recommend> findAll(String name);
    //新用户冷启动
    List<Recommend> createFindAll();

    //创建基于物品推荐推荐库
    void createItemDataBase() throws IOException,TasteException;
    //删除基于物品推荐库
    void deleteitemDataBase();
    //提供基于物品推荐内容
    List<Recommend> findItemAll(String name);

    /**
     * 返回全部列表
     * @return
     */
    public List<Recommend> findAll();


    /**
     * 返回分页列表
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(Recommend recommend);


    /**
     * 修改
     */
    public void update(Recommend recommend);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public Recommend findOne(Long id);


    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(Recommend recommend, int pageNum, int pageSize);

    public List<Recommend> findListByUserId(Long userId);



    public void add(ItemModel itemModel) throws IOException, TasteException;

    public List<ItemModel> findAllItemModel();
}
