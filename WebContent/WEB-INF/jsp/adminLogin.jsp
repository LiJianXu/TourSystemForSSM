<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员登录界面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/adminlogin.css">
	

  </head>
  
  <body>
<div class="container">
	<section id="content">
		<form action="${pageContext.request.contextPath }/admin/index.do" method="post">
			<h1>管理员登录</h1>
			<div>
				<input type="text" placeholder="Username" required="" id="username" name="phone"/>
			</div>
			<div>
				<input type="password" placeholder="Password" required="" id="password" name="password" />
			</div>
			
			<div style="width: 100%;height: 100px;text-align: center;">
					<input type="submit" value="登录" />
			</div>
		</form><!-- form -->
	</section><!-- content -->
</div><!-- container -->
</body>
</html>
