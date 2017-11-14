package com.ssm.dao;

import com.ssm.entity.Collections;


import java.util.List;

public interface CollectionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collections record);

    Collections selectByPrimaryKey(Integer id);

    List<Collections> selectAll();

    int updateByPrimaryKey(Collections record);
    
    /**
     * 验证用户是否收藏过
     * @param record
     * @return
     */
    List<Collections> checkCollectionsByUserDataId(Collections record);
    
    /**
     * 通过用户的资料id 得到收藏的信息
     * @param userDataId
     * @return
     */
    List<Collections> getCollectionsByUserDataId(Integer userdateid);
}