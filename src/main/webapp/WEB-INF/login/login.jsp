<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<!-- //深圳市依格欣计算机技术有限公司客户关系管理系统 -->
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />


</head>
<body>


<div class="login">
    <div class="box png">
		<div class="logo png"></div>
		<form id="form1" action="#"> 
		<div class="input">
			<div class="log">
				<div class="name">
					<label>用户名</label><input type="text" class="text" id="username" placeholder="用户名" name="value_1" tabindex="1">
				</div>
				<div class="pwd">
					<label>密　码</label><input type="password" class="text" id="password" placeholder="密码" name="value_2" tabindex="2">
					<br/>
					<span id = "loginText"></span>
					<input type="button" class="submit" tabindex="3" value="登录" id = "userLogin">
					<div class="check"></div>
				</div>
				<div class="tip"></div>
			</div>
		</div>
		</form>
	</div>
    <div class="air-balloon ab-1 png"></div>
	<div class="air-balloon ab-2 png"></div>
    <div class="footer"></div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fun.base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/script.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>

<!--[if IE 6]>
<script src="js/DD_belatedPNG.js" type="text/javascript"></script>
<script>DD_belatedPNG.fix('.png')</script>
<![endif]-->
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
<p>适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。</p>

<p>Copyright © 1998-2018  All Rights Reserved.  <a href="http://www.eaglec.com/" target="_blank" title="模板之家">深圳市依格欣计算机技术有限公司</a> - Collect from <a href="http://www.eaglec.com/" title="深圳市依格欣计算机技术有限公司" target="_blank">依格欣网络安全小组</a></p>
</div>
</body>


<script>

	$("#userLogin").click(function(){
		//alert("欢迎登陆关系管理系统");
		var username = $('#username').val();
		var password = $('#password').val();
		$('#loginText').html("");
		  $.ajax({
              url: '/eaglecCrm/sys/checkUserLogin',
              data: {'userName':username,'password':password},
              dataType: 'json',
              type: 'POST',
              success: function(data) {
            	  var code = data.success;
                  if (code == 0) {
                	  $('#loginText').html("登录失败");
                      return;
                  }
                  $('#loginText').html("登录成功，正在跳转...");
                  var jumpUrl = '/eaglecCrm/sys/customerInfoList?userId='+data.userId+'&userName='+data.userName;  //跳转到列表页
                  window.location.href = jumpUrl;
                
              },
              error: function() {

              }
          });
	});  
	//$("#userLogin").popover(); 
</script>
</html>