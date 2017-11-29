package com.ssm.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.JSON;
import com.ssm.entity.Users;
import com.ssm.service.UsersService;

@Controller
@RequestMapping(value="admin")
public class AdminControl {
	
	@Autowired
	private UsersService usersService;
	
	
	@RequestMapping(value="enter.do",method=RequestMethod.GET)
	public String enterAdmin(){
		return "adminLogin";
	}
	
	/**
	 * 管理员登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="index.do",method=RequestMethod.POST)
	public String enterIndex(@RequestParam("phone") String username,@RequestParam("password") String password){
		Users user=new Users();
		user.setPhone(username);
		user.setPassword(password);
		try {
			if(usersService.adminLogin(user)){
				return "adminIndex";
			}else{
				return "adminLogin";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "adminLogin";
	}
	
	@RequestMapping(value="update.do",method=RequestMethod.POST)
	@ResponseBody
	public JSON updateAdmin(@RequestParam("newPassword") String password){
		JSON json = new JSON();
		if(usersService.updateUser(password)){
			json.setDescribe("修改密码成功");
			json.setState(true);
			return json;
		}
		json.setDescribe("修改密码失败");
		json.setState(false);
		return json;
	}
	
	/**
	 * 进入文章管理界面
	 * @return
	 */
	@RequestMapping(value="articleManage.do",method=RequestMethod.GET)
	public String intoArticleManage(){
		return "articleManage";
	}
	
	/**
	 * 进入用户管理界面
	 * @return
	 */
	@RequestMapping(value="userManage.do",method=RequestMethod.GET)
	public String intoUserManage(){
		return "userManage";
	}
}
