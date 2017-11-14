package com.ssm.control;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.Assess;
import com.ssm.entity.JSON;
import com.ssm.entity.UsersData;
import com.ssm.service.AssessService;

@Controller
@RequestMapping(value="assess")
public class AssessControl {
	
	@Autowired
	private AssessService assessService;
	
	/**
	 * 给文章评价
	 * @param assess
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value="add.do",method=RequestMethod.POST)
	@ResponseBody
	public JSON addAssess(@RequestBody Assess assess,HttpSession httpSession){
		JSON json=new JSON();
		UsersData usersData=(UsersData) httpSession.getAttribute("userdata");
		//判断用户是否登录
		if(usersData==null){
			json.setDescribe("请先登录");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			return json;
		}
		//得到用户信息
		assess.setUsersDataId(usersData.getId());
		//添加评论
		if(!assessService.addAssess(assess,httpSession)){
			json.setDescribe("评价失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			return json;
		}
		json.setDescribe("评价成功");
		json.setState(true);
		json.setStateId(JSON.SUCCESS);
		return json;
	}
}
