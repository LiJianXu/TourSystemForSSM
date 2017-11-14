package com.ssm.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.ModelAndView;

import com.ssm.entity.JSON;
import com.ssm.entity.Users;
import com.ssm.entity.UsersData;
import com.ssm.service.UsersDataService;
import com.ssm.service.UsersService;
import com.ssm.until.RegexUtils;


@RequestMapping(value="user")
@Controller
public class UserControl {
	
	private static Logger logger=LoggerFactory.getLogger(UserControl.class);
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UsersDataService usersDataService;

	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	@ResponseBody
	public JSON login(@RequestBody Users users,HttpSession httpSession){
		JSON json=new JSON();
		String username=users.getPhone();
		String password=users.getPassword();
		logger.debug("得到登录的请求参数:username:"+username+" password:"+password);
		if(RegexUtils.checkEmail(username)){
			users.setEmail(username);
			Integer userid=usersService.loginByEmail(users);
			if(userid>0){
				System.out.println("登录成功");
				System.out.println("邮箱成功");
				UsersData usersData;
				//通过用户的Id 得到用户的详细信息
				usersData=usersDataService.getDataByUserId(userid);
				if(usersData!=null){
					httpSession.setAttribute("userdata", usersData);
				}
				json.setState(true);
				json.setStateId(JSON.SUCCESS);
				json.setDescribe("登录成功");
			}else{
				System.out.println("登录失败");
				json.setState(false);
				json.setStateId(JSON.ERROR);
				json.setDescribe("邮箱或者密码错误");
			}				
		}else if(RegexUtils.checkMobile(username)){
			users.setPhone(username);
			Integer userid=usersService.loginByPhone(users);
			if(userid>0){
				System.out.println("登录成功");
				System.out.println("手机号成功");
				UsersData usersData;
				//通过用户的Id 得到用户的详细信息
				usersData=usersDataService.getDataByUserId(userid);
				if(usersData!=null){
					httpSession.setAttribute("userdata", usersData);
				}
				json.setState(true);
				json.setStateId(JSON.SUCCESS);
				json.setDescribe("登录成功");
			}else{
				System.out.println("登录失败");
				json.setState(false);
				json.setStateId(JSON.ERROR);
				json.setDescribe("手机号码不存在或者密码错误");
			}	
		}else{
			System.out.println("请输入正确的用户名和密码");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			json.setDescribe("用户不存在");
		}
		return json;
	}
	
	/**
	 * 退出登录
	 * @param httpServletResponse
	 * @param httpSession
	 */
	@RequestMapping(value="out.do",method=RequestMethod.GET)
	public void OutLogin(HttpServletResponse httpServletResponse,HttpSession httpSession){
		if(httpSession.getAttribute("userdata")!=null){
			httpSession.setAttribute("userdata", null);
			System.out.println("用户退出登录");
		}
		try {
			httpServletResponse.sendRedirect("../index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册的方法
	 * @param users
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="register.do",method=RequestMethod.POST)
	public JSON register(@RequestBody Users users,HttpSession httpSession){
		JSON json=new JSON();
		String phone=users.getPhone();
		String email=users.getEmail();
		String password=users.getPassword();
		//判断是不是通过手机注册
		if(RegexUtils.checkMobile(phone)){
			if(usersService.checkPhone(phone)){
				json.setState(false);
				json.setStateId(JSON.ERROR);
				json.setDescribe("手机号注册过");
			}else{
				users.setId(null);
				users.setEmail("未填写邮箱");
				Integer userid=usersService.insertUser(users);
				if(userid>0){
					json.setState(true);
					json.setStateId(JSON.SUCCESS);
					json.setDescribe("注册成功");
				}else{
					json.setState(false);
					json.setStateId(JSON.ERROR);
					json.setDescribe("注册异常请重新注册");
				}
			}
			//判断是不是通过邮箱注册
		}else if(RegexUtils.checkEmail(email)){
			if(usersService.checkEmail(email)){
				json.setState(false);
				json.setStateId(JSON.ERROR);
				json.setDescribe("邮箱已经注册过");
			}else{
				users.setId(null);
				users.setPhone("未填写手机号");
				Integer userid=usersService.insertUser(users);
				if(userid>0){
					json.setState(true);
					json.setStateId(JSON.SUCCESS);
					json.setDescribe("注册成功");
				}else{
					json.setState(false);
					json.setStateId(JSON.ERROR);
					json.setDescribe("注册异常请重新注册");
				}
			}
		}else{
			json.setState(false);
			json.setStateId(JSON.ERROR);
			json.setDescribe("请输入手机号或者密码");
		}
		return json;
	}
}
