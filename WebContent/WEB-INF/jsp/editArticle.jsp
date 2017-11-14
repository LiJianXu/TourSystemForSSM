<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编写文章</title>
<!-- 引入富文本的样式 -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.0/css/froala_editor.pkgd.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.0/css/froala_style.min.css"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
<link href="${pageContext.request.contextPath }/css/fileinput.css"
	media="all" rel="stylesheet" type="text/css" />

</head>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>

<!-- 引入富文本的js -->
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/mode/xml/xml.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.0//js/froala_editor.pkgd.min.js"></script>

<!-- 引入markdown的插件 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/markdown.js"></script>
<!-- 引入文件上传 -->
<script src="${pageContext.request.contextPath }/js/fileinput.js"
	type="text/javascript"></script>
<script>
	$(function() {

	
		//显示富文本编辑器
		$("#showedit").click(function() {
			$("#editmarkdown").hide();
			$("#edit").show()
		})
		//显示markdown文本编辑器
		$("#showmarkdownedit").click(function() {
		alert($("#text-input").val())
			$("#edit").hide();
			$("#editmarkdown").show()
			var $1 = function(id) {
				return document.getElementById(id);
			};
			new Editor($1("text-input"), $1("preview"));
			//带动滚动条滑动
			var div = document.getElementById("text-input");
			//alert(div.value);
			//不能随时改变
			$("#text-input").scroll(function() {
				$("#preview").scrollTop($("#text-input").scrollTop());
			});
		})
		//初始化富文本框
		$('#edit').froalaEditor()


		//定义编辑函数
		function Editor(input, preview) {
			this.update = function() {
				preview.innerHTML = markdown.toHTML(input.value);
			};
			input.editor = this;
			this.update();
		}
		$("#submit").click(function() {
			//发布博客
			submitAjax(3);
		})
		$("#save").click(function() {
			//保存博客
			submitAjax(2);
		})
		$("#last").click(function() {
			//保存博客
			$("#mymodal").modal("toggle");
			//window.location.href = "${pageContext.request.contextPath}/index.jsp";
		})
		$("#share").click(function(){
			window.location.href = "${pageContext.request.contextPath}/index.jsp";
		})
		//发生ajax请求
		function submitAjax(state){
						//发生ajax请求
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/DetailedArticel/add.do",
				data : JSON.stringify(getData(state)),
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				error : function(XMLResponse) {
					alert("错误")
				},
				success : function(data) {
					//alert(data.obj);
					if (data.state) {
						//ArticlesCategoryR
						$.post("${pageContext.request.contextPath}/ArticlesCategoryR/add.do", {
							"categorys[]" : getCategorys(),
							"articleId" : data.obj
						},
							function(data) {
								if(data.state){
									alert("发布博客成功");
									window.location.href = "${pageContext.request.contextPath}/index.jsp";
								}else{
									alert("发布博客失败");
								}
							}
						);
					}
				}
			});
		}
		//得到所选的分类
		function getCategorys() {
			//得到选中的分类
			var categorys = new Array();
			var i = 0;
			$("[name='checkbox']").each(function() {
				if ($(this).is(':checked')) {
					categorys[i] = $(this).val();
					i++;
				}
			})
			return categorys.join(",");
		}
		//得到界面上的数据
		function getData(stateid) {
			//文章的类型
			var type = $("#articleType").val();
			var title = $("#articleTitle").val();
			var introduction = $("#introduction").val();
			var labels = $("#lebles").val();


			//是否转载
			var isturn = $("#isturn").val();
			//获取文本的内容
			var contents;
			//获取编写文本的类型
			var editcontenttype = 0;
			if ($("#edit").css("display") == "block") {
				//alert("富文本")
				//content=$("#edit").html();
				editcontenttype = 1;
				//得到富文本框的值
				contents = $("#edit").froalaEditor('html.get');
				contents=contents.replace(/["]+/g,"'");
			}
			if ($("#editmarkdown").css("display") == "block") {
				editcontenttype = 2;
				contents = $("#text-input").val();
			}
			
			var data = {
				id : 0,
				title : title,
				introduction : introduction,
				contents : contents,
				editcontenttype : editcontenttype,
				isturn : isturn,
				labels : labels,
				stateid : stateid,
				typeid : type
			}
			return data;
		}
		
		    //0.初始化fileinput
    var oFileInput = new FileInput();
    oFileInput.Init("txt_file", "${pageContext.request.contextPath}/File/treeFile.do");
     
});
</script>
<script type="text/javascript">
	//初始化fileinput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    oFile.Init = function(ctrlName, uploadUrl) {
    var control = $('#' + ctrlName);

    //初始化上传控件的样式
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: uploadUrl, //上传的地址
        allowedFileExtensions: ['txt', 'docx', 'zip','doc','rar'],//接收的文件后缀
        showUpload: true, //是否显示上传按钮
        showCaption: true,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式    
        showUploadedThumbs:false,
         showPreview :true, 
        dropZoneEnabled: true,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount: 10, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        layoutTemplates:{
        //删除上传，下载，删除按钮
        	actionUpload :"",
        	actionDownload :"",
        	actionDelete :"",
        },
        allowedPreviewTypes: null, 
        previewFileIcon: "<i class='glyphicon glyphicon-file'></i>"
    });

    //导入文件上传完成之后的事件
    $("#txt_file").on("fileuploaded", function (event, data, previewId, index) {
    	alert(data.response.describe);
    });
}
    return oFile;
};
</script>
<body>
	<!-- 引用头文件 -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include> 
