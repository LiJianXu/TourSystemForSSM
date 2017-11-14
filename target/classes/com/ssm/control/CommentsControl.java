package com.ssm.control;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.Comments;
import com.ssm.entity.JSON;
import com.ssm.entity.UsersData;
import com.ssm.service.CommentsService;

@Controller
@RequestMapping(value="comments")
public class CommentsControl {
	
	private static Logger logger=LoggerFactory.getLogger(CommentsControl.class);
	
	@Autowired
	private CommentsService commentsService;
	
	/**
	 * 通过文章id 得到评论
	 * @param articleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getByarticleId.do",method=RequestMethod.GET)
	private JSON getCommentsByArticleId(@RequestParam("articleId") Integer articleid){
		JSON json = new JSON();
		try {
			List<Comments> comments=commentsService.selectCommentsByArticleId(articleid);
			if(comments.size() > 0){
				json.setDescribe("得到评论");
				json.setObj(comments);
				json.setState(true);
				json.setStateId(JSON.SUCCESS);
				return json;
			}else{
				json.setDescribe("评论为空");
				json.setState(false);
				json.setStateId(JSON.ERROR);
				return json;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		json.setDescribe("得到评论失败");
		json.setState(false);
		json.setStateId(JSON.ERROR);
		return json;
	}
	
	/**
	 * 添加评论
	 * @param context
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="insertComment.do",method=RequestMethod.POST)
	private JSON insertComments(@RequestBody Comments comments,HttpSession httpSession) {
		JSON json = new JSON();
		UsersData usersData = (UsersData) httpSession.getAttribute("userdata");
		if (usersData == null){
			json.setDescribe("请登录");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			return json;
		}else{
			comments.setUserdataid(usersData.getId());
		}
		if (commentsService.insertComment(comments)){
			json.setDescribe("添加评论成功");
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("添加评论失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}
		return json;
	}
}
