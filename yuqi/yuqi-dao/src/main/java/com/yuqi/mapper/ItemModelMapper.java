package com.yuqi.mapper;

import com.yuqi.pojo.ItemModel;
import com.yuqi.pojo.ItemModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemModelMapper {
    int countByExample(ItemModelExample example);

    int deleteByExample(ItemModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ItemModel record);

    int insertSelective(ItemModel record);

    List<ItemModel> selectByExample(ItemModelExample example);

    ItemModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ItemModel record, @Param("example") ItemModelExample example);

    int updateByExample(@Param("record") ItemModel record, @Param("example") ItemModelExample example);

    int updateByPrimaryKeySelective(ItemModel record);

    int updateByPrimaryKey(ItemModel record);
}