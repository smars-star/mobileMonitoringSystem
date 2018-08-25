<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<head>
<title>error</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/common/taglib.jsp"%>
<%@include file="/common/css_js.jsp"%>
</head>
<body>

  <nav class="navbar navbar-inverse navbar-fixed-top mainpage-header">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand logo" id="logo" href="<%=request.getContextPath()%>/">
          Error
        </a>
      </div>  
    </div>
  </nav>

  <div class="main">	
    <!-- 页面内容开始 -->
    <div class="error-info">
      <img class="pull-right" src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/images/common/nopermission.png'>
      <div class="error-text">
        <h3 class="margin0 margin-b-15">抱歉，您没有权限</h3>
          <p class="margin-b-10">1.请检查您的输入是否正确。<br>2.如果该问题一直存在请联系管理员。</p>
          <a href="#" onclick="history.go(-1)">返回上一级页面 &gt;</a><br>
      </div>
    </div>
    <!-- 页面内容结束 -->
  </div>

  <%@include file="/common/footer.jsp"%>

</body>
</html>