package com.ssm.dao;

import com.ssm.entity.Turns;
import java.util.List;

public interface TurnsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Turns record);

    Turns selectByPrimaryKey(Integer id);

    List<Turns> selectAll();

    int updateByPrimaryKey(Turns record);
    
    /**
     * 验证用户是否转载过
     * @param record
     * @return
     */
    List<Turns> checkTurnsByUserDataId(Turns record);
}