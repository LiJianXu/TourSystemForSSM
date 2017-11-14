package com.ssm.dao;

import com.ssm.entity.Assess;
import java.util.List;

public interface AssessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assess record);

    Assess selectByPrimaryKey(Integer id);

    List<Assess> selectAll();

    int updateByPrimaryKey(Assess record);
    
    /**
     * 验证用户是否评价过
     * @param record
     * @return
     */
    List<Assess> checkAssessUserDataId(Assess record);
}