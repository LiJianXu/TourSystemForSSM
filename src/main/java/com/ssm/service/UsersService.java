package com.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.UsersMapper;
import com.ssm.entity.Users;

@Service
public class UsersService {
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UsersDataService usersDataService;
	
	/**
	 * 通过用户的电话号码登录
	 * @param users
	 * @return
	 */
	public Integer loginByPhone(Users users){
		try {
			Integer i=usersMapper.selectByPhone(users);
			if(i>0){
				return i;
			}else{
				return 0;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 通过用户的邮箱登录
	 * @param users
	 * @return
	 */
	public Integer loginByEmail(Users users){
		try {
			Integer i=usersMapper.selectByEmail(users);
			if(i>0){
				return i;
			}else{
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 验证手机是否注册过
	 * @param phone
	 * @return
	 */
	public Boolean checkPhone(String phone){
		try {
			Integer i=usersMapper.checkedPhone(phone);
			if(i>0){
				return true;
			}else{
				return false;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 验证邮箱是否注册过
	 * @param email
	 * @return
	 */
	public Boolean checkEmail(String email){
		try {
			Integer i=usersMapper.checkedEmail(email);
			if(i>0){
				return true;
			}else{
				return false;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 插入用户
	 * @param users
	 * @return
	 */
	public Integer insertUser(Users users){
		try {
			Integer i=usersMapper.insert(users);
			Integer userid=users.getId();
			if(userid>0){
				//通过用户的Id 创建用户初始化的基本信息
				if(usersDataService.insertUserData(userid)){
					return userid;
				}else{
					return 0;
				}
			}else{
				return 0;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 管理员登录
	 * @param users
	 * @return
	 */
	public boolean adminLogin(Users users){
		try {
			if(usersMapper.adminLogin(users)>0){
				return true;
			}else{
				return false;
			}		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public Users getUserById(Integer userId){
		Users users = null;
		users = usersMapper.selectByPrimaryKey(userId);
		if (users != null){
			return users;
		}
		return null;
	}
	
	public boolean updateUser(Users users){
		if(usersMapper.updateByPrimaryKey(users)>0){
			return true;
		}else{
			return false;
		}
		
	}
}
