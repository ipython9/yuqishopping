package com.yuqi.recommend.service;

import com.yuqi.pojo.Recommend;
import entity.PageResult;

/**
 * @Author:
 * @Description:
 * @Date: Create in 17:25 2019/5/6
 */
public interface ItemService {
    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(Recommend recommend, int pageNum, int pageSize);

}
