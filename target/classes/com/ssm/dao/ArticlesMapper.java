package com.ssm.dao;

import com.ssm.entity.Articles;
import java.util.List;

public interface ArticlesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Articles record);

    Articles selectByPrimaryKey(Integer id);

    List<Articles> selectAll();

    int updateByPrimaryKey(Articles record);
    
    /**
     * 通过分类的Id 查询文章
     * @param categoryId
     * @return
     */
    List<Articles> selectByCategoryId(Integer categoryId);
    
    /**
     * 修改文章的评论
     * @param articlesId
     * @param type
     * @return
     */
    Integer assessArticlesAdd(Articles record);
    
    /**
     * 通过用户的id得到文章
     * @param userDateId
     * @return
     */
    List<Articles> selectByUserDataId(Integer userdateid);
    
    /**
     * 通过用户的资料id 得到文章信息
     * @param userdateid
     * @return
     */
    List<Articles> selectSimpleArticleByUserId(Integer userdateid);
    
    /**
     * 删除文章
     * @param id
     * @return
     */
    Integer deleteById(Integer id);
}