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
import com.ssm.service.TurnsService;

@Controller
@RequestMapping(value="Turns")
public class TurnsControl {
	
	@Autowired
	private TurnsService turnsService;
	
	//转载文章
	@RequestMapping(value="add.do",method=RequestMethod.GET)
	@ResponseBody
	public JSON addTurns(HttpSession httpSession){
		JSON json=new JSON();
		Articles articles=(Articles) httpSession.getAttribute("article");
		UsersData usersData=(UsersData) httpSession.getAttribute("userdata");
		try {
			if(turnsService.addTurns(articles, usersData.getId())){
				json.setDescribe("转发成功");
				json.setState(true);
				json.setObj(0);
				json.setStateId(JSON.SUCCESS);
				return json;
			}else{
				json.setDescribe("该用户已经转发或者是作者");
				json.setState(false);
				json.setObj(1);
				json.setStateId(JSON.ERROR);
				return json;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		json.setDescribe("转发失败");
		json.setState(false);
		json.setObj(2);
		json.setStateId(JSON.ERROR);
		return json;
	}
}
