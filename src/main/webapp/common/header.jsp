<%@ page language="java" pageEncoding="UTF-8"%>
<!--[if lt IE 7]>
    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<nav class="navbar navbar-inverse navbar-fixed-top mainpage-header">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand logo" href="#here"><%=request.getAttribute("titleInfo")%></a>
    </div>
    <ul class="nav navbar-nav navbar-right top-welcome">
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><img class="portrait pull-left" src="/media/images/user.jpg">管理员 <span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="#"><i class="fa"></i>校领导</a></li>
          <li><a href="#"><i class="fa fa-check"></i>主管参谋</a></li>
          <li><a href="#"><i class="fa"></i>教员</a></li>
        </ul>
      </li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-bell"></i><span class="count">5</span></a>
        <ul class="dropdown-menu" role="menu">
          <li class="item-header">你有5个新的通知</li>
          <li>
            <a href="#"><i class="fa fa-comment"></i>新的通知</a>
          </li>
          <li>
            <a href="#"><i class="fa fa-plus"></i>新用户注册</a>
          </li>
          <li>
            <a href="#"><i class="fa fa-envelope"></i>来自系统管理员的消息</a>
          </li>
          <li>
            <a href="#"><i class="fa fa-eye"></i>新的政策</a>
          </li>
          <li>
            <a href="#"><i class="fa fa-envelope"></i>来自管理员的消息</a>
          </li>
          <li class="item-footer"><a href="#">查看所有的通知</a> </li>
        </ul>
      </li>
      <li><a href="#" title="退出登录"><i class="fa fa-power-off"></i></a></li>
    </ul>
  </div>
</nav>