package com.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.ArticlesCategoryRMapper;
import com.ssm.entity.ArticlesCategoryR;

@Service
public class ArticlesCategoryRService {
	
	@Autowired
	private ArticlesCategoryRMapper articlesCategoryRMapper;
	
	/**
	 * 插入文章分类的关系记录
	 * @param articlesCategoryR
	 * @return
	 */
	public boolean addACR(Integer categoryId ,Integer articleId){
		try {
			ArticlesCategoryR articlesCategoryR2=new ArticlesCategoryR(0,categoryId,articleId);
			if(articlesCategoryRMapper.insert(articlesCategoryR2)>0){
				return true;
			}else{
				return false;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
		
	}
}
