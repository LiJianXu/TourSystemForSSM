package com.ssm.dao;

import com.ssm.entity.Users;
import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    Users selectByPrimaryKey(Integer id);

    List<Users> selectAll();

    int updateByPrimaryKey(Users record);
    
    /**
     * 通过用户的手机号登录
     * @param users
     * @return
     */
    Integer selectByPhone(Users users);
    
    /**
     * 通过用户的邮箱登录
     * @param users
     * @return
     */
    Integer selectByEmail(Users users);
    
    /**
     * 验证手机是否注册过
     * @param phone
     * @return
     */
    Integer checkedPhone(String phone);
    
    /**
     * 验证邮箱是否注册过
     * @param email
     * @return
     */
    Integer checkedEmail(String email);
    
    /**
     * 管理员登录
     * @param user
     * @return
     */
    Integer adminLogin(Users user);
}