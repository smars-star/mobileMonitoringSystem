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
<body>

  <%@include file="/common/header.jsp"%>
  <%@include file="/common/nav-demo.jsp"%>

  <div class="main">
  <!-- 页面内容开始 -->
    <div class="error-info">
      <img class="pull-right" src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/images/common/working.png'>
      <div class="error-text">
        <h3 class="margin0 margin-b-15">页面正在开发中</h3>
          <p class="margin-b-10">非常抱歉，您访问的页面我们正在努力开发中，给您带来的不便之处还请谅解，请先使用系统其他功能。</p>
          <a href="#" onclick="history.go(-1)">返回上一级页面 &gt;</a><br>
      </div>
    </div>
  <!-- 页面内容结束 -->
  </div>

<%@include file="/common/footer.jsp"%>
</body>
</html>