package com.ssm.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.JSON;
import com.ssm.entity.Users;
import com.ssm.entity.UsersData;
import com.ssm.service.OccupationsService;
import com.ssm.service.UsersDataService;
import com.ssm.service.UsersService;

@Controller
@RequestMapping(value="UserData")
public class UserDataControl {
	
	@Autowired
	private UsersDataService usersDataService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private OccupationsService occupationsService;
	
	/**
	 * 通过资料的Id 得到资料
	 * @param userdataId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getUserData.do",method=RequestMethod.GET)
	public UsersData getUserDataById(@RequestParam ("userdataId")Integer userdataId){
		return usersDataService.getUserDataById(userdataId);
	}
	
	/**
	 * 得到热门的作者的资料
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="byExperienceDesc.do",method=RequestMethod.GET)
	public JSON getUsersDataByExperienceDesc(){
		JSON json=new JSON();
		List<UsersData> list=null;
		list=usersDataService.getUsersDataByExperienceDesc();
		if(list!=null){
			json.setObj(list);
			json.setDescribe("得到热门的作者");
			json.setStateId(JSON.SUCCESS);
			json.setState(true);
		}else{
			json.setDescribe("得到热门的作者失败");
			json.setStateId(JSON.ERROR);
			json.setState(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value="updateUserDate.do",method=RequestMethod.POST)
	public JSON updateUserDate(HttpServletRequest request){
		JSON json = new JSON();
		String name=request.getParameter("name");
		String occupationsname=request.getParameter("occupationsname");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String introduction=request.getParameter("introduction");
		HttpSession httpSession=request.getSession();
		UsersData usersData = (UsersData) httpSession.getAttribute("userdata");
		Users users= new Users();
		users.setId(usersData.getUsersid());
		users.setEmail(email);
		users.setPhone(phone);
		//添加手机和邮箱
		if(!usersService.updateUser(users)){
			json.setDescribe("修改手机和邮箱失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			return json;
		}
		UsersData usersData2 = new UsersData();
		usersData2.setId(usersData.getId());
		usersData2.setIntroduction(introduction);
		//添加职业
		Integer occId=occupationsService.insertOccupations(occupationsname);
		if( occId>0 ){
			usersData2.setOccupationid(occId);
		}else{
			usersData2.setOccupationid(usersData.getOccupationid());
		}
		usersData2.setName(name);
		//添加用户资料
		if(!usersDataService.updateUserDate(usersData2)){
			json.setDescribe("修改用户资料失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
			return json;
		}
		json.setDescribe("修改用户资料成功");
		json.setState(true);
		json.setStateId(JSON.SUCCESS);
		return json;
	}
}
