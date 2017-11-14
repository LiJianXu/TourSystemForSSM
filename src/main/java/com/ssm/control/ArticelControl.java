package com.ssm.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ssm.entity.Articles;
import com.ssm.entity.ArticlesCategoryR;
import com.ssm.entity.JSON;
import com.ssm.entity.Occupations;
import com.ssm.entity.UsersData;
import com.ssm.jsonuntil.JsonUtils;
import com.ssm.service.ArticleFilesService;
import com.ssm.service.ArticlesService;
import com.ssm.service.AssessService;
import com.ssm.service.CollectionsService;
import com.ssm.service.CommentsService;
import com.ssm.service.OccupationsService;
import com.ssm.service.TurnsService;
import com.ssm.service.UsersDataService;
import com.sun.tools.classfile.StackMapTable_attribute.append_frame;

@Controller
@RequestMapping(value="DetailedArticel")
public class ArticelControl {
	
	@Autowired
	private ArticlesService articlesService;
	
	@Autowired
	private ArticleFilesService articleFilesService;
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private UsersDataService usersDataService;
	
	@Autowired
	private OccupationsService occupationsService;
	
	@Autowired
	private AssessService assessService;
	
	@Autowired
	private TurnsService turnsService;
	
	@Autowired
	private CollectionsService collectionsService;
	
	/**
	 * 进入编辑界面
	 * @return
	 */
	@RequestMapping(value="enterEdit.do",method=RequestMethod.GET)
	public String enterEditArticel(){
		return "editArticle";
	}
	
	
	/**
	 * 分页得到博客
	 * @param categoryId
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getArticlesByCategory.do",method=RequestMethod.GET)
	public PageInfo<Articles> getArticlesCategorys(@RequestParam("categoryId")Integer categoryId,@RequestParam("page")Integer page){
		PageInfo<Articles> pageInfo=articlesService.getArticlesByCategoryId(categoryId, page);
		return pageInfo;
	}
	
	/**
	 * 添加一条博客记录
	 * @param articles
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value="add.do",method=RequestMethod.POST)
	@ResponseBody
	public JSON addArticel(@RequestBody Articles articles,HttpSession httpSession){
		JSON json=new JSON();
		System.out.println(articles);
		try {
			UsersData usersData=(UsersData) httpSession.getAttribute("userdata");
			if(usersData!=null){
				articles.setUserdateid(usersData.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.setDescribe("得到用户的资料异常");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			return json;
		}
		
		Integer articleId=articlesService.addArticles(articles);
		//读取文件名
		List<String> names=articleFilesService.readFileName(httpSession);
		//一个一个插入把文件名插入数据库
		for (int i = 0; i < names.size(); i++) {
			String filename=names.get(i).substring(names.get(i).indexOf("@")+1);
			System.out.println("插入数据库文件的名字"+filename);
			articleFilesService.addFileToArticle(articleId, filename, names.get(i));
		}
		if(articleId>0){
			json.setDescribe("插入文章成功");
			json.setState(true);
			json.setObj(articleId);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("插入文章失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}
		return json;
	}
	
	/**
	 * 得到文章信息
	 * @param articleId
	 * @param httpSession
	 * @return
	 */
	
	@RequestMapping(value="getArticle.do",method=RequestMethod.GET)
	public String getArticle(@RequestParam("articleId") Integer articleId,HttpSession httpSession){
		Articles articles=null;
		Integer commentsAll=0;
		//每个初始请求为0  即设置为没有请求
		httpSession.setAttribute("isAssessGoodAndBad", 0);
		//默认为可以显示转发的按钮
		httpSession.setAttribute("showTurnsBtn", 0);
		//默认为可以显示收藏的按钮
		httpSession.setAttribute("showCollectionsBtn", 0);
		//得到文章信息
		articles=articlesService.showArticles(articleId);
		//得到文章所有的评论数
		commentsAll=commentsService.getCommentsAlls(articleId);
		httpSession.setAttribute("commentsAll", commentsAll);
		
		UsersData usersData=null;
		//得到用户详细资料
		usersData=usersDataService.getUserDataById(articles.getUserdateid());
		Occupations occupations=null;
		//得到用户的职业
		occupations=occupationsService.getOccupationsById(usersData.getOccupationid());
		if(occupations!=null){
			httpSession.setAttribute("occupations", occupations);
		}
		//详细界面上 文章作者的信息
		if(usersData!=null){
			httpSession.setAttribute("detailUsersData", usersData);
			//把所有的视图放入session中
			setArticelView(usersData.getId(),httpSession);
		}
		UsersData usersDataOnlne;
		//判断是否有用户登录
		usersDataOnlne=(UsersData) httpSession.getAttribute("userdata");
		if(usersDataOnlne!=null){
			//判断用户是否评价过
			if(assessService.checkAssessUserDataId(articleId, usersDataOnlne.getId())){
				httpSession.setAttribute("isAssessGoodAndBad", 1);
			}
		}	
		if(articles!=null){
			httpSession.setAttribute("article", articles);
		}else{
			System.out.println("得到文章出错");
		}
		UsersData sessionUsersData=null;
		sessionUsersData=(UsersData) httpSession.getAttribute("userdata");
		//判断session中是否有用户的信息
		if(sessionUsersData!=null){
			try {
				//验证是否显示转发按钮
				if(!turnsService.checkShowTurn(articles, sessionUsersData.getId())){
					httpSession.setAttribute("showTurnsBtn", 1);
				}
				//验证是否显示转发按钮
				if(collectionsService.checkShowCollections(articles, sessionUsersData.getId())){
					httpSession.setAttribute("showCollectionsBtn", 1);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return "datesArticle";
	}
	
	/**
	 * 得到文章的list
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="articelView.do",method=RequestMethod.GET)
	public String articelViewList(@RequestParam(value="userDataId") Integer userDataId,HttpSession httpSession){
		Integer sessionUserId=(Integer) httpSession.getAttribute("ViewUserId");
		//判断session中是否有查询后的记录
		if(sessionUserId!=null && sessionUserId.equals(userDataId)){
			String result=(String) httpSession.getAttribute("articelView");
			return result;
		}else{
			//通过userdataid 获得articleslist
			List<Articles> articles=articlesService.getArticlesByUserDataId(userDataId);
			Map<Class<?>, String> include =new HashMap<Class<?>, String>();
			//添加要过滤的字段
			include.put(Articles.class, "contents,editcontenttype");
			httpSession.setAttribute("ViewUserId", userDataId);
			httpSession.setAttribute("articelView", JsonUtils.obj2jsonWithFilter(articles, include));
			return JsonUtils.obj2jsonWithFilter(articles, include);
		}
	}
	
	/**
	 * 把所有的视图信息放入session中
	 * @param userDataId
	 * @param httpSession
	 */
	public void setArticelView(Integer userDataId,HttpSession httpSession){
		//通过userdataid 获得articleslist
		List<Articles> articles=articlesService.getArticlesByUserDataId(userDataId);
		httpSession.setAttribute("articelView", articles);
	}
}
