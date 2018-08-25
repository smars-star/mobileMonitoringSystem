<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	//页面logo文字
	request.setAttribute("titleInfo","GDS界面规范");

	java.util.Map gdsGuiWebThemeMap = new java.util.HashMap();

	//WebUI域名
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_domain", gds.framework.utils.PropertyUtils.getProperty("webUI"));

	//系统主题色：Blue / Green / Gray / Dark
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_colorScheme", "Blue");

	//页头：normal / big / small / none
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_headerSize", "normal");

	//侧栏导航：normal / none
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_sidebarStyle", "normal");

	//日期插件：datetimepicker / my97datepicker / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_dateTime", "my97datepicker");

	//启用bootstrap-table：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_bsTable", "true");

	//bootstrap-table分页数量
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_bsTablePageSize", "20");

	//启用多功能select框bootstrap-select：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_bsSelect", "true");

	//启用自动补全：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_completer", "true");

	//启用单选复选开关效果bootstrap-switch：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_bsSwitch", "true");

	//启用右键菜单bootstrap-contextmenu：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_bsContextMenu", "true");

	//启用拖动排序：jqxSortable / jqueryDad / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_sortable", "jqxSortable");

	//启用分隔器：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_splitter", "true");

	//启用单选复选框iCheck风格：true / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_iCheck", "false");

	//启用zTree：all（合并包） / core（核心包） / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_zTree", "core");

	//图表插件（建议在页面中配置）：FusionCharts / eCharts / false
	gdsGuiWebThemeMap.put("gdsGuiWebTheme_charts", "eCharts");

%>
