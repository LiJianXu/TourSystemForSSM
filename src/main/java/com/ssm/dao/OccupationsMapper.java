package com.ssm.dao;

import com.ssm.entity.Occupations;
import java.util.List;

public interface OccupationsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Occupations record);

    Occupations selectByPrimaryKey(Integer id);

    List<Occupations> selectAll();

    int updateByPrimaryKey(Occupations record);
    
    /**
     * 添加职业信息
     * @param name
     * @return
     */
    Integer insertByName(String name);
}