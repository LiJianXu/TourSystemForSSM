package com.ssm.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.entity.JSON;
import com.ssm.service.ArticlesCategoryRService;

@Controller
@RequestMapping(value="ArticlesCategoryR")
public class ArticlesCategoryRControl {
	
	@Autowired
	private ArticlesCategoryRService articlesCategoryRService;
	
	/**
	 * 给文章添加分类
	 * @param categorys
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="add.do",method=RequestMethod.POST)
	@ResponseBody
	public JSON addACR(@RequestParam("categorys[]") String [] categorys,@RequestParam("articleId") Integer articleId){
		JSON json=new JSON();
		int i=0,j=0;
		for(;i<categorys.length;i++){
			try {
				if(!articlesCategoryRService.addACR(Integer.parseInt(categorys[i]), articleId)){
					System.out.println("添加分类id为"+categorys[i]+"失败");
				}else{
					j++;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		if(j==categorys.length){
			json.setDescribe("添加分类成功");
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("添加分类失败");
			json.setState(false);
			json.setStateId(JSON.ERROR);		
		}
		return json;
	}
}
