<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商圈管理</title>
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
		<li class="active"><a href="${ctx}/business/businesscircle/businessCircle/">商圈列表</a></li>
		<shiro:hasPermission name="business:businesscircle:businessCircle:edit"><li><a href="${ctx}/business/businesscircle/businessCircle/form">商圈添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="businessCircle" action="${ctx}/business/businesscircle/businessCircle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商圈名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商圈名称</th>
				<th>省市区</th>
				<th>门店数量</th>
				<th>备注信息</th>
				<th>更新时间</th>
				<shiro:hasPermission name="business:businesscircle:businessCircle:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessCircle">
			<tr>
				<td><a href="${ctx}/business/businesscircle/businessCircle/form?id=${businessCircle.id}">
					${businessCircle.name}
				</a></td>
				<td>
						${businessCircle.location}
				</td>
				<td>
					TODO
				</td>
				<td>
						${businessCircle.remarks}
				</td>
				<td>
					<fmt:formatDate value="${businessCircle.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="business:businesscircle:businessCircle:edit"><td>
    				<a href="${ctx}/business/businesscircle/businessCircle/form?id=${businessCircle.id}">修改</a>
					<a href="${ctx}/business/businesscircle/businessCircle/delete?id=${businessCircle.id}" onclick="return confirmx('确认要删除该商圈吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>