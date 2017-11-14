package com.ssm.dao;

import com.ssm.entity.Comments;
import java.util.List;

public interface CommentsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comments record);

    Comments selectByPrimaryKey(Integer id);

    List<Comments> selectAll();

    int updateByPrimaryKey(Comments record);
    
    /**
     * 通过文章的id 得到所有评论数
     * @param articleId
     * @return
     */
    Integer getCommentsAll(Integer articleid);
    
    /**
     * 通过文章的id 获得所有的评论
     * @param articleid
     * @return
     */
    List<Comments> getCommentsByArticleId(Integer articleid);
}