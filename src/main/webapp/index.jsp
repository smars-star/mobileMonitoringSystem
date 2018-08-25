<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">

<head>
  <title>GDS CSS 使用规范</title>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@include file="/common/taglib.jsp"%>
  <%@include file="/common/css_js.jsp"%>
</head>

<style type="text/css">
body{
 	margin:0 auto;
    text-align:center;
}

.context{
	font-family: sans-serif;
	display:block;
	margin-top:250px;
	margin-bottom:10px;
	font-size:80px;
	font-weight:800;
}

@media all and (-webkit-min-device-pixel-ratio:0) and (min-resolution: .001dpcm) { 
    .context{
        background-image: -webkit-linear-gradient(left, #147B96, #E6D205 25%, #147B96 50%, #E6D205 75%, #147B96);
        -webkit-text-fill-color: transparent;
        -webkit-background-clip: text;
        -webkit-background-size: 200% 100%;
        -webkit-animation: context-animation 4s infinite linear;
    }
}
@-webkit-keyframes context-animation {
    0%  { background-position: 0 0;}
    100% { background-position: -100% 0;}
}

@media all and (-webkit-min-device-pixel-ratio:0) and (min-resolution: .001dpcm) { 
    .right-little{
        background-image: -webkit-linear-gradient(left, #147B96, #E6D205 25%, #147B96 50%, #E6D205 75%, #147B96);
        -webkit-text-fill-color: transparent;
        -webkit-background-clip: text;
        -webkit-background-size: 200% 100%;
        -webkit-animation: right-little-animation 4s infinite linear;
    }
}

@-webkit-keyframes right-little-animation {
    0%  { background-position: 0 0;}
    100% { background-position: -100% 0;}
}

@media all and (-webkit-min-device-pixel-ratio:0) and (min-resolution: .001dpcm) { 
    .hr{
        background-image: -webkit-linear-gradient(left, #147B96, #E6D205 25%, #147B96 50%, #E6D205 75%, #147B96);
        -webkit-background-color: transparent;
        -webkit-background-clip: content-box;
        -webkit-background-size: 200% 100%;
        -webkit-animation: hr-animation 4s infinite linear;
    }
}

@-webkit-keyframes hr-animation {
    0%  { background-position: 0 0;}
    100% { background-position: -100% 0;}
}

.hr{
	background-color:red;
	margin:50px auto;
	display:block;
	width:90%;
	height: 5px;
}
</style>

<body class="body">
    <div class="main welcome text-center">
      <!-- 欢迎页  begin -->
      <div class="context">Hello ${appName }</div>
	  <div class="right-little">without CAS</div>
      <div class="hr"></div>
      <div class="fg-grayLight margin-t-15">建议使用1280*800以上分辨率，ie8及以上版本浏览器<br>西安长城数字开发部</div>
      <!-- 欢迎页  end -->
    </div>
</body>
</html>