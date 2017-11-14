package com.ssm.until;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.entity.JSON;
import com.ssm.entity.UsersData;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件工具类
 * Created by vison on 2017/4/23.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 将图片到byte数组
     * @param path 文件路径
     * @return
     */
    public  byte[] image2byte(String path){
             byte[] data = null;
           //  FileImageInputStream input = null;
              InputStream  input = null;
             try {
                 //input = new FileImageInputStream(new File(path));
                input = getClass().getResourceAsStream(path);//图片在项目中的位置

                 ByteArrayOutputStream output = new ByteArrayOutputStream();
                   byte[] buf = new byte[1024];
                   int numBytesRead = 0;
                   while ((numBytesRead = input.read(buf)) != -1) {
                       output.write(buf, 0, numBytesRead);
                       }
                   data = output.toByteArray();
                   output.close();
                   input.close();
                 }
             catch (FileNotFoundException ex1) {
                   ex1.printStackTrace();
                }
             catch (IOException ex1) {
                   ex1.printStackTrace();
                 }
             return data;
           }


        /**
         * 将图片到byte数组
         * @param
         * @return 图片数组
         */
        public static byte[] image2byte(InputStream input){
            byte[] data = null;

            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int numBytesRead = 0;
                while ((numBytesRead = input.read(buf)) != -1) {
                    output.write(buf, 0, numBytesRead);
                }
                data = output.toByteArray();
                output.close();
                input.close();
            }
            catch (FileNotFoundException ex1) {
                ex1.printStackTrace();
            }
            catch (IOException ex1) {
                ex1.printStackTrace();
            }
            return data;
        }

    /**
     * byte数组到图片
     * @param data 需要转换的byte数据
     * @param path 保存的图片路径
     */
    public static void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
            } catch(Exception ex) {
                 System.out.println("Exception: " + ex);
                 ex.printStackTrace();
            }
    }


    /**
     * 文件上传
     * @param file 上传的文件
     * @param path  文件上传的路径
     * @return 文件上传的结果
     */
    public static JSON fileUpLoad(MultipartFile file,String path){
        JSON json = new JSON();
        if(file.isEmpty()){
            json.setDescribe("您上传的文件是空的！");
            json.setState(false);
            return json;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);

        File dest = new File(path+fileName);
        logger.info("文件保存的位置：{}",dest.toPath());

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            logger.error("Exception:{}",e);
            json.setDescribe("上传失败，请检查您上传的文件是否符合要求");
            json.setState(false);
            return json;
        }
        json.setState(true);
        json.setDescribe("文件上传成功");
        return json;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static JSON deleteFile(String fileName,String oldDir) {
        JSON json = new JSON();
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            //将这个文件转移到另一个文件夹
            // 构建目标文件
            File fileCopy = new File(oldDir);
            try {
                copyFile(file,fileCopy);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file.delete()) {
              // System.out.println("删除单个文件" + fileName + "成功！");
                logger.info("删除文件" + fileName + "成功！");
                json.setState(true);
                json.setDescribe("删除文件" + fileName + "成功！");
                return json;
            } else {
                //System.out.println("删除单个文件" + fileName + "失败！");
                logger.info("删除文件" + fileName + "失败！");
                json.setDescribe("删除文件" + fileName + "失败！");
                json.setState(false);
                return json;
            }
        } else {
            //System.out.println("删除单个文件失败：" + fileName + "不存在！");
            logger.info("删除文件" + fileName + "不存在！");
            json.setDescribe("删除文件" + fileName + "不存在！");
            json.setState(false);
            return json;
        }
    }

    /**
     * 获得某个文件夹下的所有文件
     * @param dirPath 文件所在的位置
     * @return 该路径下的所有文件
     */
    public static List<String> getDirectoryFile(String dirPath){
        List<String> filesPath = new ArrayList<String>();
        File file=new File(dirPath);
        if (!file.isDirectory()){
            logger.info("该路径不是一个文件夹");
            return null;
        }
        File[] tempList = file.listFiles();
        if (tempList.length == 0){
            logger.info("该文件夹没有任何文件");
            return  null;
        }
        logger.info("该目录下对象个数："+tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                logger.info("文件路径：{}",tempList[i].toPath());
                String filePath = tempList[i].toPath().toString();
                filesPath.add(filePath);
            }
        }
        return filesPath;
    }

    /**
     * 获得文件的父目录的路径
     * @param filePath 文件路径
     * @return
     */
    public static String  getParentPath(String filePath){
        File file = new File(filePath);
        String parentPath = file.getParent();
        logger.info("parentPath:{}",parentPath);
        return parentPath;
    }


    /**
     * 复制文件
     * @param source 需要复制的文件
     * @param dest  复制到的地址
     * @throws IOException
     */
    public static void copyFile(File source, File dest)
            throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    /**
     * 上传图片文件限制
     * @param file
     * @return
     */
    public static JSON LimitUpLoadImage(MultipartFile file){
        JSON json=new JSON();
        logger.info("上传文件的大小:{}",file.getSize());
        logger.info("上传文件的类型:{}",file.getContentType());
        if (file.isEmpty()){
            json.setState(false);
            json.setDescribe("上传文件为空");
            return json;
        }
        if (!file.getContentType().equals("image/png")){
            json.setState(false);
            json.setDescribe("请上传.png,.jpg,.jpeg的图片");
            return json;
        }
        if (file.getSize()<0||file.getSize()>204800){
            json.setState(false);
            json.setDescribe("请上传小于200kb的头像");
            return json;
        }
        return json;
    }
    
    /**
     * 上传多个文件
     * @param request
     * @param files
     * @return
     */
    public static JSON uplaodMulp(CommonsMultipartFile files[],HttpServletRequest request){
    	JSON json=new JSON();
    	//获取项目的路径
    			ServletContext servletContext=request.getServletContext();
    			//获取用户的id
    			String userId=String.valueOf(((UsersData)request.getSession().getAttribute("userdata")).getId());
    			//获取文件的存储路径
    			String path=servletContext.getRealPath("upload")+"/"+userId+"/";
    		    File f = new File(path);  
    		    //创建文件夹
    		    if (!f.exists())  
    		        f.mkdirs();  
    		    for (int i = 0; i < files.length; i++) {  
    		        // 获得原始文件名  
    		        String fileName = files[i].getOriginalFilename();  
    		        System.out.println("原始文件名:" + fileName);
    		        Date date=new Date();
    		        // 新文件名  
    		        String newFileName =String.valueOf(date.getTime())+"@"+fileName;  
    		        if (!files[i].isEmpty()) {  
    		            try {  
    		                FileOutputStream fos = new FileOutputStream(path  
    		                        + newFileName);  
    		                InputStream in = files[i].getInputStream();  
    		                int b = 0;  
    		                while ((b = in.read()) != -1) {  
    		                    fos.write(b);  
    		                }  
    		                fos.close();  
    		                in.close();
    		            } catch (Exception e) {  
    		                e.printStackTrace();
    		    		    json.setDescribe("上传文件失败");
    		    		    json.setState(false);
    		    		    json.setStateId(JSON.ERROR);
    		            }  
    		        }  
    		        System.out.println("文件上传到:" + path + newFileName);  
    		    }
    		    json.setDescribe("上传文件成功");
    		    json.setState(true);
    		    json.setStateId(JSON.SUCCESS);
    	return json;
    }
    /**
     * 下载文件
     * @param filename
     * @param response
     */
    public static void downLoadFile(String filename,String realPath, HttpServletRequest request, HttpServletResponse response){
        File file = new File(realPath, filename);
        System.out.println("文件路径:"+file.getAbsolutePath());
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            try {
                filename = URLEncoder.encode(filename, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + filename.substring(17,filename.length()));// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }else {
            System.out.println("文件不存在");
        }
    }
}
