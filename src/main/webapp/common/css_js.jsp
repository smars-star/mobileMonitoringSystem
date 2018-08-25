<%
	net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(gdsGuiWebThemeMap);
	request.setAttribute("gdsGuiWebThemeSetting", jsonObject.toString());
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/media/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/media/bootstrap/css/bootstrap-theme.min.css">
<!--[if (gt IE 9)|!(IE)]><!-->
<script id="gdsGuiWebThemeScript" src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/js/gdsGuiWebTheme.js' data='<%=(String)request.getAttribute("gdsGuiWebThemeSetting")%>'></script>
<!--<![endif]-->
<!--[if IE 9]>
	<script id="gdsGuiWebThemeScript" src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/js/gdsGuiWebTheme.js' data='<%=(String)request.getAttribute("gdsGuiWebThemeSetting")%>' dataie='ie9'></script>
<![endif]-->
<!--[if lte IE 8]>
	<script id="gdsGuiWebThemeScript" src='<%=(String)gdsGuiWebThemeMap.get("gdsGuiWebTheme_domain")%>/media/js/gdsGuiWebTheme.js' data='<%=(String)request.getAttribute("gdsGuiWebThemeSetting")%>' dataie='lteie8'></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/media/css/default.css">