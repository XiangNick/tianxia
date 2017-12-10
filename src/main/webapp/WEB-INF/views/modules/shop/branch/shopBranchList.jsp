<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门店管理</title>
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
		<li class="active"><a href="${ctx}/shop/branch/shopBranch/">门店列表</a></li>
		<shiro:hasPermission name="shop:branch:shopBranch:edit"><li><a href="${ctx}/shop/branch/shopBranch/form">门店添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopBranch" action="${ctx}/shop/branch/shopBranch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>门店名称：</label>
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
				<th>门店名称</th>
				<th>详细地址</th>
				<th>备注信息</th>
				<th>更新时间</th>
				<shiro:hasPermission name="shop:branch:shopBranch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopBranch">
			<tr>
				<td><a href="${ctx}/shop/branch/shopBranch/form?id=${shopBranch.id}">
					${shopBranch.name}
				</a></td>
				<td>
					${shopBranch.locationDetail}
				</td>
				<td>
					${shopBranch.remarks}
				</td>
				<td>
					<fmt:formatDate value="${shopBranch.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="shop:branch:shopBranch:edit"><td>
    				<a href="${ctx}/shop/branch/shopBranch/form?id=${shopBranch.id}">修改</a>
					<a href="${ctx}/shop/branch/shopBranch/delete?id=${shopBranch.id}" onclick="return confirmx('确认要删除该门店吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>