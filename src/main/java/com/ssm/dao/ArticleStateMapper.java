package com.ssm.dao;

import com.ssm.entity.ArticleState;
import java.util.List;

public interface ArticleStateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleState record);

    ArticleState selectByPrimaryKey(Integer id);

    List<ArticleState> selectAll();

    int updateByPrimaryKey(ArticleState record);
}