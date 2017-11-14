package com.ssm.dao;

import com.ssm.entity.ArticleFiles;
import com.ssm.entity.Articles;

import java.util.List;

public interface ArticleFilesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleFiles record);

    ArticleFiles selectByPrimaryKey(Integer id);

    List<ArticleFiles> selectAll();

    int updateByPrimaryKey(ArticleFiles record);
    
    /**
     * 通过文章id 得到文章的文件
     * @param articleId
     * @return
     */
    List<ArticleFiles> selectAllByArticleId(Integer articleid);
    
    /**
     * 通过用户资料id 得到文章的文件
     * @param articleId
     * @param userdateid
     * @return
     */
    List<ArticleFiles> selectAllByUserDataId(Articles articles);
    
    /**
     * 通过过用户资料id 分组
     * @param userdataid
     * @return
     */
    List<Articles> selectGroupByArticleIdByUserDateId(Integer userdataid);
    
}