package com.yuqi.sellergoods.service;
import com.yuqi.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {
        /**
         * 返回全部列表
         * @return
         */

        public List<TbBrand> findAll();

        /**
         * 品牌分页
         * @param pageNum 当前页面
         * @param pageSize 每页记录数
         * @return
         */
        public PageResult findPage(int pageNum,int pageSize);

        /**
         * 增加
         * @param brand
         */
        public void add(TbBrand brand);

        /**
         * 根据ID查询实体
         * @param id
         * @return
         */
        public TbBrand findOne(Long id);

        /**
         * 修改
         * @param brand
         */
        public void update(TbBrand brand);


        /**
         * 删除
         * @param ids
         */
        public void delete(Long[] ids);


        /**
         * 品牌分页
         * @param pageNum 当前页面
         * @param pageSize 每页记录数
         * @return
         */
        public PageResult findPage(TbBrand brand, int pageNum,int pageSize);

        public List<Map> selectOptionList();

}
