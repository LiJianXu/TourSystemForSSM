package com.ssm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.CategoryMapper;
import com.ssm.entity.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	/**
	 * 得到所有的分类
	 * @return
	 */
	public List<Category> getCategorys(){
		List<Category> aCategories=categoryMapper.selectAll();
		return aCategories;
	}
}
