package com.ssm.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.CollectionsMapper;
import com.ssm.entity.Articles;
import com.ssm.entity.Collections;

@Service
public class CollectionsService {

	private static Logger logger=LoggerFactory.getLogger(CollectionsService.class);
	
	@Autowired
	private CollectionsMapper collectionsMapper;
	
	@Autowired
	private ArticlesService articlesService;
	
	/**
	 * 验证该用户是否收藏过
	 * @param articlesId
	 * @param userDataId
	 * @return
	 */
	public boolean checkIsCollections(Integer articleId,Integer userDataId){
		Collections collections=new Collections();
		collections.setArticleid(articleId);
		collections.setUserdateid(userDataId);
		try {
			//判断该用户是否收藏过
			if(collectionsMapper.checkCollectionsByUserDataId(collections).get(0).getId()>0){
				return true;
			}
		}catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
			logger.info("该用户没有收藏过");
			return false;
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 验证是否显示收藏的按钮
	 * @param articles
	 * @param usersDataId
	 * @return
	 */
	public boolean checkShowCollections(Articles articles,Integer usersDataId){
		try {
			//判断该文章是否自己的
			if(articlesService.checkAriticleByMe(articles, usersDataId)){
				return false;
			}
			//判断该用户是否转发过
			if(this.checkIsCollections(articles.getId(), usersDataId)){
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 添加收藏记录
	 * @param articles
	 * @param usersDataId
	 * @return
	 */
	public boolean addCollection(Articles articles,Integer usersDataId){
		//验证是否是自己的文章
		if(articlesService.checkAriticleByMe(articles,usersDataId)){
			return false;
		}
		//验证用户是否收藏过
		if(this.checkIsCollections(articles.getId(), usersDataId)){
			return false;
		}
		Collections collections=new Collections();
		collections.setArticleid(articles.getId());
		collections.setArticletitle(articles.getTitle());
		collections.setCreatetime(new Date());
		collections.setUserdateid(usersDataId);
		try {
			//插入收藏记录
			if(collectionsMapper.insert(collections)>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 通过用户的资料id 得到分页信息
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<Collections> getCollectionsByUserDataId(Integer userId,Integer page,Integer size){
		PageInfo<Collections> pageInfo = null;
		try {
			List<Collections> collections=collectionsMapper.getCollectionsByUserDataId(userId);
			PageHelper.startPage(page, size);
			
			pageInfo=new PageInfo<Collections>(collections);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 通过收藏的id 删除收藏记录
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id){
		try {
			if(collectionsMapper.deleteByPrimaryKey(id)>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
