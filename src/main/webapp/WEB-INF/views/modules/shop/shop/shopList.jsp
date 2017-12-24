<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户管理</title>
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
        function posView(shopId,shopName){
			window.location.href = '${ctx}/shop/pos/pos/posList?shopId='+shopId+'&shopName='+shopName;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/shop/shop/">商户列表</a></li>
		<shiro:hasPermission name="shop:shop:shop:edit"><li><a href="${ctx}/shop/shop/shop/form">商户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shop" action="${ctx}/shop/shop/shop/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户名称：</label>
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
				<th>商户名称</th>
				<th>联系人姓名</th>
				<th>电话</th>
				<th>所属行业</th>
				<th>备注信息</th>
				<th>更新时间</th>
				<shiro:hasPermission name="shop:shop:shop:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shop">
			<tr>
				<td><a href="${ctx}/shop/shop/shop/form?id=${shop.id}">
					${shop.name}
				</a></td>
				<td>
					${shop.contactName}
				</td>
				<td>
					${shop.phone}
				</td>
				<td>
					${shop.category.name}
				</td>
				<td>
					${shop.remarks}
				</td>
				<td>
					<fmt:formatDate value="${shop.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="shop:shop:shop:edit"><td>
    				<a href="${ctx}/shop/shop/shop/form?id=${shop.id}">修改</a>
					<a href="${ctx}/shop/shop/shop/delete?id=${shop.id}" onclick="return confirmx('确认要删除该商户吗？', this.href)">删除</a>
                    <a href="javascript:void(0);" onclick="posView('${shop.id}','${shop.name}')">POS机列表</a>
                </td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>