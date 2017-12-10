<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门店管理</title>
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

			$("#side").change(function(){
			    var side = $(this).val();
				if(side == 2){
					//选择是的无商场
					$("#circle").attr("disabled",true);
					$("#circleDiv").attr("disabled",true);
					$("#market").attr("disabled",true);
					$("#marketDiv").attr("disabled",true);
					$("#marketFloor").attr("disabled",true);
					$("#marketFloorDiv").attr("disabled",true);
				}else if(side == 1){
                    $("#marketFloor").attr("disabled",true);
                    $("#marketFloorDiv").attr("disabled",true);
                    $("#circleDiv").show();
                    $("#marketDiv").show();
                }else{
                    $("#circleDiv").show();
                    $("#marketDiv").show();
                    $("#marketFloorDiv").show();
				}
			});
			//联动
            var todoStr = "<option value=''>---请选择---</option>";
            $("#circle").change(function () {
				var circleId = $(this).val();
				var side = $("#side").val();
				if(side == 0 || side == ''){
                    $(".select2-chosen")[3].innerText = '---请选择---';
                    $(".select2-chosen")[4].innerText = '---请选择---';
				}else if(side == 1){
                    $(".select2-chosen")[3].innerText = '---请选择---';
                }else if(side == 2){
				    return ;
				}
                $("#market").find("option").remove();
                $("#market").append(todoStr);
                $("#marketFloor").find("option").remove();
                $("#marketFloor").append(todoStr);
                $.ajax({
                    url: "/a/business/businessmarket/businessMarket/getMarketByCircleId",
                    type: "POST",
                    data: {"circleId": circleId},
                    success: function (data) {
                        var html = "";
                        for (var i = 0; i < data.length; i++) {
                            var name = data[i].name;
                            var id = data[i].id;
                            html += "<option value=" + id + ">" + name + "</option>";
                        }
                        $("#market").append(html);
                    }
                });
                });

                $("#market").change(function () {
                    $("#marketFloor").find("option").remove();
                    $("#marketFloor").append(todoStr);
                    var side = $("#side").val();
                    if(side == 0 || side == ''){
                        $(".select2-chosen")[4].innerText = '---请选择---';
                    }else if(side == 1){
                        return ;
                    }else if(side == 2){
                        return ;
                    }
                    var marketId = $(this).val();
                    $.ajax({
                        url: "/a/business/businessmarket/businessMarket/getFloorListByMarketId",
                        type: "GET",
                        data: {"marketId": marketId},
                        success: function (data) {
                            var html = "";
                            for (var i = 0; i < data.length; i++) {
                                var name = data[i].name;
                                var id = data[i].id;
                                html += "<option value=" + id + ">" + name + "</option>";
                            }
                            $("#marketFloor").append(html);
                        }
                    });
                });
		})
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/branch/shopBranch/">门店列表</a></li>
		<li class="active"><a href="${ctx}/shop/branch/shopBranch/form?id=${shopBranch.id}">门店<shiro:hasPermission name="shop:branch:shopBranch:edit">${not empty shopBranch.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:branch:shopBranch:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopBranch" action="${ctx}/shop/branch/shopBranch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属商户：</label>
			<div class="controls">
				<form:select path="shop.id" htmlEscape="false" maxlength="64" class="input-xlarge required">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${shopList}" var="s">
						<option <c:if test="${shopBranch.shop.id == s.id}" >selected="selected"</c:if> value="${s.id}">${s.name}</option>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门店位置：</label>
			<div class="controls">
				<form:select id="side" path="side" class="input-xlarge required">
					<form:option value="" label="---请选择---"/>
					<form:options items="${fns:getDictList('shop_side')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="circleDiv">
			<label class="control-label">所属商圈：</label>
			<div class="controls">
				<form:select id="circle"  path="circle.id" htmlEscape="false" maxlength="64" class="input-xlarge ">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${circleList}" var="c">
						<option <c:if test="${shopBranch.circle.id == c.id}" >selected="selected"</c:if> value="${c.id}">${c.name}</option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="marketDiv">
			<label class="control-label">所属商场：</label>
			<div class="controls">
				<form:select  id="market" path="market.id" htmlEscape="false" maxlength="64" class="input-xlarge ">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${marketList}" var="m">
						<option <c:if test="${shopBranch.market.id == m.id}" >selected="selected"</c:if> value="${m.id}">${m.name}</option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="marketFloorDiv">
			<label class="control-label">所属商场楼层：</label>
			<div class="controls">
				<form:select id="marketFloor" path="marketFloor.id" htmlEscape="false" maxlength="64" class="input-xlarge ">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${marketFloorList}" var="f">
						<option <c:if test="${shopBranch.marketFloor.id == f.id}" >selected="selected"</c:if> value="${f.id}">${f.name}</option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门店名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址：</label>
			<div class="controls">
				<form:textarea path="locationDetail" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人姓名：</label>
			<div class="controls">
				<form:input path="contactName" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:branch:shopBranch:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>