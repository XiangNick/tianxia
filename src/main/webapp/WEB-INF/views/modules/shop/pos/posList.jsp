<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>pos机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

            $("#prePage").click(function(){
                window.location.href = "${ctx}/shop/shop/shop";
			});
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
		<li class="active"><a href="${ctx}/shop/pos/pos/">pos机列表</a></li>
		<shiro:hasPermission name="shop:shop:shop:edit"><li><a href="${ctx}/shop/pos/pos/form?shop.id=${shopId}&shop.name=${shopName}">pos机添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pos" action="${ctx}/shop/pos/pos/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="prePage" class="btn btn-primary" type="button" value="返回上一页"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>pos号</th>
				<th>商户</th>
				<th>门店</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="shop:shop:shop:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${postList}" var="pos">
			<tr>
				<td>
					<a href="${ctx}/shop/pos/pos/form?id=${pos.id}">
						${pos.posNo}
					</a>
				</td>
				<td>
					${pos.shop.name}
				</td>
				<td>
					${pos.branch.name}
				</td>
				<td>
					<fmt:formatDate value="${pos.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pos.remarks}
				</td>
				<shiro:hasPermission name="shop:shop:shop:edit"><td>
    				<a href="${ctx}/shop/pos/pos/form?id=${pos.id}">修改</a>
					<a href="${ctx}/shop/pos/pos/delete?id=${pos.id}" onclick="return confirmx('确认要删除该pos机吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>