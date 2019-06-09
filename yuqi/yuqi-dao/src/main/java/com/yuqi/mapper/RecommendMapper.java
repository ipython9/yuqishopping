package com.yuqi.mapper;

import com.yuqi.pojo.Recommend;
import com.yuqi.pojo.RecommendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecommendMapper {
    int countByExample(RecommendExample example);

    int deleteByExample(RecommendExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    List<Recommend> selectByExample(RecommendExample example);

    Recommend selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Recommend record, @Param("example") RecommendExample example);

    int updateByExample(@Param("record") Recommend record, @Param("example") RecommendExample example);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);
}