<div class="modal fade" id="mymodal">  
    <div class="modal-dialog">  
        <div class="modal-content">  
            <div class="modal-header">  
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>  
                <h4 class="modal-title">确认放弃?</h4>  
            </div>  
            <div class="modal-body">  
                <p>放弃会丢失保存的数据？？？？？？</p>  
            </div>  
            <div class="modal-footer">  
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>  
                <button type="button" class="btn btn-primary" id="share">确定</button>  
            </div>  
        </div><!-- /.modal-content -->  
    </div><!-- /.modal-dialog -->  
</div><!-- /.modal -->  

	<!--
        	作者：1582722349@qq.com
        	时间：2017-09-21
        	描述：中间的部分
        -->
	<div class="container" style="margin-top: 10px;height:90%">
		<div style="width: 100%;">
			<center>
				<div style="width: 50%;height: 30px;">
					<div class="form-group">
						<select class="col-md-2" style="height: 30px;" id="articleType">
							<option value="1">原创</option>
							<option value="2">转载</option>
							<option value="3">翻译</option>
						</select> <input type="text" class="col-md-10" placeholder="请输入文章的标题"
							id="articleTitle" style="height: 30px;" />
					</div>
				</div>
				<div style="width: 50%;">
					<label style="margin-top: 10px;float: left;">内容简介</label>
					<div>
						<textarea class="form-control" rows="2" id="introduction"></textarea>
					</div>

				</div>
				<div>
					<div style="width: 50%;height: 30px;">
						<div class="form-group">
							<span class="col-md-2" style="margin-top: 5px;"><b>标签:</b></span>
							<input type="text" class="col-md-8" placeholder="添加标签(用空格隔开哦)"
								style="height: 30px;" id="lebles" /> <span class="col-md-2"
								style="margin-top: 5px;">例如:A B</span>
						</div>
					</div>
				</div>
			</center>
			<!--
                	作者：1582722349@qq.com
                	时间：2017-09-25
                	描述：分类显示部分
                -->
			<div>
				<span><b>分类:</b></span>
				<div id="articlcategory">
					<c:forEach var="item" items="${categorys}" varStatus="status">
						<label class="checkbox-inline"> <input type="checkbox"
							name="checkbox" value="${item.id}">${item.name}</label>
					</c:forEach>
				</div>

			</div>
			<!--
                	作者：1582722349@qq.com
                	时间：2017-09-21
                	描述：富文本内容
                -->
			<div>
				<div style="margin-top: 10px;">
					<select style="height: 30px;" id="isturn">
						<option value="1">允许被转载</option>
						<option value="2">不允许被转载</option>
					</select>
					<button class="btn btn-success" style="float: right;" id="showedit">切换成富文本编辑</button>
					<button class="btn btn-success"
						style="float: right;margin-right: 10px;" id="showmarkdownedit">切换成markdown编辑</button>
				</div>
				<div id='edit' style="margin-top: 10px;height: auto;">
					<img class="fr-fir"
						src="${pageContext.request.contextPath }/image/header.jpg"
						alt="Old Clock" width="300" />

					<h1>Click and edit</h1>

					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.Donec facilisis diam in odio iaculis blandit. Nunc eu mauris sitamet purus viverra gravida ut a dui. Vivamus nec rutrum augue,pharetra faucibus purus. Maecenas non orci sagittis, vehiculalorem et, dignissim nunc. Suspendisse suscipit, diam non variusfacilisis, enim libero tincidunt magna, sit amet iaculis eroslibero sit amet eros. Vestibulum a rhoncus felis. Nam lacus nulla,consequat ac lacus sit amet, accumsan pellentesque risus. Aeneanviverra mi at urna mattis fermentum. Curabitur porta metus intortor elementum, in semper nulla ullamcorper. Vestibulum mattistempor tortor quis gravida. In rhoncus risus nibh. Nullamcondimentum dapibus massa vel fringilla. Sed hendrerit sed estquis facilisis. Ut sit amet nibh sem. Pellentesque imperdietmollis libero.</p>

					<p>
						<a href="http://google.com" title="Aenean sed hendrerit">Aeneansed hendrerit</a> velit. Nullam eu mi dolor. Maecenas et erat risus.Nulla ac auctor diam, non aliquet ante. Fusce ullamcorper, ipsumid tempor lacinia, sem tellus malesuada libero, quis ornare semmassa in orci. Sed dictum dictum tristique. Proin eros turpis,ultricies eu sapien eget, ornare rutrum ipsum. Pellentesque erosnisl, ornare nec ipsum sed, aliquet sollicitudin erat. Nullatincidunt porta vehicula.
					</p>

					<p>Nullam laoreet imperdiet orci ac euismod. Curabitur vellectus nisi. Phasellus accumsan aliquet augue, eu rutrum tellusiaculis in. Nunc viverra ultrices mollis. Curabitur malesuada nuncmassa, ut imperdiet arcu lobortis sed. Cras ac arcu mauris.Maecenas id lectus nisl. Donec consectetur scelerisque quam atultricies. Nam quis magna iaculis, condimentum metus ut, elementummetus. Pellentesque habitant morbi tristique senectus et netus etmalesuada fames ac turpis egestas. Vivamus id tempus nisi.</p>
				</div>
				<div id='editmarkdown'
					style="margin-top: 10px;width: 100%;height: 1000px;background-color: #CCCCCC;display:none">
					<textarea id="text-input" oninput="this.editor.update()" rows="10"
						cols="60" style="float: left;width: 50%;height: 100%;">##诉讼费</textarea>
					<div id="preview"
						style="overflow:scroll;float: right;width: 49%;height: 100%;margin-left: 10px;">
					</div>
				</div>
			</div>
			<!--
        	作者：1582722349@qq.com
        	时间：2017-09-27
        	描述：上传文件
        -->
			<div style="margin-top: 20px;">
				<div style="width: 100%;float: left;" class="container">
<%-- 				<form enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/File/treeFile.do">
						<input id="file-0a" class="file" type="file" multiple
							data-min-file-count="1" name="file"> <br>

						<h3 style="color: red">上传需注意</h3>				
				</form> --%>
<input type="file" name="file" id="txt_file" multiple class="file-loading" />
				</div>
			</div>
			<div style="margin-top: 10px;">
				<div style="height: auto;width: 100%;" class="container">
					<center>
						<div class="btn btn-info" id="submit">发布</div>
						<div class="btn btn-success" id="save">保存</div>
						<div class="btn btn-danger" id="last">放弃</div>
					</center>
				</div>
			</div>
		</div>
		
			
	</div>

	<!-- 引用尾部文件 -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

</body>
</html>