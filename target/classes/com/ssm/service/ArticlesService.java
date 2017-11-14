package com.ssm.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.ArticlesMapper;
import com.ssm.entity.Articles;
import com.ssm.entity.Assess;

@Service
public class ArticlesService {
	
	private static Logger logger=LoggerFactory.getLogger(ArticlesService.class);
	
	@Autowired
	private ArticlesMapper articlesMapper;
	
	@Autowired
	private UsersDataService usersDataService;
	/**
	 * 通过分类的Id 和页数分页
	 * @param categoryId
	 * @param page
	 * @return
	 */
	public PageInfo<Articles> getArticlesByCategoryId(Integer categoryId,Integer page){
		PageHelper.startPage(page, 4);
		List<Articles> articles=articlesMapper.selectByCategoryId(categoryId);
		PageInfo<Articles> pageInfo=new PageInfo<Articles>(articles);
		return pageInfo;
	}

	
	/**
	 * 插入一条记录
	 * @param articles
	 * @return
	 */
	public Integer addArticles(Articles articles){
		Date date=new Date();
		articles.setCreatetime(date);
		articles.setUpdatetime(date);
		articles.setBad(0);
		articles.setGood(0);
		articles.setBrowses(0);
		System.out.println("内容:"+articles.getContents());
		//把换行符替换为  \n
		articles.setContents(articles.getContents().replaceAll("\n", "\\\\n"));
		try {
			if(articlesMapper.insert(articles)>0){
				//System.out.println("插入文章后得到的id"+articles.getId());
				//添加文章总数和经验记录
				if(usersDataService.addArticleAllByUserId(articles.getUserdateid())){
					logger.info("根据文章作者的id添加文章总数和经验成功");
				}else{
					logger.info("根据文章作者的id添加文章总数和经验失败");
				}
				return articles.getId();
			}else{
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 通过博客的id 得到博客
	 * @param articleId
	 * @return
	 */
	public Articles showArticles(Integer articleId){
		Articles articles=null;
		try {
			articles=articlesMapper.selectByPrimaryKey(articleId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return articles;
	}
	
	/**
	 * 修改文章的评论
	 * @param articleId
	 * @param type
	 * @return
	 */
	public boolean modifyAssessArticlesAdd(Integer type,HttpSession httpSession){
		Articles articles=(Articles) httpSession.getAttribute("article");
		//从session中获取article对象的信息
		Integer id=articles.getId();
		Integer good=articles.getGood();
		Integer bad=articles.getBad();
		//判断修改的类型
		if(type.equals(Assess.ASSESS_TYPE_GOOD)){
			good=articles.getGood()+1;
		}else if(type.equals(Assess.ASSESS_TYPE_BAD)){
			bad=articles.getBad()+1;
		}
		try {
			Articles articles2=new Articles();
			articles2.setId(id);
			articles2.setGood(good);
			articles2.setBad(bad);
			
			//System.out.println(articlesMapper.assessArticlesAdd(articles2));
			if(articlesMapper.assessArticlesAdd(articles2)>0){
				return true;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 验证文章是否是自己发布的
	 * @param articles
	 * @param usersDataId
	 * @return
	 */
	public boolean checkAriticleByMe(Articles articles,Integer usersDataId){
		try {
			//判断session中文章是否是自己的
			if(articles.getUserdateid().equals(usersDataId)){
				return true;
			}else{
				logger.info("该文章不是该用户的");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 通过用户的id 得到所有的文章
	 * @param userDataId
	 * @return
	 */
	public List<Articles> getArticlesByUserDataId(Integer userDataId){
		List<Articles> list=null;
		try {
			list=articlesMapper.selectByUserDataId(userDataId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 通过用户的id 和page  size 得到文章信息
	 * @param userDataId
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<Articles> getArticlesByUserDataId(Integer userDataId,Integer page,Integer size){
		PageHelper.startPage(page, size);
		List<Articles> articles=articlesMapper.selectSimpleArticleByUserId(userDataId);
		PageInfo<Articles> pageInfo=new PageInfo<Articles>(articles);
		return pageInfo;
	}
	
	/**
	 * 通过文章id 删除文章
	 * @param id
	 * @return
	 */
	public boolean delateById(Integer id){
		try {
			if(articlesMapper.deleteById(id)>0){
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
}
