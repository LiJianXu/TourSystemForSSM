<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>我的博客管理后台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="${pageContext.request.contextPath }/css/default.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/themes/icon.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.easyui.js"></script>

<script type="text/javascript"
	src='${pageContext.request.contextPath }/js/outlook2.js'> </script>

<script type="text/javascript">
	var _menus = {
		"menus" : [
			{
				"menuid" : "1",
				"icon" : "icon-sys",
				"menuname" : "博客管理",
				"menus" : [ {
					"menuname" : "博客管理",
					"icon" : "icon-nav",
					"url" : "${pageContext.request.contextPath}/admin/articleManage.do"
				}
				]
			}, {
				"menuid" : "2",
				"icon" : "icon-sys",
				"menuname" : "用户管理",
				"menus" : [ {
					"menuname" : "用户管理",
					"icon" : "icon-nav",
					"url" : "${pageContext.request.contextPath}/admin/userManage.do"
				}
				]
			}

		]
	};

	$(function() {
		close()
		//设置登录窗口
		function openPwd() {
			$('#w').window({
				title : '修改密码',
				width : 320,
				modal : true,
				shadow : true,
				closed : true,
				height : 160,
				resizable : false
			});
		}

		//修改密码
		function serverLogin() {
			var newpass = $('#txtNewPass');
			var rePass = $('#txtRePass');

			if (newpass.val() == '') {
				msgShow('系统提示', '请输入密码！', 'warning');
				return false;
			}
			if (rePass.val() == '') {
				msgShow('系统提示', '请在一次输入密码！', 'warning');
				return false;
			}

			if (newpass.val() != rePass.val()) {
				msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
				return false;
			}

			$.post('${pageContext.request.contextPath}/admin/update.do?newPassword=' + newpass.val(), function(msg) {
				if(msg.state){
				msgShow('系统提示', msg.describe, 'info');
				newpass.val('');
				rePass.val('');
				close();
				}else{
				msgShow('系统提示', msg.describe, 'info');
				}
			})

		}
		//关闭登录窗口
		function close() {
			$('#w').window('close');
		}
		$("#cancel").click(function(){
			close();
		})
		$('#editpass').click(function() {
			$('#w').window('open');
		});
		$('#btnEp').click(function() {
			serverLogin();
		})
		$('#loginOut').click(function() {

			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

				if (r) {
					location.href = '${pageContext.request.contextPath}/admin/enter.do';
				}
			});

		})
	})
</script>
</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 60px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<h2>
			<span style="float:right; padding-right:20px;" class="head"><span
				id="editpass">修改密码</span> <span id="loginOut">安全退出</span></span> <span
				style="padding-left:10px; font-size: 30px;text-align: center; "><img
				src="${pageContext.request.contextPath }/image/blocks.gif"
				width="20" height="20" /> 我的博客后台管理系统</span>
		</h2>
	</div>

	<div region="west" split="true" title="导航菜单" style="width:180px;"
		id="west">
		<div class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>

	</div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y:hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				<h1>欢迎使用"我的博客"后台管理系统</h1>
			</div>
<!--  			<div title="文章管理" style="padding:20px;overflow:hidden;" id="article">
				<h1>文章</h1>
			</div>
			<div title="用户管理" style="padding:20px;overflow:hidden;" id="user">
				<h1>用户</h1>
			</div>  -->
		</div>
	</div>


	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save"
		style="width: 320px; height: 160px; padding: 5px;
        background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="Password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="Password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)"> 确定</a> <a class="easyui-linkbutton"
					icon="icon-cancel" href="javascript:void(0)" id="cancel">取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>
</html>
