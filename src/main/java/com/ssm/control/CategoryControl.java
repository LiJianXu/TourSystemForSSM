package com.ssm.control;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.Category;
import com.ssm.entity.JSON;
import com.ssm.service.CategoryService;

@Controller
@RequestMapping(value="category")
public class CategoryControl {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 得到所有的分类
	 * @param httpSession
	 */
	@ResponseBody
	@RequestMapping(value="get_categorys.do",method=RequestMethod.GET)
	public JSON showCategorys(HttpSession httpSession){
		JSON json = new JSON();
		//设置所有的分类
		httpSession.setAttribute("categorys",categoryService.getCategorys());
		List<Category> categories = categoryService.getCategorys();
		if(categories==null){
			json.setDescribe("得到分类失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}else{
			json.setDescribe("得到分类");
			json.setObj(categories);
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}
		return json;
	}
}
