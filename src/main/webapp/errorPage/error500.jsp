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
      <img class="pull-right" src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/images/common/500.png'>
      <div class="error-text">
        <h3 class="margin0 margin-b-15">抱歉，无法连接到服务器</h3>
          <p class="margin-b-10">1.请检查您的输入是否正确。<br>2.如果该问题一直存在请联系管理员。</p>
          <a href="#" onclick="history.go(-1)">返回上一级页面 &gt;</a><br>
          <a role="button" data-toggle="collapse" href="#error-code" aria-expanded="false" aria-controls="error-code">查看错误信息 &gt;</a>
      </div>
    </div>
    <div id="error-code" class="error-code collapse" aria-expanded="false">
      <div class="panel panel-default">
        <div class="panel-heading"><i class="fa fa-exclamation-circle"></i> 错误信息</div>
        <div class="panel-body">
          <pre>2016-8-2 9:28:55 org.apache.catalina.core.AprLifecycleListener init
  信息: The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: D:\Java\jdk1.6.0_20_64\bin;.;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;D:\Java\jdk1.6.0_45\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\Program Files (x86)\ATI Technologies\ATI.ACE\Core-Static;C:\Program Files (x86)\Autodesk\Backburner\;C:\Program Files\Common Files\Autodesk Shared\;D:\Program Files\QuickTime\QTSystem\
  2016-8-2 9:28:55 org.apache.coyote.http11.Http11Protocol init
  信息: Initializing Coyote HTTP/1.1 on http-8089
  2016-8-2 9:28:55 org.apache.catalina.startup.Catalina load
  信息: Initialization processed in 575 ms
  2016-8-2 9:28:55 org.apache.catalina.core.StandardService start
  信息: Starting service Catalina
  2016-8-2 9:28:55 org.apache.catalina.core.StandardEngine start
  信息: Starting Servlet Engine: Apache Tomcat/6.0.18
  2016-8-2 9:28:57 org.apache.catalina.core.StandardContext addApplicationListener
  信息: The listener "org.springframework.web.util.Log4jConfigListener" is already configured for this context. The duplicate definition has been ignored.
  2016-8-2 9:28:57 org.apache.catalina.core.StandardContext start
  严重: Error listenerStart
  2016-8-2 9:28:57 org.apache.catalina.core.StandardContext start
  严重: Context [/zbgl] startup failed due to previous errors
  log4j:WARN No appenders could be found for logger (org.directwebremoting.util.Logger).
  log4j:WARN Please initialize the log4j system properly.
  log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
  2016-8-2 9:29:02 org.apache.coyote.http11.Http11Protocol start
  信息: Starting Coyote HTTP/1.1 on http-8089
  2016-8-2 9:29:02 org.apache.jk.common.ChannelSocket init
  信息: JK: ajp13 listening on /0.0.0.0:8009
  2016-8-2 9:29:02 org.apache.jk.server.JkMain start
  信息: Jk running ID=0 time=0/11  config=null
  2016-8-2 9:29:02 org.apache.catalina.startup.Catalina start
  信息: Server startup in 7073 ms</pre>					
        </div>
      </div>
    </div>
    <!-- 页面内容结束 -->
  </div>

  <%@include file="/common/footer.jsp"%>

</body>
</html>