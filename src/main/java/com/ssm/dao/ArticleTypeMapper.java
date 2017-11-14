package com.ssm.dao;

import com.ssm.entity.ArticleType;
import java.util.List;

public interface ArticleTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleType record);

    ArticleType selectByPrimaryKey(Integer id);

    List<ArticleType> selectAll();

    int updateByPrimaryKey(ArticleType record);
}