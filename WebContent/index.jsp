<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>

<!-- 引用验证框架的css -->
<link
	href="${pageContext.request.contextPath }/css/bootstrapValidator.min.css" />
<!-- 引用分页的css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/pagination.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<style>
.row {
	margin-right: 0px;
	margin-left: 0px;
}

.spanleft {
	float: left;
	margin: 5px 10px 5px 10px;
}

.spanright {
	float: right;
	margin: 5px 10px 5px 10px;
}

.spanrighteye {
	float: right;
	margin: 6px -5px 5px 10px;
}

.parent {
	width: 100%;
	height: 38px;
	margin: 0 auto;
	text-align: center;
	margin-top: 10px;
}

.children {
	display: inline-block;
	width: auto;
	height: 38px;
}

*+html .children {
	display: inline;
	zoom: 1;
}

* html .children {
	display: inline;
	zoom: 1;
}
</style>

<script type="text/javascript">
		$.getJSON(
			"${pageContext.request.contextPath}/category/get_categorys.do",
			function(data) {
				if(data.state){
					$("#categorysList").html("");
					$.each(data.obj,function(i,item){
						$("#categorysList").append("<li><a href='javascript:void(0)' id='category"+item.id+"'>"+item.name+"</a></li>")
						$("#category"+item.id+"").on("click",function(){
						var id=item.id;
							InitPage(id, 1);
						})
					})
				}else{
					alert(data.describe)
				}
			});
		//初始化分页
		InitPage(1, 1);
		
		//开始分页和分类加载博客
		function InitPage(categoryId, page) {
			$("#left_big_content_articles").html("");
			$.getJSON("${pageContext.request.contextPath}/DetailedArticel/getArticlesByCategory.do?categoryId=" + categoryId + "&page=" + page, function(data) {
				//动态修改页数
				$(".M-box").pagination({
					pageCount : data.pages,
					jump : true,
					coping : true,
					homePage : '首页',
					endPage : '末页',
					prevContent : '上页',
					nextContent : '下页',
					current : data.pageNum,
					callback : function(index) {
						InitPage(categoryId, index.getCurrent());
					}
				});
				if (data.pages > "0") {
					$(".M-box").css("display", "")
				}
				//开始解析返回的结果
				$.each(data.list, function(i, n) {
					$.getJSON("${pageContext.request.contextPath}/UserData/getUserData.do?userdataId=" + n.userdateid, function(data) {
						//alert(data.userimage+data.name
						$("#left_big_content_articles").append("<div class='row img-rounded' " +
							"style='background-color: #FFFFFF; width: 100%; height: 200px; border: groove #CCCCCC 1px; margin-top: 5px;'>" +
							"<div class='col-md-2'" +
							"style='float: left; height: 100%; padding-top: 30px; padding-left: 0px; padding-right: 0px;'>" +
							"<center>" +
							"<img src='image/" + data.userimage + "' class='img-circle' height='100px'" +
							"width='100px' />" +
							"<p style='margin-top: 10px;'>" + data.name + "</p>" +
							"</center>" +

							"</div>" +
							"<div class='col-md-10'" +
							"style='float: right; height: 100%; padding-left: 0px; padding-right: 0px; padding-top: 10px;'>" +
							"<div style='border-left: groove #CCCCCC 1px; padding-left: 10px;'>" +
							"<a href='${pageContext.request.contextPath}/DetailedArticel/getArticle.do?articleId=" + n.id + "'><h3>" + n.title + "</h3></a>" +
							"<div " +
							"style='width: 100%; background-color: #FFFFFF; height: 60px; margin-bottom: 10px;'>" + n.introduction + "</div>" +
							"<div " +
							"style='width: 100%; background-color: #FFFFFF; height: 30px;'>" +
							"<span class='spanleft'>" + n.labels + "</span>" +
							"<span class='spanright'>" + n.browses + "</span><span class='spanrighteye'><span" +
							" class='glyphicon glyphicon-eye-open'></span></span><span" +
							" class='spanright'>" + n.updatetime + "</span>" +
							"</div>" +
							"</div>" +

							"</div>" +
							"</div>");
					})

				});
			})
		}
		$.getJSON("${pageContext.request.contextPath}/UserData/byExperienceDesc.do", function(data) {
			$.each(data.obj, function(i, n) {
				//alert(n.name)
				$("#right_big_content_hot").append("<div class='img-rounded' " +
					"style='width: 100%; height: 280px; border: groove #CCCCCC 1px; margin-top: 10px;'>" +
					"<div style='width: 100%; height: 100%; padding-top: 30px;'>" +
					"<center>" +
					"<img src='image/" + n.userimage + "' class='img-circle' height='100px' " +
					"width='100px' />" +
					"<p style='margin-top: 10px;'>" + n.name + "</p>" +
					"<p style='margin-top: 10px;'>" +
					"经验<span style='margin-left: 10px; color: red;'>" + n.experience + "</span>" +
					"</p>" +
					"</center>" +
					"<div class='container' " +
					"style='width: 100%; height: 60px; background-color: #FFFFFF;'>" +
					"<img src='image/icon_l.png' style='float: left;' /> <img" +
					" src='image/icon_r.png' style='margin-top: 50px; float: right;' />" +
					"<p style='margin-top: 10px; margin-left: 10px;'>" + n.introduction + "</p>" +
					"</div>" +
					"</div>" +
					"</div>");
			});
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
	<div class="container">

		<!--
	            	作者：offline
	            	时间：2017-09-18
	            	描述：分类搜索
	            -->
		<div style="padding-left: 0px; padding-right: 0px;">
			<nav class="navbar navbar-default" role="navigation"
				style="margin-top: 20px;background-color: #5BC0DE;">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">分类</a>
				</div>
				<ul class="nav navbar-nav navbar-left" id="categorysList">
<%-- 					<c:forEach var="item" items="${categorys}" varStatus="status">
						<li><a href="javascript:void(0)" id="category${item.id}"><c:out
									value="${item.name}"></c:out></a></li>
					</c:forEach> --%>
				</ul>
			</div>
			</nav>
		</div>
		<!--
            	作者：offline
            	时间：2017-09-18
            	描述：中间的大容器
            -->
		<div style="padding-left: 0px; padding-right: 0px; height: 1200px;">
			<!--
                	作者：offline
                	时间：2017-09-18
                	描述：大容器中左边的容器
                -->
			<div id="left_big_content" class="container"
				style="width: 78%; height: 1200px; float: left; padding-top: 10px;">
				<div class="container" id="left_big_content_articles"
					style="width: 100%;">
					<!--
	                    	作者：offline
	                    	时间：2017-09-18
	                    	描述：具体内容的容器  即重复的容器
	                    -->


				</div>
				<!--
                    	作者：offline
                    	时间：2017-09-18
                    	描述：翻页的容器
                    -->
				<div class="container" id="left_big_content_pages"
					style="width: 100%; height: 50px; background-color: #FFFFFF;">
					<div class="parent">
						<div class="M-box children"></div>
					</div>
				</div>
			</div>
			<!--
                	作者：offline
                	时间：2017-09-18
                	描述：中间大容器中右边的容器
                -->
			<div id="right_big_content"
				style="width: 20%; height: 1200px; float: right;">

				<div
					style="width: 100%; height: 40px; text-align: center;background-color: #4CAE4C"
					class="img-rounded">
					<p style="padding-top: 10px;">名人榜</p>
				</div>

				<div id="right_big_content_hot">
					<!-- 开始显示热门的作者资料 -->

					<!-- 结束 -->
				</div>
			</div>


		</div>
		<!-- 引用尾部文件 -->
		<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>