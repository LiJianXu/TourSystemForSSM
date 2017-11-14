package com.ssm.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.TurnsMapper;
import com.ssm.entity.Articles;
import com.ssm.entity.Turns;
import com.ssm.entity.UsersData;

@Service
public class TurnsService {

	private static Logger logger=LoggerFactory.getLogger(TurnsService.class);
	
	@Autowired
	private TurnsMapper turnsMapper;
	
	@Autowired
	private ArticlesService articlesService;
	
	
	/**
	 * 验证用户是否转载过
	 * @param articlesId
	 * @param userDataId
	 * @return
	 */
	public boolean checkIsTurns(Integer articlesId,Integer userDataId){
		Turns turns=new Turns();
		turns.setArticleid(articlesId);
		turns.setTurnuserid(userDataId);
		try {
			//开始验证用户是否转载过
			if(turnsMapper.checkTurnsByUserDataId(turns).get(0).getId()>0){
				logger.info("该用户转发过");
				return true;
			}
		}catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
			logger.info("该用户没有转过");
			return false;
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * 添加一条转载文章记录 (包含是否是自己和是否转载过的验证)
	 * @param articles
	 * @param usersDataId
	 * @return
	 */
	public boolean addTurns(Articles articles,Integer usersDataId){
		//验证是否是自己的文章
		if(articlesService.checkAriticleByMe(articles,usersDataId)){
			return false;
		}
		//验证用户是否转载过
		if(this.checkIsTurns(articles.getId(), usersDataId)){
			return false;
		}
		//创建转载信息
		Turns turns=new Turns();
		turns.setArticleid(articles.getId());
		turns.setTurnuserid(usersDataId);
		turns.setCreatetime(new Date());
		try {
			//把转载信息添加到数据库中
			if(turnsMapper.insert(turns)>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否可以显示可以转载的按钮
	 * @param articles
	 * @param usersDataId
	 * @return
	 */
	public boolean checkShowTurn(Articles articles,Integer usersDataId){
		try {
			//判断该文章是否自己的
			if(articlesService.checkAriticleByMe(articles, usersDataId)){
				return false;
			}
			//判断该用户是否转发过
			if(this.checkIsTurns(articles.getId(), usersDataId)){
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
