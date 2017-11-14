<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章详细</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap-treeview.js"></script>

<!-- 引入markdown的插件 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/markdown.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/marked.min.js"></script>
<script>
	function getTree() {
  // Some logic to retrieve, or generate tree structure

 var tree = [
  {
    text: "Parent 1",
    nodes: [
      {
        text: "Child 1"
      },
      {
        text: "Child 2"
      }
    ]
  },
  {
    text: "Parent 2",
    nodes: [
      {
        text: "Child 1"
      },
      {
        text: "Child 2"
      }
    ]
  },
  {
    text: "Parent 3",
    nodes: [
      {
        text: "Child 1"
      },
      {
        text: "Child 2"
      }
    ]
  },
  {
    text: "Parent 4",
    nodes: [
      {
        text: "Child 1"
      },
      {
        text: "Child 2"
      }
    ]
  }
];
  return tree;
}

$(function(){
	$("#tree").treeview({data: getTree()});
	var showlistview=$("#showlistview");
	var showcontentview=$("#showcontentview");
	var dateview=$("#dateview");
	
	var list=0;
	var content=0;
	
	//显示目录视图
	showlistview.click(function(){
		$("#listview").show();
		$("#contentview").hide();
		$("#dateview").hide();
		//添加分页按钮
/* 		if(list==0){
			addPage($("#listview"));
			list=1;
		} */
		
	})
	//显示摘要视图
	showcontentview.click(function(){
		$("#contentview").show();
		$("#listview").hide();
		$("#dateview").hide();
		//添加分页按钮
/* 		if(content==0){
			addPage($("#contentview"));
			content=1;
		} */
	})
	 //$("#contentview").append("<div class='panel panel-danger' style='margin: 10px 10px 0px 10px;'><div class='panel-heading'><h3 class='panel-title'><span class='label label-info' style='margin-right: 10px;margin-top: 2px;'>原创</span>面板标题</h3></div><div class='panel-body'>这是一个基本的面板</div><div style='height: 30px;padding-top: 5px;'><span style='float: right;margin-right: 5px;'>100</span><span style='float: right;' class='glyphicon glyphicon-comment'></span><span style='float: right;margin-right: 10px;'>100</span><span style='float: right;' class='glyphicon glyphicon-eye-open'></span><span style='float: right;margin-right: 10px;'>2017-1-1 15:00:00</span><span class='glyphicon glyphicon-time' style='float: right;'></span></div></div>")
	//添加分页
	function addPage(view){
		view.append("<div style='margin: 5px 10px 5px 10px;height: auto;'><center style='padding-top: 5px;padding-bottom: 5px;'><span class='btn btn-success' style='margin-right: 5px;'>上一页</span><span class='btn btn-default'>下一页</span></center></div>");
	}
	//判断内容是由富文本编辑的还是markdown
	if(${article.editcontenttype}=="1"){
		$("#content").append($.parseHTML("${article.contents}"))
	}else if(${article.editcontenttype}=="2"){
		$("#content").append (marked("${article.contents}")) ;
	}
	//点击执行的函数
	function bgclick(){
		$("span[id^='btnassess-gb-']").click(function(){
			var id=this.id;
			var assessType=id.substring(id.length-1,id.length);
			var articleId=${article.id};
			var assess = {
			articleId : articleId,
			assessType : assessType
			}
					$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/assess/add.do",
					data : JSON.stringify(assess),
					dataType : 'json',
					contentType : 'application/json;charset=utf-8',
					error : function(XMLResponse) {
						alert("错误")
					},
					success : function(data) {
						//alert(data.obj);
						if (data.state) {
							if(assessType==1){
								//$("#good").val(${article.good}+1);
								
								$("#good").text(Number($("#good").text())+Number(1))
							}else if(assessType==2){
								//$("#bad").val(${article.bad}+1);
								$("#bad").text(Number($("#bad").text())+Number(1));
							}
							$("span[id^='btnassess-gb-']").attr("disabled",true);
							$("span[id^='btnassess-gb-']").unbind('click');
						}else{
							alert("失败");
						}
						
					}
				});
		})	
	}
	
	//转载博客
	function Turns(){
		$("#btnassess-turn").click(function(){
			$.getJSON("${pageContext.request.contextPath }/Turns/add.do",function(data){
				alert(data.describe);
				if(data.obj!="2"){
					$("span[id^='btnassess-turn']").attr("disabled",true);
					$("span[id^='btnassess-turn']").unbind("click");
				}
			})
		})
	}
	//收藏
	function Collect(){
		$("#btnassess-collect").click(function(){
			$.getJSON("${pageContext.request.contextPath }/Collections/add.do",function(data){
				alert(data.describe);
				if(data.obj!="2"){
					$("span[id^='btnassess-collect']").attr("disabled",true);
					$("span[id^='btnassess-collect']").unbind("click");
				}
			})
		})
	}
	//判断session中是否有登录信息
	if(${empty userdata.id}){
		$("span[id^='btnassess-']").attr("disabled",true);
	}else{
		$("span[id^='btnassess-']").attr("disabled",false);
		//判断是否评价过
		if(${isAssessGoodAndBad}=="1"){
			$("span[id^='btnassess-gb-']").attr("disabled",true);
		}else{
			bgclick();
		}
		//验证是否转发过
		if(${showTurnsBtn}=="1"){
			$("span[id^='btnassess-turn']").attr("disabled",true);
		}else{
			Turns();
		}
		//验证是否收藏过
		if(${showCollectionsBtn}=="0"){
			$("span[id^='btnassess-collect']").attr("disabled",true);
		}else{
			Collect();
		}	
	}
	//comments
	$.getJSON("${pageContext.request.contextPath }/comments/getByarticleId.do?articleId=${article.id}",function(result){
		if(result.state){
		$("#comments").html("")
			$.each(result.obj,function(i,item){
			var l=Number(i+1);
				$("#comments").append("<div style='margin: 5px 10px 5px 10px;'><div style='background-color: #46B8DA;height: 20px;'class='img-rounded'><span style='margin-left: 10px;'>"+l+"楼</span><span style='margin-left: 10px;'>"+item.usersData.name+"</span><span style='margin-left: 10px;'></span></div><div style='border: 1px solid #cccccc;height: auto;padding-left: 10px;margin-top: 1px;' class='img-rounded'><img src='${pageContext.request.contextPath}/image/"+item.usersData.userimage+"' height='40px' width='40px' style='margin-right: 10px;' /><span>"+item.contents+"</span></div></div>");
			})
		}else{
		$("#comments").html("<center><h3>评论为空<h3></center>")
		}
	})
	//提交评论
	$("#submits").click(function(){
		var comments = $("#context").val();
		var articleId = ${article.id};
		if (comments.length >0 && articleId>0){
			var data = {
				"articleid":articleId,
				"contents":comments
			};
			//发送请求
			 $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${pageContext.request.contextPath }/comments/insertComment.do",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        if(result.state){
                        alert("评论成功");
                        window.location.reload();
                       }else{
                       alert(result.describe);
                       }
                    },
                    error: function(data) {
                        alert("评论失败");
                     }
                });
		}
	})
	$.getJSON("${pageContext.request.contextPath }/File/getFiles.do?articleId=${article.id}",function(result){
		//<li><a href="#">1</a></li>
		if(result.state){
		$.each(result.obj,function(i,item){
			$("#filelist").append("<li><a href='${pageContext.request.contextPath}/File/downLoadFiles.do?fileid="+item.id+"'>"+item.name+"</a></li>")
		})
		
		}
		
	})
})
	</script>
