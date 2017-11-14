<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>头</title>
<!-- 引用父类的  css以及 js -->
<jsp:include page="/WEB-INF/jsp/indexfater.jsp"></jsp:include>
<style type="text/css">
.has-error {
	margin-bottom: 0px;
}
</style>
</head>
<script
	src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script>
	$(function() {
		//切换登陆和注册的tab
		$('#myTab li:eq(0) a').tab('show');

		function startLoginForm() {
			//验证登陆form表单
			$('#login_form').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					username : {
						message : '用户名验证失败',
						validators : {
							notEmpty : {
								message : '用户名不能为空'
							}
						}
					},
					password : {
						message : '密码验证失败',
						validators : {
							notEmpty : {
								message : '密码不能为空'
							}
						}
					}
				}
			});
		}
		startLoginForm();
		function startRegisterForm() {
			//验证注册表单
			$('#register_form').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					password : {
						message : '密码验证失败',
						validators : {
							notEmpty : {
								message : '密码不能为空'
							},
							stringLength : {
								min : 6,
								max : 18,
								message : '密码长度必须在6到18位之间'
							},
							regexp : {
								regexp : /^[a-zA-Z0-9_]+$/,
								message : '密码只能包含大写、小写、数字和下划线'
							}
						}
					},
					confirmpassword : {
						message : '密码验证失败',
						validators : {
							notEmpty : {
								message : '密码不能为空'
							},
							identical : {
								field : 'password',
								message : '两次输入密码不一致'
							}
						}
					},
					username : {
						message : '用户验证失败',
						validators : {
							callback : { /*自定义，可以在这里与其他输入项联动校验*/
								message : '请输入邮箱或者电话号码',
								callback : function(value, validator, $field) {
									var phone = /^1[3|4|5|7|8][0-9]{9}$/;
									var email = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
									//判断输入的值是否是手机号 或者邮箱
									if (phone.test(value)) {
										return true;
									} else if (email.test(value)) {
										return true;
									} else {
										return false;
									}
								}
							}
						}
					}
				}
			});
		}
		startRegisterForm();
		//模态框消失的时候 重置验证
		//myModal
		$('#myModal').on('hidden.bs.modal', function() {
			$("#login_form").data('bootstrapValidator').destroy();
			$('#login_form').data('bootstrapValidator', null);
			$("#register_form").data('bootstrapValidator').destroy();
			$('#register_form').data('bootstrapValidator', null);
		})
		//点击登录按钮
		$("#loginbtn").click(function() {
			//得到表单中的数据
			var data = {
				id : 0,
				phone : $("#username").val(),
				email : $("#username").val(),
				password : $("#password").val()
			};
			//发生ajax请求
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/user/login.do",
				data : JSON.stringify(data),
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				error : function(XMLResponse) {
					alert("错误")
				},
				success : function(data) {
					if (data.state == true) {
						$('#myModal').modal('hide')
						$("#login_form").data('bootstrapValidator').destroy();
						$('#login_form').data('bootstrapValidator', null);
						alert("登录成功")
						window.location.href = "${pageContext.request.contextPath}/index.jsp";
					} else {
						alert(data.describe);
						$("#username").val("");
						$("#password").val("");
						$("#login_form").data('bootstrapValidator').destroy();
						$('#login_form').data('bootstrapValidator', null);
						//开始验证表单数据
						startLoginForm();
					}
				}
			});
		});
		//点击注册按钮
		$("#registerbtn").click(function() {
			//得到表单中的数据
			var data = {
				id : 0,
				phone : $("#register_username").val(),
				email : $("#register_username").val(),
				password : $("#register_conf_password").val()
			};
			//发生ajax请求
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/user/register.do",
				data : JSON.stringify(data),
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				error : function(XMLResponse) {
					alert("错误")
				},
				success : function(data) {
					if (data.state == true) {
						$('#myModal').modal('hide')
						$("#login_form").data('bootstrapValidator').destroy();
						$('#login_form').data('bootstrapValidator', null);
						alert("注册成功")
						window.location.href = "${pageContext.request.contextPath}/index.jsp";
					} else {
						alert(data.describe);
					}
				}
			});
		});
	});
