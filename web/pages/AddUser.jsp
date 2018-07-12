<%--
  Created by IntelliJ IDEA.
  User: VanXN
  Date: 2018/7/12
  Time: 17:12
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
    <title>新增用户</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
<%
    String status = request.getParameter("status");
    if (status != null) {
        if (status.equals("OK")) status = "添加成功";
        else if (status.equals("ERROR")) status = "添加失败";
    }
%>
<body class="body">


<form class="layui-form layui-form-pane" action="../api/addUser" method="post">
    <% if (status != null) {%>
    <div class="layui-form-item">
        <div class="layui-input-inline">
            <button class="layui-btn layui-btn-normal" disabled><%=status%></button>
        </div>
    </div>
    <% } %>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline">
            <input type="text" name="username" required autocomplete="off" placeholder="请输入用户名" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline">
            <input type="password" name="password" required placeholder="请输入密码" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-inline">
                <input type="email" name="email" required placeholder="请输入邮箱" lay-verify="email" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">头像链接</label>
            <div class="layui-input-inline">
                <input type="text" id="avatar" name="avatar" placeholder="请选择头像图片" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <button type="button" class="layui-btn" id="choose">
                <i class="layui-icon">&#xe67c;</i>选择图片
            </button>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <input type="submit" id="changeData" class="layui-btn layui-btn-normal" value="提交">
        </div>
    </div>
</form>

<script src="../frame/layui/layui.js" charset="utf-8"></script>
<script>

    layui.use('upload', function() {
        var upload = layui.upload;
        var $ = layui.jquery;

        upload.render({
            elem: '#choose'
            ,auto: false //选择文件后不自动上传
            ,choose: function(obj){
                obj.preview(function(index, file, result) {
                    $("#avatar").val('/images/user/' + file.name);
                });
            }
        });

    });
</script>
</body>
</html>