<body>
	<!-- 引用头文件 -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<style>
.list-group-item {
	position: relative;
	display: block;
	padding: 10px 15px;
	margin-bottom: -1px;
	background-color: #FFFFFF;
	border: 0px;
}

.glyphicon {
	position: relative;
	top: 1px;
	display: inline-block;
	font-family: 'Glyphicons Halflings';
	font-style: normal;
	font-weight: 400;
	line-height: 1;
	margin-top: 2px;
	margin-right: 2px;
	-webkit-font-smoothing: antialiased;
}

hr {
	margin-top: 0px;
	margin-bottom: 0px;
	border: 0;
	border-top: 2px solid #CCCCCC;
}

.panel {
	margin-bottom: 10px;
	background-color: #fff;
	border: 1px solid transparent;
	border-radius: 4px;
	-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
}

.label {
	display: inline;
	font-size: 75%;
	font-weight: 700;
	line-height: 1;
	color: #fff;
	text-align: center;
	white-space: nowrap;
	vertical-align: baseline;
	border-radius: .25em;
}
</style>
	<!-- 中间部分 -->
	<div>
		<div class="container" style="height: auto;">
			<div style="width: 100%;height: auto;margin-top: 100px;">
				<!--
	                    	作者：1582722349@qq.com
	                    	时间：2017-09-26
	                    	描述：显示视图的按钮
	                    -->
				<div
					style="background-color: #31B0D5;width: 100%;height: 54px;padding: 10px;"
					class="img-rounded">
					<div class="btn btn-success" style="float: right;"
						id="showcontentview">摘要视图</div>
					<div class="btn btn-success"
						style="float: right;margin-right: 10px;" id="showlistview">目录视图</div>
				</div>
				<!--
	                    	作者：1582722349@qq.com
	                    	时间：2017-09-26
	                    	描述：中间容器左边
	                    -->
				<div id="centerleft" style="width: 22%;height: auto;float: left;"
					class="container">
					<div
						style="width: 100%;height: auto;border: 1px solid #CCCCCC ;margin-top: 10px;"
						class="container  img-rounded">
						<center>
							<div
								style="background-color: #4CAE4C;margin-top: 10px;padding-top: 5px;height: 30px;"
								class="img-rounded">个人资料</div>
							<img
								src="${pageContext.request.contextPath}/image/${detailUsersData.userimage}"
								class="img-circle" height="100px" width="100px"
								style="margin-top: 10px;" />
							<p style="margin-top: 10px;">${detailUsersData.name}</p>
							<p>
								等级:

								<c:forEach var="item" varStatus="status" begin="1"
									end="${detailUsersData.grade}">

									<span class="glyphicon glyphicon-star"></span>

								</c:forEach>


							</p>
						</center>
						<p>
							职业:<span>${occupations.name}</span>
						</p>
						<p>
							经验:<span>${detailUsersData.experience}</span>
						</p>
						<p>
							所有文章:<span>${detailUsersData.articlesall}</span>
						</p>
						<p>
							所有评论:<span>${detailUsersData.acommentsall}</span>
						</p>

					</div>
					<!--
	                        	作者：1582722349@qq.com
	                        	时间：2017-09-26
	                        	描述：显示所以的分类以及文章
	                        -->
