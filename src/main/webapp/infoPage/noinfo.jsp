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
<%request.setAttribute("titleInfo","暂无信息");%>
</head>
<body>

  <%@include file="/common/header.jsp"%>
  <%@include file="/common/nav-demo.jsp"%>

  <div class="main text-center">

  <!-- 页面内容开始 -->
    <div class="error-info text-center">
      <img src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/images/common/noinfo.png'>
      <p>抱歉，没有找到您需要的信息</p>
      <a href="#" onclick="history.go(-1)">返回上一级页面 &gt;</a>
    </div>
  <!-- 页面内容结束 -->
  
  </div>

  <%@include file="/common/footer.jsp"%>
</body>
</html>