<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商场管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $("#circleId").change(function(){
                var circleId = $(this).val();
                $.ajax({
                    url:"${ctx}/business/businesscircle/businessCircle/getCirclePCRByCircleId",
                    type:"POST",
                    data:{"circleId":circleId},
                    success:function(data){
                        $("#location").val(data);
                        console.log(data);
                    }
                });
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/business/businessmarket/businessMarket/">商场列表</a></li>
    <li class="active"><a
            href="${ctx}/business/businessmarket/businessMarket/form?id=${businessMarket.id}">商场<shiro:hasPermission
            name="business:businessmarket:businessMarket:edit">${not empty businessMarket.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="business:businessmarket:businessMarket:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="businessMarket" action="${ctx}/business/businessmarket/businessMarket/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="alert alert-success">
        <strong>注意：</strong>
        如商场不属于某商圈，选择默认的无所属商圈即可
    </div>
    <div class="control-group">
        <label class="control-label">所属商圈：</label>
        <div class="controls">
            <form:select path="circleId" id="circleId" class="input-large">
                <form:option value="" label="无所属商圈"/>
                <c:forEach items="${businessCircleList}" var="businessCircle">
                    <form:option value="${businessCircle.id}" label="${businessCircle.name}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商场名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商场介绍：</label>
        <div class="controls">
            <form:textarea path="description" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">省市区：</label>
        <div class="controls">
            <form:input path="location" id="location" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">详细地址：</label>
        <div class="controls">
            <form:textarea path="locationDetail" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">营业时间：</label>
        <div class="controls">
            <form:input path="openTime" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
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
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="business:businessmarket:businessMarket:edit"><input id="btnSubmit"
                                                                                       class="btn btn-primary"
                                                                                       type="submit"
                                                                                       value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>