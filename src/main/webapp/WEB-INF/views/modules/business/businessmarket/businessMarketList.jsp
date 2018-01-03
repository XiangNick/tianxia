<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商场管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {


        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function saveUpdateFloor() {
            var floor = $("#upFloor").val().trim();
            if (floor == '') {
                alertx("楼层号必填");
                return;
            }
            if ($("#upName").val().trim() == '') {
                alertx("楼层名必填");
                return;
            }
            if (!/^[0-9]*[1-9][0-9]*$/.test(floor)) {
                alertx("楼层号请输入数字");
                return;
            }
            $("#updateForm").submit();
            alertx("修改楼层成功!");
        }

        function addFloor(marketId) {
            var html = "<form id='saveForm'  action='${ctx}/business/businessmarket/businessMarket/saveMarketFloor' method='post'><input type='hidden' name='marketId' value='" + marketId + "'/> <br/>楼层号：<input type='text' id='addFloor' name='floor'/>&nbsp;[必填]<br/>楼层名：<input type='text' id='addName' name='name'/>&nbsp;[必填]<br/>该楼层类别：<input type='text' id='addCategory' name='category'/><br/>备注信息：<textarea id='addRemarks' name='remarks'></textarea></form>";
            $("#modal-body").html(html);
            $("#floorFooter").html("<button class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">关闭</button><button class=\"btn btn-primary\" onclick=\"saveFloor()\">新增</button>");
        }

        function saveFloor() {
            var floor = $("#addFloor").val().trim();
            if (floor == '') {
                alertx("楼层号必填");
                return;
            }
            if ($("#addName").val().trim() == '') {
                alertx("楼层名必填");
                return;
            }
            if (!/^[0-9]*[1-9][0-9]*$/.test(floor)) {
                alertx("楼层号请输入数字");
                return;
            }
            $("#saveForm").submit();
            alertx("新增楼层成功!");
        }

        function listFloor(marketId) {
            $.ajax({
                url: "${ctx}/business/businessmarket/businessMarket/getFloorListByMarketId",
                type: "get",
                data: {"marketId": marketId},
                success: function (data) {
                    var html = '<table id="floorTable" class="table table-striped table-bordered table-condensed"><thead><tr><th>楼层号</th><th>楼层名</th><th>该楼层类别</th><th>更新时间</th><th>备注信息</th><th>操作</th></tr></thead><tbody id="floorBody">';
                    for (var i = 0; i < data.length; i++) {
                        var str = data[i];
                        html += '<tr><td>' + str.floor + '</td>' + '<td>' + str.name + '</td>' + '<td>' + str.category + '</td>' + '<td>' + str.updateDate + '</td>' + '<td>' + str.remarks + '</td><td><a class="floorUpdateBtn" onclick=editFloor("' + str.id + '") href="javascript:;">修改</a>&nbsp;<a class="floorDelBtn" onclick=delFloor("' + str.id + '") href="javascript:;">删除</a></td></tr>';
                    }
                    html += '</tbody></table>';
                    $("#modal-body").html(html);
                    $("#floorFooter").html("<button class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">关闭</button><button class=\"btn btn-primary\" onclick=\"addFloor('" + marketId + "')\">新增楼层</button>");
                }
            });
            $('#myModal').modal();
        }

        function editFloor(id) {
            $.ajax({
                url: "${ctx}/business/businessmarket/businessMarket/getFloorById",
                data: {"floorId": id},
                type: "get",
                success: function (data) {
                    var html = "<form id='updateForm' action='${ctx}/business/businessmarket/businessMarket/updateFloorByCondition' method='post'><input type='hidden' name='id' value='" + data.id + "'/><br/>楼层号：<input type='text' id='upFloor' name='floor' value='" + data.floor + "'/><br/>楼层名：<input type='text' id='upName' name='name' value='" + data.name + "'/><br/>该楼层类别：<input type='text' name='category' value='" + data.category + "'/><br/>备注信息：<textarea name='remarks'>" + data.remarks + "</textarea></form>";
                    $("#modal-body").html(html);
                    $("#floorFooter").html("<button class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">关闭</button><button class=\"btn btn-primary\" onclick=\"saveUpdateFloor()\">保存修改</button>");
                }
            });
        }

        function delFloor(id) {
            var r = confirm("确定要删除吗?");
            if (r) {
                $.ajax({
                    url: "${ctx}/business/businessmarket/businessMarket/delFloor",
                    data: {"floorId": id},
                    type: "get",
                    success: function (data) {
                        alertx('删除楼层成功!');
                        $('#myModal').modal('hide');
                    }
                });
            }
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/business/businessmarket/businessMarket/">商场列表</a></li>
    <shiro:hasPermission name="business:businessmarket:businessMarket:edit">
        <li><a href="${ctx}/business/businessmarket/businessMarket/form">商场添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="businessMarket" action="${ctx}/business/businessmarket/businessMarket/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>商场名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>
        <li><label>商圈名称：</label>
            <form:input path="circleName" htmlEscape="false" maxlength="200" class="input-medium"/>
        </li>
        <li><label>省市区：</label>
            <form:input path="location" htmlEscape="false" maxlength="200" class="input-medium"/>
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
        <shiro:hasPermission name="business:businessmarket:businessMarket:edit">
            <th>操作</th>
        </shiro:hasPermission>
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
            <input type="hidden" id="marketId" value="${businessMarket.id}"/>
            <shiro:hasPermission name="business:businessmarket:businessMarket:edit">
                <td>
                    <a href="${ctx}/business/businessmarket/businessMarket/form?id=${businessMarket.id}">修改</a>
                    <a href="${ctx}/business/businessmarket/businessMarket/delete?id=${businessMarket.id}"
                       onclick="return confirmx('确认要删除该商场吗？', this.href)">删除</a>
                    <a onclick="listFloor('${businessMarket.id}')" href="#" data-toggle="modal">楼层管理</a>

                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">${businessMarket.name}</h3>
    </div>
    <div class="modal-body" id="modal-body">

    </div>
    <div class="modal-footer" id="floorFooter">
    </div>
</div>
<div class="pagination">${page}</div>
</body>
</html>