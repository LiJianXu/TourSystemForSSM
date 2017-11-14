package com.ssm.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.ArticleFilesMapper;
import com.ssm.entity.ArticleFiles;
import com.ssm.entity.Articles;
import com.ssm.entity.UsersData;

@Service
public class ArticleFilesService {
	
	@Autowired
	private ArticleFilesMapper articleFilesMapper;
	
	/**
	 * 插入上传的文件
	 * @param articleId
	 * @param name
	 * @param address
	 * @return
	 */
	public boolean addFileToArticle(Integer articleId,String name,String address){
		ArticleFiles articleFiles=new ArticleFiles();
		articleFiles.setArticleid(articleId);
		articleFiles.setGrade(0);
		articleFiles.setName(name);
		articleFiles.setAddressurl(address);
		try {
			if(articleFilesMapper.insert(articleFiles)>0){
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
	
	/**
	 * 得到当前用户下所有的文件
	 * @param httpSession
	 * @return
	 */
	public List<String> readFileName(HttpSession httpSession){
		List<String> list=new ArrayList<String>();
		UsersData usersData=(UsersData) httpSession.getAttribute("userdata");
		String filename=String.valueOf(usersData.getId());
		ServletContext servletContext=httpSession.getServletContext();
		String path=servletContext.getRealPath("upload")+"/"+filename;
		File file=new File(path);
		if(file.exists()){
			//if(file.listFiles())
			File[] files=file.listFiles();
			if(files!=null){
				for (File file2 : files) {
					list.add("/"+filename+"/"+file2.getName());
				}
			}
		}
		return list;
	}
	
	/**
	 * 通过文章的id 获取所有的文件
	 * @param articleId
	 * @return
	 */
	public List<ArticleFiles> getFilesByArticleId(Integer articleId){
		List<ArticleFiles> files=null;
		try {
			files=articleFilesMapper.selectAllByArticleId(articleId);
			return files;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过文件的id 获得文件
	 * @param id
	 * @return
	 */
	public ArticleFiles getFileById(Integer id){
		ArticleFiles articleFiles=null;
		articleFiles=articleFilesMapper.selectByPrimaryKey(id);
		if(articleFiles!=null){
			return articleFiles;
		}
		return null;
	}
	
	/**
	 * 通过用户id 得到分组
	 * @param userId
	 * @return
	 */
	public List<Articles> getGroupIdsByUserId(Integer userId){
		List<Articles> list=null;
		try {
			list = articleFilesMapper.selectGroupByArticleIdByUserDateId(userId);
			if (list == null){
				return null;
			}else{
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	//通过用户和文章id 得到文件
	public List<ArticleFiles> getArticleFilesByArticleId(Integer id,Integer userId){
		List<ArticleFiles> articleFiles =null;
		Articles articles = new Articles();
		articles.setUserdateid(userId);
		articles.setId(id);
		try {
			articleFiles = articleFilesMapper.selectAllByUserDataId(articles);
			if(articleFiles == null){
				return null;
			}else{
				return articleFiles;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过文件id 删除文件
	 * @param fileId
	 * @return
	 */
	public boolean deleteByFileId(Integer fileId){
		try {
			if(articleFilesMapper.deleteByPrimaryKey(fileId)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