<%-- 					<div
						style="width: 100%;height: auto;border: 1px solid #CCCCCC ;margin-top: 10px;"
						class="container img-rounded">
						<center>
							<div
								style="background-color: #4CAE4C;margin-top: 10px;padding-top: 5px;height: 30px;"
								class="img-rounded">所有文章分类</div>
						</center>
						<div id="tree" style="margin-top: 10px;"></div>
					</div> --%>
					<!--
	                        	作者：1582722349@qq.com
	                        	时间：2017-09-26
	                        	描述：相关文档
	                        -->
					<div
						style="width: 100%;height: auto;border: 1px solid #CCCCCC ;margin-top: 10px;margin-bottom: 10px;"
						class="container img-rounded">
						<center>
							<div
								style="background-color: #4CAE4C;margin-top: 10px;padding-top: 5px;height: 30px;"
								class="img-rounded">相关文档</div>
						</center>
						<ul style="margin-top: 10px;" id="filelist">
							
<!-- 							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li> -->
						</ul>
					</div>
				</div>
				<!--
	                    	作者：1582722349@qq.com
	                    	时间：2017-09-26
	                    	描述：中间容器右边的容器
	                    -->
				<div id="centerright" style="width: 78%;height: auto;float: left;">
					<div style="height: auto;width: 100%; ">
						<!--
	                            	作者：1582722349@qq.com
	                            	时间：2017-09-26
	                            	描述：目录视图
	                            -->
						<div id="listview"
							style="display: none;margin-top: 10px;margin-left: 10px;margin-right: 10px;border: 1px solid #CCCCCC;">
							<!--
	                                	作者：1582722349@qq.com
	                                	时间：2017-09-26
	                                	描述：每一个列表
	                                -->
							<!-- 翻页部分 -->
							<c:forEach items="${articelView}" var="item" varStatus="i">
							
 						<c:if test="${i.count%2==0}">
								<hr style='height: 1px;width: 97%;' />
							</c:if> 
								<div class='list-group-item'>
									<span style='float: right;'>100</span> <span
										style='float: right;' class='glyphicon glyphicon-comment'></span>
									<span> <c:choose>
											<c:when test="${item.typeid==1}">
												<span class='label label-info'
													style='margin-right: 10px;margin-top: 2px;'>原创</span>
											</c:when>
											<c:when test="${item.typeid==2}">
												<span class='label label-danger'
													style='margin-right: 10px;margin-top: 2px;'>转载</span>
											</c:when>
											<c:when test="${item.typeid==3}">
												<span class='label label-default'
													style='margin-right: 10px;margin-top: 2px;'>翻译</span>
											</c:when>
										</c:choose> <a href="${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId=${item.id}">${item.title}</a>
									</span> <span style='float: right;margin-right: 10px;'>${item.browses}</span>
									<span style='float: right;'
										class='glyphicon glyphicon-eye-open'></span> <span
										style='float: right;margin-right: 10px;'><fmt:formatDate
											value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss" /> </span>
									<span class='glyphicon glyphicon-time' style='float: right;'></span>
								</div>
								
							</c:forEach>

						</div>

						<!--
	                            	作者：1582722349@qq.com
	                            	时间：2017-09-26
	                            	描述：摘要视图
	                            -->
						<div id="contentview"
							style="display: none;border: 1px solid #CCCCCC;margin: 10px 10px 0px 10px;">
							<!--
	                                	作者：1582722349@qq.com
	                                	时间：2017-09-26
	                                	描述：每个一面板
	                                -->
	                        <c:forEach items="${articelView}" var="item">
	                        	<div class='panel panel-danger' style='margin: 10px 10px 0px 10px;'>
								<div class='panel-heading'>
									<h3 class='panel-title'>
										<c:choose>
											<c:when test="${item.typeid==1}">
												<span class='label label-info'
													style='margin-right: 10px;margin-top: 2px;'>原创</span>
											</c:when>
											<c:when test="${item.typeid==2}">
												<span class='label label-danger'
													style='margin-right: 10px;margin-top: 2px;'>转载</span>
											</c:when>
											<c:when test="${item.typeid==3}">
												<span class='label label-default'
													style='margin-right: 10px;margin-top: 2px;'>翻译</span>
											</c:when>
										</c:choose><a href="${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId=${item.id}">${item.title}</a>
									</h3>
								</div>
								<div class='panel-body'>${item.introduction}</div>
								<div style='height: 30px;padding-top: 5px;'>
									<span style='float: right;margin-right: 5px;'>100</span><span
										style='float: right;' class='glyphicon glyphicon-comment'></span><span
										style='float: right;margin-right: 10px;'>${item.browses}</span><span
										style='float: right;' class='glyphicon glyphicon-eye-open'></span><span
										style='float: right;margin-right: 10px;'><fmt:formatDate
											value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss" /></span><span class='glyphicon glyphicon-time'
										style='float: right;'></span>
								</div>
							</div>
	                        </c:forEach>

						</div>
						<!--
	                            	作者：1582722349@qq.com
	                            	时间：2017-09-26
	                            	描述：详细视图
	                            -->
						<div id="dateview"
							style="margin: 10px 10px 0px 10px;border: 1px solid #CCCCCC;">
							<!--
	                                	作者：1582722349@qq.com
	                                	时间：2017-09-26
	                                	描述：标题栏
	                                -->
							<div style="background-color: #FFFFFF;height: auto;">
								<!--
	                                    	作者：1582722349@qq.com
	                                    	时间：2017-09-26
	                                    	描述：标题 上
	                                    -->
								<div class="list-group-item">
									<h4>
										<!-- 判断文章的类型 -->
										<c:choose>
											<c:when test="${article.typeid==1}">
												<span class="label label-info"
													style="margin-right: 10px;margin-top: 2px;">原创</span>
											</c:when>
											<c:when test="${article.typeid==2}">
												<span class="label label-danger"
													style="margin-right: 10px;margin-top: 2px;">转载</span>
											</c:when>
											<c:when test="${article.typeid==3}">
												<span class="label label-default"
													style="margin-right: 10px;margin-top: 2px;">翻译</span>
											</c:when>
										</c:choose>

										<span style="font-size: 25px">${article.title}</span>
									</h4>
								</div>
								<!--
	                                    	作者：1582722349@qq.com
	                                    	时间：2017-09-26
	                                    	描述：标题 中
	                                    -->
								<div class="list-group-item">
									<span class="glyphicon glyphicon-tags"
										style="margin-right: 10px;margin-left: 10px;"></span> <span
										id="tags"> <!-- 显示标签 --> <c:set
											value="${fn:split(article.labels, ' ')}" var="names" /> <c:forEach
											items="${ names }" var="name">
											<span style="margin-left: 5px;">${name}</span>
										</c:forEach>
									</span>
								</div>
								<!--
	                                    	作者：1582722349@qq.com
	                                    	时间：2017-09-26
	                                    	描述：标题 下
	                                    -->
								<div style="height: 30px;padding-top: 5px;">
									<span style="float: right;margin-right: 10px;"
										class="glyphicon glyphicon-share"></span> <span
										style="float: right;margin-right: 10px;">${commentsAll}</span><span
										style="float: right;" class="glyphicon glyphicon-comment"></span>
									<span style="float: right;margin-right: 10px;">${article.browses}</span><span
										style="float: right;" class="glyphicon glyphicon-eye-open"></span>
									<span style="float: right;margin-right: 10px;"><fmt:formatDate
											value="${article.createtime}" pattern="yyyy-MM-dd" /></span><span
										class="glyphicon glyphicon-time" style="float: right;"></span>
								</div>
							</div>
							<hr />
							<!--
	                                	作者：1582722349@qq.com
	                                	时间：2017-09-26
	                                	描述：内容
	                                -->
							<div id="content" style="background-color: #FFFFFF;"></div>
							<hr />
							<!--
	                                	作者：1582722349@qq.com
	                                	时间：2017-09-26
	                                	描述：赞，踩，上下篇
	                                -->
							<div
								style="background-color: #FFFFFF;height: auto;padding-bottom: 10px;">
								<!--
	                                    	作者：1582722349@qq.com
	                                    	时间：2017-09-26
	                                    	描述：赞或者踩
	                                    -->
								<center>
									<div style="padding-top: 80px;">
										<span class="btn btn-success" disabled="disabled"
											id="btnassess-gb-1" style="height: 50px;width: 50px;">
											<span class="glyphicon glyphicon-thumbs-up"></span>
											<p id="good">${article.good}</p>
										</span> <span class="btn btn-default" disabled="disabled"
											id="btnassess-gb-2" style="height: 50px;width: 50px;">
											<span class="glyphicon glyphicon-thumbs-down"></span>
											<p id="bad">${article.bad}</p>
										</span> <span class="btn btn-info" disabled="disabled"
											id="btnassess-turn" style="height: 50px;width: 50px;">
											<span class="glyphicon glyphicon-share"></span>
											<p id="turn">转</p>
										</span> <span class="btn btn-danger" disabled="disabled"
											id="btnassess-collect" style="height: 50px;width: 50px;">
											<span class="glyphicon glyphicon-star-empty"></span><br />
											<p id="collect">藏</p>
										</span>
									</div>
								</center>
								<!--
	                                    	作者：1582722349@qq.com
	                                    	时间：2017-09-26
	                                    	描述：上下篇
	                                    -->
								<div style="margin-top: 50px;">
								<c:forEach items="${articelView}" var="item" varStatus="i">
									<c:if test="${article.id-1==item.id}">
										<div style="margin: 10px;">
										<a href="${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId=${item.id}"><span class="btn btn-info"
											style="height: 30px;width: auto;margin-right: 10px;">上一篇</span><span>${item.title}</span></a></div>
									</c:if>
									<c:if test="${article.id+1==item.id}">
										<div style="margin: 10px;">
										<a href="${pageContext.request.contextPath }/DetailedArticel/getArticle.do?articleId=${item.id}"><span class="btn btn-primary"
											style="height: 30px;width: auto;margin-right: 10px;">下一篇</span><span>${item.title}</span></a></div>
									</c:if>
								</c:forEach>
								</div>
							</div>
							<hr />
							<!--
	                                	作者：1582722349@qq.com
	                                	时间：2017-09-26
	                                	描述：评论区
	                                -->
							<div style="background-color: #FFFFFF;padding-bottom: 10px;">
								<div
									style="height: 30px;background-color: #31B0D5;padding-top: 5px;padding-left: 10px;"
									class="img-rounded">
									<span>评论</span>
								</div>
								<div id="comments" style="border: 1 solid #CCCCCC;">
									<!--
	                                        	作者：1582722349@qq.com
	                                        	时间：2017-09-26
	                                        	描述：每个单独的评论
	                                        -->
								</div>
<%-- 								<div style="margin: 5px 10px 5px 10px;height: auto;">
									<center style="padding-top: 5px;padding-bottom: 5px;">
										<span class="btn btn-success" style="margin-right: 5px;">上一页</span><span
											class="btn btn-default">下一页</span>
									</center>
								</div> --%>
								<div
									style="height: 30px;background-color: #31B0D5;padding-top: 5px;padding-left: 10px;"
									class="img-rounded">
									<span>发表评论</span>
								</div>
								<div id="commentContext">
								<c:if test="${userdata==null}">
									<center><h3>先登录才能评论哦</h3></center>
								</c:if>
								<c:if test="${userdata!=null}">
									<center>
										<div style="margin-top: 10px;margin-bottom: 10px;">
											<span style="margin-right: 10px;">用户名:</span><span>${userdata.name}</span>
										</div>
										<div>
											<textarea rows="5" style="width: 90%;" id="context"></textarea>
											<br /> <br /> <input type="button" value="提交" id="submits"
												class="btn btn-success" />
										</div>
									</center>
								</c:if>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- 引用尾部文件 -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>