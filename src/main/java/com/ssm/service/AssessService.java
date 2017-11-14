package com.ssm.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.dao.AssessMapper;
import com.ssm.entity.Assess;

@Service
public class AssessService {
	
	private static Logger logger=LoggerFactory.getLogger(AssessService.class);
	
	@Autowired
	private AssessMapper assessMapper;
	
	@Autowired
	private ArticlesService articlesService;
	
	/**
	 * 添加评价
	 * @param assess
	 * @return
	 */
	public Boolean addAssess(Assess assess,HttpSession httpSession){
		try {
			assess.setAssessDate(new Date());
			if(!this.checkAssessUserDataId(assess.getArticleId(), assess.getUsersDataId())){
				//判断添加评价是否成功
				if(assessMapper.insert(assess)>0){
					//修改文章评价的记录
					if(articlesService.modifyAssessArticlesAdd(assess.getAssessType(), httpSession)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}				
			}else{
				System.out.println("该用户已经评价过");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 验证用户是否评价过
	 * @param articleId
	 * @param userDataId
	 * @return
	 */
	public boolean checkAssessUserDataId(Integer articleId,Integer usersDataId){
		Assess assess=new Assess();
		assess.setArticleId(articleId);
		assess.setUsersDataId(usersDataId);
		try {
			if(assessMapper.checkAssessUserDataId(assess).get(0).getId()>0){
				return true;
			}
		}catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
			logger.debug("没有评价过");
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
