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
                    <img src="<%=request.getContextPath()%>/assets/img/1.png" alt="">
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

	//$("#userLogin").popover(); 
</script>
</html>

