package com.ssm.dao;

import com.ssm.entity.ArticlesCategoryR;
import java.util.List;

public interface ArticlesCategoryRMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticlesCategoryR record);

    ArticlesCategoryR selectByPrimaryKey(Integer id);

    List<ArticlesCategoryR> selectAll();

    int updateByPrimaryKey(ArticlesCategoryR record);
    
}