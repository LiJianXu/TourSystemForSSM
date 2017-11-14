<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title>个人中心</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
.mybtn {
	display: inline-block;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	margin-right: 6px;
	padding: 0px 5px 5px 5px;
}

.parent {
	width: 100%;
	height: 38px;
	margin: 0 auto;
	text-align: center;
	margin-top: 10px;
	margin-bottom: 10px;
}

.children {
	display: inline-block;
	width: auto;
	height: 38px;
}
</style>
<!-- 引用分页的css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/pagination.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script>
	$(function() { //翻页部分
	var pageCount = 5;
	var cupage=0;
	var start1=true;
	var start2=true;
		init(0);
		init2(0);
		
		$("#saveuserdate").click(function() {
			var editname = $("#editname").val();
			var occupations = $("#occupations").val();
			var email = $("#email").val();
			var phone = $("#phone").val();
			var introduction = $("#introduction").val();
			var data = {
				name : editname,
				occupationsname : occupations,
				email : email,
				phone : phone,
				introduction : introduction
			}
			if (editname.trim().length > 0 && occupations.trim().length > 0 && email.trim().length > 0 && phone.trim().length > 0 && introduction.trim().length > 0) {
				//发送请求
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath }/UserData/updateUserDate.do",
					data : data,
					success : function(result) {
						if (result.state) {
							alert("修改成功");
							window.location.reload();
						} else {
							alert(result.describe);
						}
					},
					error : function(data) {
						alert("修改失败");
					}
				});
			}
		});
		
		//添加每个文章
		function addArticle(item,type){
			if(type==1){
				$("#blogslist").append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='article"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span><span><span class='label label-info' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span><a href='${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId="+item.id+"'>"+item.title+"</a></span> <span style='float: right;margin-right: 10px;'>"+item.createtime+"</span><span class='glyphicon glyphicon-time' style='float: right;margin-top: 2px;margin-right: 5px;'></span></div>");
			}else if(type==2){
				$("#blogslist").append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='article"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span><span><span class='label label-danger' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span><a href='${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId="+item.id+"'>"+item.title+"</a></span> <span style='float: right;margin-right: 10px;'>"+item.createtime+"</span><span class='glyphicon glyphicon-time' style='float: right;margin-top: 2px;margin-right: 5px;'></span></div>");
			}else if(type==3){
				$("#blogslist").append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='article"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span><span><span class='label label-warning' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span><a href='${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId="+item.id+"'>"+item.title+"</a></span> <span style='float: right;margin-right: 10px;'>"+item.createtime+"</span><span class='glyphicon glyphicon-time' style='float: right;margin-top: 2px;margin-right: 5px;'></span></div>");
			}
			//绑定单击事件
			$("#article"+item.id).on("click",function(){
 			$("#example").modal()
 				var article=this.id;
				$("#shure").attr("name",article);
			})
		}
		$("#shure").click(function(){
		//得到模特框中的name
		var name=$("#shure").attr("name");
		//判断类型
		if(name.substring(0,7)=="article"){
		deleteBogs(name); 
		}else if(name.substring(0,7)=="collect"){
		deleteCollections(name)
		}else if(name.substring(0,5)=="files"){
		deleteFiles(name)
		}
		
		})
		//定义回调函数
        function PageCallback(index, jq) {
	       init(index.getCurrent());
        };
        //初始化翻页
		function init(pageIndex){
			$("#blogslist").html("");
			$.getJSON("${pageContext.request.contextPath }/user/getBlogslist.do?userDataId="+${userdata.id}+"&page="+(pageIndex)+"&size=10",function(result){
					$.each(result.list,function(i,item){
						addArticle(item,item.articleType.id)
					})
					pageCount=result.pages;
					if(start1){
						start1=false;
						$('.M-box3').pagination({
						pageCount : pageCount,
						prevContent : '上页',
						nextContent : '下页',
						current_page: cupage,   //当前页索引
						callback: PageCallback
						});
					}
			});
		}
		//初始化翻页
		function init2(pageIndex){
			$("#collectlist").html("");
			$.getJSON("${pageContext.request.contextPath }/user/getCollectionslist.do?userDataId=${userdata.id}&page="+pageIndex+"&size=5",function(result){
			$.each(result.list,function(i,item){
			addCollections(item,item.articleType.id)
			})
			if(start2){
			start2=false;
			$('.M-box4').pagination({
			pageCount : result.pages,
			jump : true,
			coping : true,
			homePage : '首页',
			endPage : '末页',
			prevContent : '上页',
			nextContent : '下页',
			current_page: result.pageNum
		});
			}
			
		})
		}
		//删除博客
		function deleteBogs(article){
			var id=article.substring("7",article.length);
			$.getJSON("${pageContext.request.contextPath }/user/deleteById.do?articleId="+id,function(result){
			$("#example").modal("hide");
			if(result.state){
				window.location.reload();
			}else{
				alert("删除失败")
			}
			});
		}
		//删除收藏
		function deleteCollections(collect){
		var id=collect.substring("7",collect.length);
 			$.getJSON("${pageContext.request.contextPath }/user/deleteCollectionsById.do?id="+id,function(result){
			$("#example").modal("hide");
			if(result.state){
				window.location.reload();
			}else{
				alert("删除失败")
			}
			});
		}
		//删除文件
		function deleteFiles(files){
		var id=files.substring(5,files.length);
		//开始写删除文件
		$.getJSON("${pageContext.request.contextPath }/user/deleteFileId.do?fileId="+id,function(result){
			$("#example").modal("hide");
			if(result.state){
				window.location.reload();
			}else{
				alert("删除失败")
			}
			});
		}
		//添加每个收藏
		function addCollections(item,type){
			if(type==1){
				$("#collectlist").append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='collect"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span> <span><span class='label label-info' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span><a href='${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId="+item.articleid+"'>"+item.articletitle+"</a></span> <span style='float: right;margin-right: 10px;'>2017-1-1 15:00:00</span><span class='glyphicon glyphicon-time' style='float: right;margin-top: 2px;margin-right: 5px;'></span></div>");
			}else if(type==2){
				$("#collectlist").append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='collect"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span> <span><span class='label label-danger' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span><a href='${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId="+item.articleid+"'>"+item.articletitle+"</a></span> <span style='float: right;margin-right: 10px;'>2017-1-1 15:00:00</span><span class='glyphicon glyphicon-time' style='float: right;margin-top: 2px;margin-right: 5px;'></span></div>");
			}else if(type==3){
				$("#collectlist").append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='collect"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span> <span><span class='label label-warning' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span><a href='${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId="+item.articleid+"'>"+item.articletitle+"</a></span> <span style='float: right;margin-right: 10px;'>2017-1-1 15:00:00</span><span class='glyphicon glyphicon-time' style='float: right;margin-top: 2px;margin-right: 5px;'></span></div>");
			}
			//绑定单击事件
			$("#collect"+item.id).on("click",function(){
 			$("#example").modal()
 				var collect=this.id;
				$("#shure").attr("name",collect);
			})
		}
		$.getJSON("${pageContext.request.contextPath }/user/getFilesGroupId.do",function(result){
			$.each(result,function(i,item){
				var father =addFileTitle(i,item);
				addArticleFile(father,item.id);
			})
		})
		//添加文件的标题
		function addFileTitle(i,item){
		if(item.articleType.id =="1"){
			$("#filesList").append("<div class='panel panel-primary' style='margin: 10px 10px 0px 10px;' ><div class='panel-heading'><h3 class='panel-title'><span class='label label-info' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span>"+item.title+"<span style='float: right;margin-top: 2px;'>"+item.createtime+"</span></h3></div><div style='height: auto;padding:5px' id='filesList"+i+"'></div></div>");
		}else if(item.articleType.id =="2"){
			$("#filesList").append("<div class='panel panel-primary' style='margin: 10px 10px 0px 10px;' ><div class='panel-heading'><h3 class='panel-title'><span class='label label-danger' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span>"+item.title+"<span style='float: right;margin-top: 2px;'>"+item.createtime+"</span></h3></div><div style='height: auto;padding:5px' id='filesList"+i+"'></div></div>");
		}else if(item.articleType.id =="3"){
			$("#filesList").append("<div class='panel panel-primary' style='margin: 10px 10px 0px 10px;' ><div class='panel-heading'><h3 class='panel-title'><span class='label label-warning' style='margin-right: 10px;margin-top: 2px;'>"+item.articleType.name+"</span>"+item.title+"<span style='float: right;margin-top: 2px;'>"+item.createtime+"</span></h3></div><div style='height: auto;padding:5px' id='filesList"+i+"'></div></div>");
		}
		return $("#filesList"+i);
		}
		
		//添加文件
		function addArticleFile(father,articleId){
		$.getJSON("${pageContext.request.contextPath}/user/getFiles.do?courseId="+articleId,function(result){
			$.each(result,function(i,item){
				father.append("<div class='list-group-item' style='margin-bottom: 5px;'><span style='float: right;height: 20px;' class='mybtn btn-danger' id='files"+item.id+"'><span class='glyphicon glyphicon-trash'></span></span> <span style='float: left;margin-top: 2px;' class='glyphicon glyphicon-download-alt'></span><span><span style='float: left;margin-right: 10px;'>"+item.grade+"</span><a href='${pageContext.request.contextPath}/File/downLoadFiles.do?fileid="+item.id+"'>"+item.name+"</a></span></span></div>")
			//绑定单击事件
			$("#files"+item.id).on("click",function(){
 			$("#example").modal()
 				var files=this.id;
				$("#shure").attr("name",files);
			})
			})
			
		})
		}
 	})
</script>
<body>
	<!-- 引用头文件 -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	
	<!--
	        	作者：offline
	        	时间：2017-09-18
	        	描述：中间的容器
	        -->
	<div style="height: 1000px;">
		<!--
                	作者：1582722349@qq.com
                	时间：2017-09-28
                	描述：个人信息部分
                -->
		<div class="container">
			<div
				style="background-color: #FFFFFF;height: 170px;padding: 10px;margin-top: 100px;border: 1px dashed #000000;"
				class="form-group img-rounded">
				<div class="col-md-2" style="border-right: 1px dashed #000000;">
					<img
						src="${pageContext.request.contextPath}/image/${userdata.userimage}"
						style="float: left;height: 150px;width: 150px;" class="img-circle" />
				</div>
				<div style="float: left;height: 190px;" class="col-md-10">
					<div style="height: 50px;">
						<span style="float: left;margin-top: 20px;margin-left: 10px;"><strong>${userdata.name}</strong></span>
						<span class="glyphicon glyphicon-user"
							style="float: left;margin-top: 22px;margin-left: 100px;"></span>
						<span style="float: left;margin-top: 20px;margin-left: 5px;">${userdata.occupations.name}</span>
						<c:if test="${not empty users.email}">
							<span class="glyphicon glyphicon-envelope"
								style="float: left;margin-top: 22px;margin-left: 10px;"></span>
							<span style="float: left;margin-top: 20px;margin-left: 2px;">${users.email}</span>
						</c:if>
						<c:if test="${not empty users.phone}">
							<span class="glyphicon glyphicon-phone"
								style="float: left;margin-top: 22px;margin-left: 10px;"></span>
							<span style="float: left;margin-top: 20px;margin-left: 2px;">${users.phone}</span>
						</c:if>

						<!--
                                	作者：1582722349@qq.com
                                	时间：2017-09-28
                                	描述：修改信息
                               -->
						<a href="#" data-toggle="modal" data-target="#editModal"><span
							class="glyphicon glyphicon-edit"
							style="margin-top: 22px;float: right;margin-right: 10px;"></span></a>

					</div>
					<div style="border: 1px dashed #000000;padding: 10px;">${userdata.introduction}</div>
					<div style="margin-top: 20px;">
						<span style="float: left;margin-right: 20px;text-align: center;">总文章数<br />${userdata.articlesall}
						</span> <span style="float: left;margin-right: 20px;text-align: center;">经验<br />${userdata.experience}
						</span> <span style="float: left;margin-right: 20px;text-align: center;">等级<br />
							<c:if test="${userdata.grade gt 0}">
								<c:forEach var="i" begin="0" end="${userdata.grade}" step="1">
									<span class="glyphicon glyphicon-star"></span>
								</c:forEach>
							</c:if>
						</span>
					</div>

				</div>
			</div>
		</div>
		<!--
                	作者：1582722349@qq.com
                	时间：2017-09-28
                	描述：博客管理和收藏管理
                -->
		<div class="container">
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#blogs" data-toggle="tab"> 博客管理
				</a></li>
				<li><a href="#collect" data-toggle="tab">收藏管理</a></li>
				<li><a href="#files" data-toggle="tab">文件管理</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<!--
                        	作者：1582722349@qq.com
                        	时间：2017-09-28
                        	描述：博客管理部分
                        -->
				<div class="tab-pane fade in active" id="blogs">
					<div style="margin-top: 10px;height: 600px;" id="blogslist">
						
					</div>
					<!--
		                    	作者：offline
		                    	时间：2017-09-18
		                    	描述：翻页的容器
		                    -->
					<div class="container" id="blogs_big_content_pages"
						style="width: 100%;height: auto;background-color: #FFFFFF;margin-top: 10px">

						<div class="parent">
							<div class="M-box3 children"></div>
						</div>
					</div>
				</div>
				<!--
                       	作者：1582722349@qq.com
                       	时间：2017-09-28
                       	描述：博客收藏部分
                       -->
				<div class="tab-pane fade" id="collect">
					<div style="margin-top: 10px;height: 600px;" id="collectlist">
						
					</div>
					<!--
		                    	作者：offline
		                    	时间：2017-09-18
		                    	描述：翻页的容器
		                    -->
					<div class="container" id="collect_big_content_pages"
						style="width: 100%;height: auto;background-color: #FFFFFF;margin-top: 10px">

						<div class="parent">
							<div class="M-box4 children"></div>
						</div>
					</div>
				</div>
				<!--
                       	作者：1582722349@qq.com
                       	时间：2017-09-28
                       	描述：文件的管理
                       -->
				<div class="tab-pane fade" id="files">
					<div style="height: 600px;" id="filesList">
						<!--
                                   	作者：1582722349@qq.com
                                   	时间：2017-09-28
                                   	描述: 第一个文件面板
                                   -->
					<!-- <div class="panel panel-primary"
							style="margin: 10px 10px 0px 10px;">
							<div class="panel-heading">
								<h3 class="panel-title">
									<span class="label label-info"
										style="margin-right: 10px;margin-top: 2px;">原创</span>面板标题
								</h3>
							</div>
							<div style="height: auto;padding:5px">
								<div class="list-group-item" style="margin-bottom: 5px;">
									<span style="float: right;height: 20px;"
										class="mybtn btn-danger"><span
										class="glyphicon glyphicon-trash"></span></span> <span
										style="float: left;margin-top: 2px;"
										class="glyphicon glyphicon-download-alt"></span><span><span
										style="float: left;margin-right: 10px;">100</span><a>标题</a></span> <span
										style="float: right;margin-right: 10px;">2017-1-1
										15:00:00</span><span class="glyphicon glyphicon-time"
										style="float: right;margin-top: 2px;margin-right: 5px;"></span>
									
								</div>
								<div class="list-group-item" style="margin-bottom: 5px;">
									<span style="float: right;height: 20px;"
										class="mybtn btn-danger"><span
										class="glyphicon glyphicon-trash"></span></span> <span
										style="float: left;margin-top: 2px;"
										class="glyphicon glyphicon-download-alt"></span><span><span
										style="float: left;margin-right: 10px;">100</span><a>标题</a></span> <span
										style="float: right;margin-right: 10px;">2017-1-1
										15:00:00</span><span class="glyphicon glyphicon-time"
										style="float: right;margin-top: 2px;margin-right: 5px;"></span>
									
								</div>
							</div>
						</div> -->
						<!--
                                   	作者：1582722349@qq.com
                                   	时间：2017-09-28
                                   	描述: 第二个文件面板
                                   -->
						<!-- <div class="panel panel-primary"
							style="margin: 10px 10px 0px 10px;">
							<div class="panel-heading">
								<h3 class="panel-title">
									<span class="label label-info"
										style="margin-right: 10px;margin-top: 2px;">原创</span>面板标题
								</h3>
							</div>
							<div style="height: auto;padding:5px">
								<div class="list-group-item" style="margin-bottom: 5px;">
									<span style="float: right;height: 20px;"
										class="mybtn btn-danger"><span
										class="glyphicon glyphicon-trash"></span></span> <span
										style="float: left;margin-top: 2px;"
										class="glyphicon glyphicon-download-alt"></span><span><span
										style="float: left;margin-right: 10px;">100</span><a>标题</a></span> <span
										style="float: right;margin-right: 10px;">2017-1-1
										15:00:00</span><span class="glyphicon glyphicon-time"
										style="float: right;margin-top: 2px;margin-right: 5px;"></span>
									
								</div>
								<div class="list-group-item" style="margin-bottom: 5px;">
									<span style="float: right;height: 20px;"
										class="mybtn btn-danger"><span
										class="glyphicon glyphicon-trash"></span></span> <span
										style="float: left;margin-top: 2px;"
										class="glyphicon glyphicon-download-alt"></span><span><span
										style="float: left;margin-right: 10px;">100</span><a>标题</a></span> <span
										style="float: right;margin-right: 10px;">2017-1-1
										15:00:00</span><span class="glyphicon glyphicon-time"
										style="float: right;margin-top: 2px;margin-right: 5px;"></span>
									
								</div>
							</div>
						</div> -->
					</div>
					<!--
		                    	作者：offline
		                    	时间：2017-09-18
		                    	描述：翻页的容器
		                    -->
					<div class="container" id="collect_big_content_pages"
						style="width: 100%;height: auto;background-color: #FFFFFF;margin-top: 10px;">
						<div class="parent">
							<div class="M-box2 children"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改用户信息</h4>
				</div>
				<div class="modal-body" style="height: 300px;">
					<div class="form-horizontal" role="form" style="margin-top: 20px;"
						id="edit_form">
						<div class="form-group">
							<label class="col-sm-1" style="margin-top: -5px;">昵称</label>
							<div class="col-sm-5">
								<input type="text" class="form-control"
									placeholder="${userdata.name}" name="name" id="editname" />
							</div>
							<label class="col-sm-1" style="margin-top: -5px;">职业</label>
							<div class="col-sm-5">
								<input type="text" class="form-control"
									placeholder="${userdata.occupations.name}" name="occupations"
									id="occupations" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1" style="margin-top: -5px;">邮箱</label>
							<div class="col-sm-5">
								<input type="text" class="form-control"
									placeholder="${users.email}" name="email" id="email" />
							</div>
							<label class="col-sm-1" style="margin-top: -5px;">电话</label>
							<div class="col-sm-5">
								<input type="text" class="form-control"
									placeholder="${users.phone}" name="phone" id="phone" />
							</div>
						</div>
						<div class="form-group" class="container">
							<div style="margin-left: 15px;">
								<label>简介</label>
							</div>
							<div>
								<center>
									<textarea rows="5" style="width: 90%;"
										placeholder="${userdata.introduction}" id="introduction"></textarea>
								</center>

							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="saveuserdate">保存</button>
					</center>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
<div class="modal fade" id="example" tabindex="-1" role="dialog"
		aria-labelledby="example" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
            <a class="close" data-dismiss="modal">×</a>
            <h3>确认删除？</h3>
        </div>
        <div class="modal-body">
            <h4>删除无法恢复哦</h4>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-success" id="shure">确认删除</a>
            <a href="#" class="btn" data-dismiss="modal">关闭</a>
        </div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	
	<!-- 引用尾部文件 -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>
