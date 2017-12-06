<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商场楼层管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/business/businessmarketfloor/businessMarketFloor/">商场楼层列表</a></li>
		<shiro:hasPermission name="business:businessmarketfloor:businessMarketFloor:edit"><li><a href="${ctx}/business/businessmarketfloor/businessMarketFloor/form">商场楼层添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="businessMarketFloor" action="${ctx}/business/businessmarketfloor/businessMarketFloor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>楼层号：</label>
				<form:input path="floor" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>楼层名：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层号</th>
				<th>楼层名</th>
				<th>该楼层类别</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="business:businessmarketfloor:businessMarketFloor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessMarketFloor">
			<tr>
				<td><a href="${ctx}/business/businessmarketfloor/businessMarketFloor/form?id=${businessMarketFloor.id}">
					${businessMarketFloor.floor}
				</a></td>
				<td>
					${businessMarketFloor.name}
				</td>
				<td>
					${businessMarketFloor.category}
				</td>
				<td>
					<fmt:formatDate value="${businessMarketFloor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${businessMarketFloor.remarks}
				</td>
				<shiro:hasPermission name="business:businessmarketfloor:businessMarketFloor:edit"><td>
    				<a href="${ctx}/business/businessmarketfloor/businessMarketFloor/form?id=${businessMarketFloor.id}">修改</a>
					<a href="${ctx}/business/businessmarketfloor/businessMarketFloor/delete?id=${businessMarketFloor.id}" onclick="return confirmx('确认要删除该商场楼层吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>