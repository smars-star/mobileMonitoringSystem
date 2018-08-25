<%@ page language="java" pageEncoding="UTF-8"%>
<%if (!"none".equals((String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_sidebarStyle"))) {%>
<div class='sidebar sidebar-fixed ace-save-state' id="sidebar">
  <script type="text/javascript">
  try{ace.settings.loadState('sidebar')}catch(e){}
  $(document).ready(function() {
    //获取当前的链接地址
      var url = window.location.href;
      //用来存放当前选中的li
      var liObj;
      var liObjArray = document.getElementById('sidebar').getElementsByTagName("li");
      if (liObjArray && liObjArray.length) {
        for ( var i = 0; i < liObjArray.length; i++) {
          if (url.indexOf(liObjArray[i].id) > 0) {
            liObjArray[i].className = "active";
            liObj = liObjArray[i];
            $(liObj).parents('li').addClass('active');
          }
        }
      }

      var accordion_head = $('.nav-list>li>a.dropdown-toggle');
      var accordion_body = $('.nav-list>li>ul.submenu');
      var slideIndex = 0;
      var subLi;
      var flag = false;
      if (liObj) {

        accordion_body.each(function(i) {
         
          var subLi = this.childNodes;
          for ( var j = 0; j < subLi.length; j++) {
            if (subLi[j].id == liObj.id) {
              flag = true;
              break;
            }
          }
          if (flag) {
            slideIndex = i;
            flag = false;
          }
        
        });
        
        if (accordion_head) {
          var pclass = liObj.parentNode.className;
          if(pclass=='submenu'){
            //默认展开某栏目菜单 
            //accordion_head.eq(slideIndex).addClass('active').next().slideDown('normal');
            //alert(slideIndex);
            accordion_head.eq(slideIndex).addClass('active');
            accordion_body.eq(slideIndex).addClass('SubMenuOpen');
            $("ul.SubMenuOpen").slideDown(0);
          }
        }
        
      }

    });
  </script>
  <a class="sidebar-toggle sidebar-collapse" id="sidebar-collapse" title="收缩/展开左侧菜单"><i id="sidebar-toggle-icon" class="ace-save-state ace-icon fa fa-angle-double-left" data-icon1="fa fa-angle-double-left" data-icon2="fa fa-angle-double-right"></i></a>
  <ul class="nav-list">
    <li id="index-portal"><a href="/demo/index-portal.jsp"><i class="fa fa-columns"></i><span class="menu-text">门户型首页</span></a></li>
    <li id="index-module"><a href="/demo/index-module.jsp"><i class="fa fa-tasks"></i><span class="menu-text">功能型首页</span></a></li>
		<li id="index-common"><a href="/demo/index-common.jsp"><i class="fa fa-newspaper-o"></i><span class="menu-text">信息查看型首页</span></a></li>
    <li>
      <a href="#" class="dropdown-toggle"><i class="fa fa-table"></i><span class="menu-text">数据列表页面</span> <span class="caret"></span></a>
      <ul class="submenu">
        <li id="list1"><a href="/demo/list1.jsp">数据列表页1</a></li>
        <li id="list2"><a href="/demo/list2.jsp">数据列表页2</a></li>
        <li id="list3"><a href="/demo/list3.jsp">数据列表页3</a></li>
        <li id="sidebar1"><a href="/demo/sidebar1.jsp">数据列表页4</a></li>
        <li id="tabs1"><a href="/demo/tabs1.jsp">数据列表页5</a></li>
				<li id="sidebar2"><a href="/demo/sidebar2.jsp">数据列表页6</a></li>
				<li id="tabs2"><a href="/demo/tabs2.jsp">数据列表页7</a></li>
      </ul>
    </li>
    <li>
      <a href="#" class="dropdown-toggle"><i class="fa fa-check-square"></i><span class="menu-text">表单提交页面</span> <span class="caret"></span></a>
      <ul class="submenu">
        <li id="pop-tab"><a href="/demo/pop-tab.jsp">表单提交页1</a></li>
        <li id="pop-tab2"><a href="/demo/pop-tab2.jsp">表单提交页2</a></li>
        <li id="pop-tab2-2"><a href="/demo/pop-tab2-2.jsp">表单提交页2-2</a></li>
        <li id="pop-tab3"><a href="/demo/pop-tab3.jsp">表单提交页3</a></li>
      </ul>
    </li>
    <li>
      <a href="#" class="dropdown-toggle"><i class="fa fa-file-text-o"></i><span class="menu-text">信息查看页面</span> <span class="caret"></span></a>
      <ul class="submenu">
        <li id="pop-common"><a href="/demo/pop-common.jsp">信息查看页1</a></li>
        <li id="pop-common2"><a href="/demo/pop-common2.jsp">信息查看页2</a></li>
        <li id="pop-common3"><a href="/demo/pop-common3.jsp">信息查看页3</a></li>
      </ul>
    </li>
    <li>
      <a href="#" class="dropdown-toggle"><i class="fa fa-warning"></i><span class="menu-text">异常提示页</span> <span class="caret"></span></a>
      <ul class="submenu">
        <li id="noinfo"><a href="/infoPage/noinfo.jsp">无信息</a></li>
        <li id="successful"><a href="/infoPage/successful.jsp">成功提示</a></li>
        <li id="nopermission"><a href="/errorPage/nopermission.jsp">无权限</a></li>
        <li id="error404"><a href="/errorPage/error404.jsp">404错误</a></li>
        <li id="error500"><a href="/errorPage/error500.jsp">500错误</a></li>
      </ul>
    </li>
    <li class="divider"></li>
    <li class="other-link open">
      <a href="#" class="dropdown-toggle"><i class="fa fa-ellipsis-h"></i></a>
      <ul class="submenu">
        <li id="welcome"><a href="/demo/welcome.jsp"><i class="fa fa-angle-right"></i><span class="menu-text">欢迎页面</span></a></li>
        <li id="iframe"><a href="/demo/iframe.jsp"><i class="fa fa-angle-right"></i><span class="menu-text">iframe页面</span></a></li>
        <li id="working"><a href="/infoPage/working.jsp"><i class="fa fa-angle-right"></i><span class="menu-text">建设中页面</span></a></li>
      </ul>
    </li>
  </ul>
</div>
<%}%>