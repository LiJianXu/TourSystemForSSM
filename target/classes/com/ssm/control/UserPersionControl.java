package com.ssm.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ssm.entity.ArticleFiles;
import com.ssm.entity.ArticleType;
import com.ssm.entity.Articles;
import com.ssm.entity.Collections;
import com.ssm.entity.JSON;
import com.ssm.entity.Test;
import com.ssm.entity.Users;
import com.ssm.entity.UsersData;
import com.ssm.jsonuntil.JsonUtils;
import com.ssm.service.ArticleFilesService;
import com.ssm.service.ArticlesService;
import com.ssm.service.CollectionsService;
import com.ssm.service.UsersDataService;
import com.ssm.service.UsersService;
import com.sun.tools.classfile.StackMapTable_attribute.append_frame;
import com.sun.tools.classfile.StackMapTable_attribute.chop_frame;

@RequestMapping(value="user")
@Controller
public class UserPersionControl {
	
	private static Logger logger = LoggerFactory.getLogger(UserPersionControl.class);
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private UsersDataService usersDataService;
	@Autowired
	private ArticlesService articlesService;
	@Autowired
	private CollectionsService collectionsService;
	@Autowired
	private ArticleFilesService articleFilesService;
	/**
	 * 进入个人中心
	 * @return
	 */
	@RequestMapping(value="enter_personal.do",method=RequestMethod.GET)
	public String enterPersonal(HttpSession httpSession){
		UsersData usersData = (UsersData) httpSession.getAttribute("userdata");
		if(usersData != null){
			httpSession.setAttribute("userdata", usersDataService.getDataByUserId(usersData.getUsersid()));
			Users users = usersService.getUserById(usersData.getUsersid());
			httpSession.setAttribute("users",users);
		}
		return "personal";
	}
	
	/**
	 * 通过分页得到文章信息
	 * @param userDataId
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="getBlogslist.do",method=RequestMethod.GET)
	@ResponseBody
	public String getBlogslist(@RequestParam("userDataId") Integer userDataId,@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		//通过资料id  分页查找文章
		PageInfo<Articles> pageInfo=articlesService.getArticlesByUserDataId(userDataId, page, size);
		Map<Class<?>, String> include =new HashMap<Class<?>, String>();
		include.put(Articles.class,"id,title,createtime,articleType");
		include.put(ArticleType.class, "id,name");
		return JsonUtils.obj2jsonWithInclude(pageInfo, include);
	}
	
	/**
	 * 通过分页得到收藏信息
	 * @param userDataId
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="getCollectionslist.do",method=RequestMethod.GET)
	@ResponseBody
	public PageInfo<Collections> getCollectionslist(@RequestParam("userDataId") Integer userDataId,@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		PageInfo<Collections> cPageInfo = collectionsService.getCollectionsByUserDataId(userDataId, page, size);
/*		Map<Class<Collections>, String> include = new HashMap<Class<Collections>, String>();
		include*/
		return cPageInfo;
	}
	
	/**
	 * 删除文章
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="deleteById.do",method=RequestMethod.GET)
	@ResponseBody
	public JSON deleteById(@RequestParam("articleId") Integer articleId){
		JSON json=new JSON();
		if(articlesService.delateById(articleId)){
			json.setDescribe("删除成功");
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("删除失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}
		return json;
	}
	
	/**
	 * 删除收藏 通过收藏的id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteCollectionsById.do",method=RequestMethod.GET)
	@ResponseBody
	public JSON deleteCollectionsById(@RequestParam("id") Integer id){
		JSON json=new JSON();
		if(collectionsService.deleteById(id)){
			json.setDescribe("删除成功");
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("删除失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}
		return json;
	}
	
	/**
	 * 通过课程id 得到用户的所有文件
	 * @param httpSession
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value="getFiles.do",method=RequestMethod.GET)
	@ResponseBody
	public String getFiles(HttpSession httpSession,@RequestParam("courseId") Integer courseId){
		UsersData user =(UsersData) httpSession.getAttribute("userdata");
		Integer userId = user.getId();
		List<ArticleFiles> articleFiles=articleFilesService.getArticleFilesByArticleId(courseId, userId);
		Map<Class<?>, String> include = new HashMap<Class<?>, String>();
		include.put(ArticleFiles.class, "id,articleid,name,addressurl,grade");
		return JsonUtils.obj2jsonWithInclude(articleFiles, include);
	}
	
	/**
	 * 得到文章的分组
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value="getFilesGroupId.do",method=RequestMethod.GET)
	@ResponseBody
	public String getFilesGroupId(HttpSession httpSession){
		UsersData user=(UsersData) httpSession.getAttribute("userdata");
		List<Articles> articles = articleFilesService.getGroupIdsByUserId(user.getId());
		Map<Class<?>, String> include = new HashMap<Class<?>, String>();
		include.put(Articles.class, "id,title,createtime,articleType");
		include.put(ArticleType.class, "id,name");
		return JsonUtils.obj2jsonWithInclude(articles, include);
	}
	
	/**
	 * 通过文件的id 删除文件
	 * @param fileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="deleteFileId.do",method=RequestMethod.GET)
	public JSON deleteFileById(@RequestParam("fileId") Integer fileId){
		JSON json = new JSON();
		if (articleFilesService.deleteByFileId(fileId)){
			json.setDescribe("删除文件成功");
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("删除文件失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}
		return json;
	}
}
