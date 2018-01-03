<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商圈管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var location = $("#province").find("option:selected").text()+$("#city").find("option:selected").text()+$("#region").find("option:selected").text();
                    $("#location").val(location);
//                    console.log($("#location").val());
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

            var todoStr = "<option value=''>---请选择---</option>";
            $("#province").change(function () {
                $("#city").find("option").remove();
                $("#city").append(todoStr);
                $(".select2-chosen")[1].innerText = '---请选择---';
                $("#region").find("option").remove();
                $("#region").append(todoStr);
                $(".select2-chosen")[2].innerText = '---请选择---';
                var parentId = $(this).val();
                $.ajax({
                    url: "${ctx}/business/businesscircle/businessCircle/linkage",
                    type: "POST",
                    data: {"parentId": parentId},
                    success: function (data) {
                        var html = "";
                        for (var i = 0; i < data.length; i++) {
                            var name = data[i].name;
                            var id = data[i].id;
                            html += "<option value=" + id + ">" + name + "</option>";
                        }
                        $("#city").append(html);
                    }
                });
            });

            $("#city").change(function () {
                $("#region").find("option").remove();
                $("#region").append(todoStr);
                $(".select2-chosen")[2].innerText = '---请选择---';
                var parentId = $(this).val();
                $.ajax({
                    url: "${ctx}/business/businesscircle/businessCircle/linkage",
                    type: "POST",
                    data: {"parentId": parentId},
                    success: function (data) {
                        var html = "";
                        for (var i = 0; i < data.length; i++) {
                            var name = data[i].name;
                            var id = data[i].id;
                            html += "<option value=" + id + ">" + name + "</option>";
                        }
                        $("#region").append(html);
                    }
                });
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/business/businesscircle/businessCircle/">商圈列表</a></li>
    <li class="active"><a
            href="${ctx}/business/businesscircle/businessCircle/form?id=${businessCircle.id}">商圈<shiro:hasPermission
            name="business:businesscircle:businessCircle:edit">${not empty businessCircle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="business:businesscircle:businessCircle:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="businessCircle" action="${ctx}/business/businesscircle/businessCircle/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <form:hidden path="location" id="location"/>
    <div class="control-group">
        <label class="control-label">商圈名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商圈介绍：</label>
        <div class="controls">
            <form:textarea path="description" htmlEscape="false" rows="4" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商圈所属省：</label>
        <div class="controls">
            <form:select path="province" id="province" class="input-large required">
                <form:option value="" label="---请选择---"/>
                <c:forEach items="${provinceList}" var="province">
                    <form:option value="${province.id}" label="${province.name}"/>
                </c:forEach>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商圈所属市：</label>
        <div class="controls">
            <form:select path="city" id="city" class="input-large required">
                <option value="">---请选择---</option>
                <c:forEach items="${cityList}" var="city">
                    <form:option value="${city.id}" label="${city.name}"/>
                </c:forEach>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商圈所属区：</label>
        <div class="controls">
            <form:select path="region" id="region" class="input-large required">
                <option value="">---请选择---</option>
                <c:forEach items="${regionList}" var="region">
                    <form:option value="${region.id}" label="${region.name}"/>
                </c:forEach>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">详细地址：</label>
        <div class="controls">
            <form:textarea path="detailLocation" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="business:businesscircle:businessCircle:edit"><input id="btnSubmit"
                                                                                       class="btn btn-primary"
                                                                                       type="submit"
                                                                                       value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>