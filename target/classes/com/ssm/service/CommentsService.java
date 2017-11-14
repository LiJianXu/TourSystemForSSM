package com.ssm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.CommentsMapper;
import com.ssm.entity.Comments;

@Service
public class CommentsService {
	
	@Autowired
	private CommentsMapper commentsMapper;
	
	
	/**
	 * 通过文章的id 得到评论的总数
	 * @param articleId
	 * @return
	 */
	public Integer getCommentsAlls(Integer articleid){
		Integer all=0;
		try {
			all=commentsMapper.getCommentsAll(articleid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return all;
	}
	
	/**
	 * 通过文章id 得到评论
	 * @param articleId
	 * @return
	 */
	public List<Comments> selectCommentsByArticleId(Integer articleid){
		List<Comments> list=null;
		try {
			list=commentsMapper.getCommentsByArticleId(articleid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 添加评论
	 * @param comments
	 * @return
	 */
	public boolean insertComment(Comments comments){
		comments.setCreatetime(new Date());
		try {
			if(commentsMapper.insert(comments)>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
