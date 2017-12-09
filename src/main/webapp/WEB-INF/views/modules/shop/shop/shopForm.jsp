<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/shop/shop/">商户列表</a></li>
		<li class="active"><a href="${ctx}/shop/shop/shop/form?id=${shop.id}">商户<shiro:hasPermission name="shop:shop:shop:edit">${not empty shop.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:shop:shop:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shop" action="${ctx}/shop/shop/shop/save" method="post" enctype="multipart/form-data" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人姓名：</label>
			<div class="controls">
				<form:input path="contactName" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
					<label class="control-label">所属行业:</label>
					<div class="controls">
						<sys:treeselect id="category" name="category.id" value="${shop.category.id}" labelName="category.name" labelValue="${shop.category.name}"
										title="所属行业" url="/base/categories/categories/treeData" extId="${shop.category.id}" cssClass="" allowClear="true"/>
					</div>
			</div>
		<div class="control-group">
			<label class="control-label">营业执照：</label>
			<c:if test="${shop.idPhotos!=null && shop.idPhotos !=''}">
				<img src="${license}">
			</c:if>
			<div class="controls">
				<input type="file" name="license"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证正面：</label>
			<c:if test="${shop.idPhotos!=null && shop.idPhotos !=''}">
				<img src="${idCardFront}">
			</c:if>
			<div class="controls">
				<input type="file" name="idCardFront"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证反面：</label>
			<c:if test="${shop.idPhotos!=null && shop.idPhotos !=''}">
				<img src="${idCardContrary}">
			</c:if>
			<div class="controls">
				<input type="file" name="idCardContrary"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业时间：</label>
			<div class="controls">
				<form:input path="openTime" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:shop:shop:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>