package com.ssm.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.entity.ArticleFiles;
import com.ssm.entity.Articles;
import com.ssm.entity.JSON;
import com.ssm.entity.UsersData;
import com.ssm.service.ArticleFilesService;
import com.ssm.until.FileUtil;

@Controller
@RequestMapping(value="File")
public class FileControl {
	
	@Autowired
	private ArticleFilesService articleFilesService;
	
	
	/**
	 * 多个文件上传
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value="treeFile.do",method=RequestMethod.POST)
	@ResponseBody
	public JSON fileUpload(@RequestParam("file") CommonsMultipartFile files[],  
	        HttpServletRequest request,HttpSession httpSession){
		JSON json;
		System.out.println("文件的大小:"+files[0].getSize());
	    
		json=FileUtil.uplaodMulp(files, request);

		return json;
	}
	
	/**
	 * 得到文件
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="getFiles.do",method=RequestMethod.GET)
	@ResponseBody
	public JSON getFileByArticle(@RequestParam("articleId") Integer articleId){
		JSON json = new JSON();
		List<ArticleFiles> articleFiles=articleFilesService.getFilesByArticleId(articleId);
		if (articleFiles != null){
			json.setDescribe("得到文件");
			json.setObj(articleFiles);
			json.setState(true);
			json.setStateId(JSON.SUCCESS);
		}else{
			json.setDescribe("文件为空");
			json.setState(false);
			json.setStateId(JSON.ERROR);
		}
		return json;
	}
	/**
	 * 下载文件
	 * @param filename
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value="downLoadFiles.do",method=RequestMethod.GET)
	public void downLoadFile(@RequestParam("fileid") Integer fileid,HttpServletRequest request, HttpServletResponse response){
		ServletContext servletContext=request.getServletContext();
		ArticleFiles articleFiles = articleFilesService.getFileById(fileid);
		System.out.println(articleFiles.getAddressurl());
		if (articleFiles != null){
			String filename=articleFiles.getAddressurl();
			String path = servletContext.getRealPath("upload");
			FileUtil.downLoadFile(filename, path, request, response);
		}
		
	}
}
