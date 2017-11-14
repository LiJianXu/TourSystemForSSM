package com.ssm.control;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.Articles;
import com.ssm.entity.JSON;
import com.ssm.entity.UsersData;
import com.ssm.service.CollectionsService;

@Controller
@RequestMapping(value="Collections")
public class CollectionsControl {
	
	@Autowired
	private CollectionsService collectionsService;
	
	/**
	 * 添加收藏记录
	 * @return
	 */
	@RequestMapping(value="add.do",method=RequestMethod.GET)
	@ResponseBody
	public JSON addCollections(HttpSession httpSession){
		JSON json=new JSON();
		Articles articles=(Articles) httpSession.getAttribute("article");
		UsersData usersData=(UsersData) httpSession.getAttribute("userdata");
		try {
			if(collectionsService.addCollection(articles, usersData.getId())){
				json.setDescribe("收藏成功");
				json.setState(true);
				json.setObj(0);
				json.setStateId(JSON.SUCCESS);
				return json;
			}else{
				json.setDescribe("该用户已经收藏");
				json.setState(false);
				json.setObj(1);
				json.setStateId(JSON.ERROR);
				return json;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		json.setDescribe("收藏失败");
		json.setState(false);
		json.setObj(2);
		json.setStateId(JSON.ERROR);
		return json;
	}
}
