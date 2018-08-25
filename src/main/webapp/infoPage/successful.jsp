<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<head>
<title>提交成功</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/common/taglib.jsp"%>
<%@include file="/common/css_js.jsp"%>
</head>
<body>

<div class="main text-center">

<!-- 页面内容开始 -->
  <div class="error-info text-center">
    <img src="<%=request.getContextPath()%>/media/images/common/successful.png">
    <h4 class="margin0 margin-b-15">该页面将在 <span id="miao" class="text-emphasize">3</span> 秒钟之后自动关闭</h4>
    <button type="button" class="btn btn-info" onclick="window.close();">立即关闭</button>
  </div>

  <script>
    function countDown(secs,mes)
    {
      miao.innerText=secs-1;
      if(secs==1)
      {
        window.opener.location.reload();
        window.close();
      }
      if(--secs>0)
      setTimeout("countDown("+secs+")",1000);
    }
    if (!'${successMessage }'){
      countDown(3);
    }
  </script>
<!-- 页面内容结束 -->
  
</div>
<%@include file="/common/footer_PopUp.jsp"%>
</body>
</html>