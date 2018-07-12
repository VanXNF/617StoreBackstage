<%@ page import="bean.vanxnf.Commodity" %>
<%--
  Created by IntelliJ IDEA.
  User: VanXN
  Date: 2018/7/12
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>编辑用户信息</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
<%--<%  String id = request.getParameter("id");--%>
    <%--String status = request.getParameter("status");--%>
    <%--Object obj = session.getAttribute("user"+id);--%>
    <%--Commodity user = null;--%>
    <%--if (obj instanceof User) {--%>
        <%--user = (User) obj;--%>
    <%--}--%>
    <%--if (status != null) {--%>
        <%--if (status.equals("OK")) status = "更改成功";--%>
        <%--else if (status.equals("ERROR")) status = "更改失败";--%>
    <%--}--%>
<%--%>--%>
<body class="body">

<%--<% if (user != null) {%>--%>
<%--<form id="dataForm" name="dataForm" class="layui-form layui-form-pane" action="../api/userEdit" method="post">--%>
    <%--<% if (status != null) {%>--%>
    <%--<div class="layui-form-item">--%>
        <%--<div class="layui-input-inline">--%>
            <%--<button class="layui-btn layui-btn-normal" disabled><%=status%></button>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<% } %>--%>
    <%--<div class="layui-form-item">--%>
        <%--<label class="layui-form-label">用户 ID</label>--%>
        <%--<div class="layui-input-inline">--%>
            <%--<input type="text" name="userID" value="<%=user.getId()%>" autocomplete="off" class="layui-input layui-disabled">--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
        <%--<label class="layui-form-label">用户名</label>--%>
        <%--<div class="layui-input-inline">--%>
            <%--<input type="text" name="username" autocomplete="off" value="<%=user.getUsername()%>" class="layui-input">--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
        <%--<label class="layui-form-label">密码</label>--%>
        <%--<div class="layui-input-inline">--%>
            <%--<input type="password" name="password" value="<%=user.getPassword()%>" autocomplete="off" class="layui-input">--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
        <%--<div class="layui-inline">--%>
            <%--<label class="layui-form-label">邮箱</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input type="text" name="email" value="<%=user.getEmail()%>" lay-verify="email" autocomplete="off" class="layui-input">--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
        <%--<div class="layui-inline">--%>
            <%--<label class="layui-form-label">头像链接</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input type="text" id="avatar" name="avatar" value="<%=user.getAvatar()%>" lay-verify="required"  autocomplete="off" class="layui-input">--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="layui-inline">--%>
            <%--<button type="button" class="layui-btn" id="choose">--%>
                <%--<i class="layui-icon">&#xe67c;</i>选择图片--%>
            <%--</button>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
        <%--<div class="layui-inline">--%>
            <%--<label class="layui-form-label">注册时间</label>--%>
            <%--<div class="layui-input-block">--%>
                <%--<input type="text" name="date" value="<%=user.getRegisterdate().toString()%>" autocomplete="off" class="layui-input layui-disabled" disabled>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</form>--%>
<%--<div class="layui-form-item">--%>
    <%--<div class="layui-inline">--%>
        <%--<input form="dataForm" type="submit" id="changeData" class="layui-btn layui-btn-normal" value="提交修改">--%>
    <%--</div>--%>
    <%--<div class="layui-inline">--%>
        <%--<button id="deleteUser" class="layui-btn layui-btn-danger">删除用户</button>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<form id="deleteForm" action="../api/userDelete" method="post">--%>
    <%--<input type="hidden" name="id" value="<%=user.getId()%>">--%>
<%--</form>--%>


<%--<script src="../frame/layui/layui.js" charset="utf-8"></script>--%>
<%--<script>--%>

    <%--layui.use(['upload', 'layer'], function() {--%>
        <%--var upload = layui.upload;--%>
        <%--var $ = layui.jquery;--%>

        <%--upload.render({--%>
            <%--elem: '#choose'--%>
            <%--,auto: false //选择文件后不自动上传--%>
            <%--,choose: function(obj){--%>
                <%--obj.preview(function(index, file, result) {--%>
                    <%--$("#avatar").val('/images/user/' + file.name);--%>
                <%--});--%>
            <%--}--%>
        <%--});--%>

        <%--var layer = layui.layer;--%>

        <%--$("#deleteUser").on("click", function () {--%>
            <%--layer.confirm('是否删除该用户数据？', function(index) {--%>
                <%--$("#deleteForm").submit();--%>
                <%--layer.close(index);--%>
                <%--return false;--%>
            <%--});--%>
        <%--});--%>

    <%--});--%>
<%--</script>--%>
<%--<% } else {%>--%>
<%--<div class="my-page-box">--%>
    <%--<h3>404</h3>--%>
    <%--<p class="msg">Page Not Found</p>--%>
    <%--<p class="text">对不起,没有找到您所需要的页面,可能是URL不确定,或者页面已被移除。</p>--%>
<%--</div>--%>
<%--<% } %>--%>
</body>
</html>