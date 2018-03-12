<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <title>用户反馈页面</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Oleo+Script:400,700'>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

    </head>

    <body>

        <div class="header">
            <div class="container">
                <div class="row">
                    <div class="logo span4">
                        <h1><a href="">深圳用户反馈信息 <span class="red">.</span></a></h1>
                    </div>
                    <div class="links span8">
                        <a class="home" href="" rel="tooltip" data-placement="bottom" data-original-title="Home"></a>
                        <!-- <a class="blog" href="" rel="tooltip" data-placement="bottom" data-original-title="Blog"></a> -->
                    </div>
                </div>
            </div>
        </div>
        <!-- <div class="copyrights">Collect from <a href="http://www.cssmoban.com/"  title="网站模板">网站模板</a></div> -->

        <div class="register-container container">
            <div class="row">
                <div class="iphone span5">
                    <img src="<%=request.getContextPath()%>/assets/img/iphone.png" alt="">
                </div>
                <div class="register span6">
                    <form action="" method="post">
                        <h2>客户反馈 <span class="red"><strong>信息</strong></span></h2>
                        <label for="firstname">客户称呼</label>
                        <input type="text" id="userName" name="userName" placeholder="先生/女士...">
                        <label for="lastname">反馈信息</label>
                        <textarea name="a" style="width:440px;height:80px;" id="infomation" name="infomation" placeholder="请输入您要反馈的内容..."></textarea>
                        <!-- <input type="textarea" id="infomation" name="infomation" placeholder="请输入您要反馈的内容..."> -->
                        <label for="username">公司</label>
                        <input type="text" id="company" name="company" placeholder="请输入您的公司名称...">
                        <label for="email">联系方式</label>
                        <input type="text" id="tel" name="tel" placeholder="方便我们联系您...">
                        <span id = "loginText"></span>
                        <button type="button" class="submit" tabindex="3" value="反馈" id = "feedBackButton">反馈</button>
                       <!--  <button type="submit">反馈</button> -->
                    </form>
                </div>
            </div>
        </div>

        <!-- Javascript -->
       <%--  <script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/jquery-1.12.3.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> --%>
        <script src="<%=request.getContextPath()%>/assets/js/jquery-1.8.2.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/jquery.backstretch.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

    </body>
<script>


	$("#feedBackButton").click(function(){
		//alert("欢迎登陆关系管理系统");
		var userName = $('#userName').val();
		var infomation = $('#infomation').val();
		var company = $('#company').val();
		var tel = $('#tel').val();
		alert(userName);
		alert(infomation);
		alert(company);
		alert(tel);
		  $.ajax({
              url: '/eaglecCrm/sys/saveFeedBack',
              data: {'userName':userName,'infomation':infomation,'company':company,'tel':tel},
              dataType: 'json',
              type: 'POST',
              success: function(data) {
            	  var code = data.success;
                  if (code == 0) {
                	  $('#loginText').html(data.Msg);
                      return;
                  }
                  var jumpUrl = '/eaglecCrm/sys/feedbackSuccess';  //跳转到列表页
                  window.location.href = jumpUrl;
                
              },
              error: function() {

              }
          });
	});  
	//$("#userLogin").popover(); 
</script>
</html>