</script>
<body>
	<!--
        	作者：offline
        	时间：2017-09-18
        	描述：导航部分
        -->
	<div style="background-color: #5BC0DE;">
		<div class="container">
			<nav class="navbar navbar-default" role="navigation"
				style="margin-bottom: 0px;">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand"
						href="${pageContext.request.contextPath}/index.jsp">我的博客</a>
				</div>
				<div>
					<ul class="nav navbar-nav navbar-right">
						<c:if test="${userdata==null}">
							<li><a href="#" data-toggle="modal" data-target="#myModal"><span
									class="glyphicon glyphicon-log-in"
									style="margin-right: 5px;margin-top: 3px;"></span>登录/注册</a></li>
						</c:if>
						<c:if test="${userdata!=null}">
							<li><a
								href="${pageContext.request.contextPath}/user/enter_personal.do"
								style="margin-right: 5px;margin-top: 2px;">个人中心</a></li>
							<li><a href="${pageContext.request.contextPath}/user/out.do"><span
									class="glyphicon glyphicon-log-out"
									style="margin-right: 5px;margin-top: 3px;"></span>退出登录</a></li>
							<li><a
								href="${pageContext.request.contextPath}/DetailedArticel/enterEdit.do"><span
									class="	glyphicon glyphicon-edit"
									style="margin-right: 5px;margin-top: 3px;"></span>编写博客</a></li>
						</c:if>
					</ul>
				</div>
			</div>
			</nav>
		</div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 40%; height: 50%;">
			<div class="modal-content" style="height: 100%;">
				<!--
                    	作者：offline
                    	时间：2017-09-15
                    	描述：注册或者登录切换
                   -->
				<ul id="myTab" class="nav nav-tabs">
					<li style="width: 50%; text-align: center;" class="active"><a
						href="#login" data-toggle="tab">登录</a></li>
					<li style="width: 50%; text-align: center;"><a
						href="#register" data-toggle="tab">注册</a></li>
				</ul>
				<div class="modal-body container tab-content" style="width: 100%">
					<!--
                        	作者：offline
                        	时间：2017-09-18
                        	描述：开始登陆
                        -->
					<div class="tab-pane fade active in" id="login">
						<div class="container" style="width: 60%">
							<div class="form-horizontal" style="margin-top: 45px;"
								id="login_form">
								<div class="form-group">
									<label class="col-sm-4 control-label">用户名</label>
									<div class="col-sm-8">
										<input type="text" id="username" class="form-control"
											placeholder="邮箱或者电话号码" name="username">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">密码</label>
									<div class="col-sm-8">
										<input type="password" id="password" class="form-control"
											name="password" placeholder="密码">
									</div>
								</div>
								<div class="form-group">
									<center>
										<button class="btn btn-success" id="loginbtn">登录</button>
									</center>

								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="register">
						<!--
                            	作者：offline
                            	时间：2017-09-18
                            	描述：开始注册
                            -->
						<div class="container" style="width: 60%">
							<div class="form-horizontal" role="form"
								style="margin-top: 20px;" id="register_form">
								<div class="form-group">
									<label class="col-sm-4 control-label">用户名</label>
									<div class="col-sm-8">
										<input type="text" id="register_username" class="form-control"
											placeholder="邮箱或者电话号码" name="username">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">密码</label>
									<div class="col-sm-8">
										<input type="password" id="register_password"
											class="form-control" name="password" placeholder="密码">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label">确认密码</label>
									<div class="col-sm-8">
										<input type="password" class="form-control"
											name="confirmpassword" id="register_conf_password"
											placeholder="确认密码">
									</div>
								</div>
								<div class="form-group">
									<center>
										<button id="registerbtn" class="btn btn-success">注册</button>
									</center>

								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>