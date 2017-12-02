<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商场管理</title>
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
		<li class="active"><a href="${ctx}/business/businessmarket/businessMarket/">商场列表</a></li>
		<shiro:hasPermission name="business:businessmarket:businessMarket:edit"><li><a href="${ctx}/business/businessmarket/businessMarket/form">商场添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="businessMarket" action="${ctx}/business/businessmarket/businessMarket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商场名称：</label>
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
				<th>商场名称</th>
				<th>所属商圈</th>
				<th>省市区</th>
				<th>详细地址</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="business:businessmarket:businessMarket:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessMarket">
			<tr>
				<td><a href="${ctx}/business/businessmarket/businessMarket/form?id=${businessMarket.id}">
					${businessMarket.name}
				</a></td>
				<td>
					<c:choose>
						<c:when test="${businessMarket.circleName==null}">
							无所属商圈
						</c:when>
						<c:otherwise>
							${businessMarket.circleName}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					${businessMarket.location}
				</td>
				<td>
					${businessMarket.locationDetail}
				</td>
				<td>
					<fmt:formatDate value="${businessMarket.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${businessMarket.remarks}
				</td>
				<shiro:hasPermission name="business:businessmarket:businessMarket:edit"><td>
    				<a href="${ctx}/business/businessmarket/businessMarket/form?id=${businessMarket.id}">修改</a>
					<a href="${ctx}/business/businessmarket/businessMarket/delete?id=${businessMarket.id}" onclick="return confirmx('确认要删除该商场吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>