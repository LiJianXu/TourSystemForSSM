package com.ssm.dao;

import com.ssm.entity.UsersData;
import java.util.List;

public interface UsersDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UsersData record);

    UsersData selectByPrimaryKey(Integer id);

    List<UsersData> selectAll();

    int updateByPrimaryKey(UsersData record);
    
    /**
     * 通过用户的Id 得到用户的信息
     * @param userid
     * @return
     */
    UsersData getUserDataByUserId(Integer userid);
    
    /**
     * 通过经验给用户排序
     * @return
     */
    List<UsersData> selectByExperienceDesc();
    
    /**
     * 通过用户的id 获取所有的文章数
     * @param usersid
     * @return
     */
    Integer selectArticlesAllByUsersId(Integer usersid);
    /**
     * 通过用户的id 获取所有的经验
     * @param usersid
     * @return
     */
    Integer selectExperienceByUsersId(Integer usersid);
    /**
     * 更新用户的资料
     * @param record
     * @return
     */
    Integer updateArticlesAllByUsersId(UsersData record);
    

}