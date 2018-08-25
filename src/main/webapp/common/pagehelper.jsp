<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function enterMoveFocus(ev, pageNum, pageSize, currentPage)
	{	
		if (ChkError(ChkInteger(currentPage),currentPage) && pageNum && pageNum > 0) {
			if(ev.keyCode==13){
				findPageHelper(pageNum, pageSize);			
		    }
		}
	}
	
	function findPageHelper(pageNum, pageSize){
		document.getElementById("pageNum").value = pageNum;
		document.getElementById("pageSize").value = pageSize;
		document.forms[0].submit();
	}
</script>
<!--   -->
<!-- 页数 -->
<input type="hidden" id="pageNum" name="pageNum" value="">
<input type="hidden" id="pageSize" name="pageSize" value="">
<div class="data-pagination form-inline"> 
	<div class="pagination"> 
		<span class="pagination-info">
			共找到<font color="#FF0000">&nbsp;${pagehelper.total}&nbsp;</font>条记录，当前显示第&nbsp;${pagehelper.startRow}&nbsp;~&nbsp;${pagehelper.endRow}&nbsp;条。
		</span>
	</div>
	
	<div class="pull-right"> 
		<ul class="pagination pagination-sm">
			<c:if test="${!pagehelper.isFirstPage}">
				<li class=""><a href="javascript:findPageHelper(1, ${pagehelper.pageSize});">首页</a></li>
				<li class="page-pre"><a href="javascript:findPageHelper(${pagehelper.prePage}, ${pagehelper.pageSize});">上页</a></li>
			</c:if>
	
			<c:forEach items="${pagehelper.navigatepageNums}" var="navigatepageNum" varStatus="indexNum">
				<c:if test="${indexNum.index < 5 }">
					<c:if test="${navigatepageNum==pagehelper.pageNum}">
						<li class="page-number active"><a href="javascript:void(0)">${navigatepageNum}</a></li>
					</c:if>
					<c:if test="${navigatepageNum!=pagehelper.pageNum}">
						<li class="page-number"><a href="javascript:findPageHelper(${navigatepageNum}, ${pagehelper.pageSize});">${navigatepageNum}</a></li>
					</c:if>
				</c:if>
			</c:forEach>
			
			<c:if test="${!pagehelper.isLastPage}">
				<li class="page-next"><a href="javascript:findPageHelper(${pagehelper.nextPage}, ${pagehelper.pageSize});">下页</a></li>
				<li class=""><a href="javascript:findPageHelper(${pagehelper.pages}, ${pagehelper.pageSize});">尾页</a></li>
			</c:if>
		</ul> 
		<!-- 只有总页数大于1时才显示跳转到*页 -->
		<c:if test="${fn:length(pagehelper.navigatepageNums) > 5}">
			<span class="pagination pagination-jump"> 跳转到 <input type="text" id="currentPage" name="currentPage" size="2" class="form-control input-sm" onkeyup="enterMoveFocus(event, this.value, ${pagehelper.pageSize}, this)"> 页</span>
		</c:if>
	</div> 
</div>