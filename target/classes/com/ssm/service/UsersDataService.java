package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.UsersDataMapper;
import com.ssm.entity.UsersData;

@Service
public class UsersDataService {
	
	@Autowired
	private UsersDataMapper usersDataMapper;
	
	@Autowired
	private OccupationsService occupationsService;
	
	/**
	 * 通过用户的Id 得到用户的信息
	 * @param userid
	 * @return
	 */
	public UsersData getDataByUserId(Integer userid){
		UsersData usersData=null;
		try {
			usersData=usersDataMapper.getUserDataByUserId(userid);
			usersData.setOccupations(occupationsService.getOccupationsById(usersData.getOccupationid()));
			return usersData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通过UsersId插入详细信息
	 * @param usersid
	 * @return
	 */
	public boolean insertUserData(Integer usersid){
		UsersData usersData=new UsersData();
		usersData.setId(0);
		usersData.setUserimage("header.jpg");
		usersData.setUsersid(usersid);
		usersData.setName("昵称都没有");
		usersData.setOccupationid(1);
		usersData.setIntroduction("这个人很懒什么都没有留下");
		usersData.setArticlesall(0);
		usersData.setAcommentsall(0);
		usersData.setAllturns(0);
		usersData.setExperience(0);
		usersData.setGrade(0);
		try {
			if(usersDataMapper.insert(usersData)>0){
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
	
	/**
	 * 通过资料的Id 得到资料信息
	 * @param userDataId
	 * @return
	 */
	public UsersData getUserDataById(Integer userDataId){
		UsersData usersData=null;
		try {
			usersData=usersDataMapper.selectByPrimaryKey(userDataId);
			return usersData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到用户信息通过经验排序  从高到低
	 * @return
	 */
	public List<UsersData> getUsersDataByExperienceDesc(){
		List<UsersData> list=null;
		try {
			list=usersDataMapper.selectByExperienceDesc();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 通过用户的id 添加所有的文章数和增添经验
	 * @param userId
	 * @return
	 */
	public boolean addArticleAllByUserId(Integer userId){
		Integer artileAll=usersDataMapper.selectArticlesAllByUsersId(userId);
		Integer experience=usersDataMapper.selectExperienceByUsersId(userId);
		if (artileAll == null){
			artileAll = 0;
		}
		if (experience == null){
			experience = 0;
		}
		UsersData usersData =new UsersData();
		usersData.setArticlesall(artileAll+1);
		usersData.setExperience(experience+1);
		usersData.setUsersid(userId);
		try {
			if(usersDataMapper.updateArticlesAllByUsersId(usersData)>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 修改用户资料
	 * @param usersData
	 * @return
	 */
	public boolean updateUserDate(UsersData usersData){
		try {
			if(usersDataMapper.updateByPrimaryKey(usersData)>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
