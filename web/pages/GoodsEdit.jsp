<%@ page import="bean.vanxnf.Commodity" %>
<%@ page import="bean.vanxnf.Parameter" %>
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
    <title>编辑商品信息</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
<%
    String id = request.getParameter("id");
    Commodity commodity = null;
    Parameter parameter = null;
    Object objectC = session.getAttribute("Commodity" + id);
    Object objectP = session.getAttribute("Parameter" + id);
    if (objectC instanceof Commodity) {
        commodity = (Commodity) objectC;
    }
    if (objectP instanceof Parameter) {
        parameter = (Parameter) objectP;
    }
    String status = request.getParameter("status");
    if (status != null) {
        if (status.equals("OK")) status = "更改成功";
        else if (status.equals("ERROR")) status = "更改失败";
    }
%>
<body class="body">

<% if (commodity != null && parameter != null) {%>
<form id="dataForm" name="dataForm" class="layui-form layui-form-pane" action="../api/goodsEdit" method="post">
    <% if (status != null) {%>
    <div class="layui-form-item">
        <div class="layui-input-inline">
            <button class="layui-btn layui-btn-normal" disabled><%=status%></button>
        </div>
    </div>
    <% } %>
    <div class="layui-form-item">
        <label class="layui-form-label">商品 ID</label>
        <div class="layui-input-inline">
            <input type="text" name="userID" value="<%=commodity.getId()%>" autocomplete="off" class="layui-input layui-disabled">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品标题</label>
        <div class="layui-input-block">
            <input type="text" name="title" autocomplete="off" value="<%=commodity.getTitle()%>" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">原价</label>
            <div class="layui-input-inline">
                <input type="text" name="OPrice" value="<%=commodity.getOriginalPrice()%>" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">现价</label>
            <div class="layui-input-inline">
                <input type="text" name="DPrice" value="<%=commodity.getDiscountPrice()%>" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">简介</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入内容" class="layui-textarea"><%=commodity.getQuickReview()%></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">详细介绍</label>
            <div class="layui-input-inline">
                <input type="text" id="overview" name="overview" placeholder="请选择图片" value="<%=commodity.getOverview()%>" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
            <button type="button" class="layui-btn" id="chooseOverview">
                <i class="layui-icon">&#xe67c;</i>选择图片
            </button>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">销量</label>
            <div class="layui-input-inline">
                <input type="text" name="saleVolume" value="<%=commodity.getSaleVolume()%>" autocomplete="off" class="layui-input layui-disabled" disabled>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发布时间</label>
            <div class="layui-input-block">
                <input type="text" name="date" value="<%=commodity.getDate()%>" autocomplete="off" class="layui-input layui-disabled" disabled>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <% for (int i = 1; i <= 5; i++) {%>
        <div class="layui-inline">
            <label class="layui-form-label">主图<%=i%></label>
            <div class="layui-input-inline">
                <input type="text" id="mainPic<%=i%>" name="mainPic<%=i%>" placeholder="请选择图片" value="<%=commodity.getMainImage().get(i-1)%>" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
            <button type="button" class="layui-btn" id="chooseMainPic<%=i%>">
                <i class="layui-icon">&#xe67c;</i>选择商品主图
            </button>
        </div>
        <% } %>
    </div>
    <div class="layui-form-item">

    </div>
</form>
<div class="layui-form-item">
    <div class="layui-inline">
        <input form="dataForm" type="submit" id="changeData" class="layui-btn layui-btn-normal" value="提交修改">
    </div>
    <div class="layui-inline">
        <button id="deleteGoods" class="layui-btn layui-btn-danger">删除商品</button>
    </div>
</div>
<form id="deleteForm" action="" method="post">
    <input type="hidden" name="id" value="">
</form>


<script src="../frame/layui/layui.js" charset="utf-8"></script>
<script>

    layui.use(['upload', 'layer'], function() {
        var upload = layui.upload;
        var $ = layui.jquery;

        upload.render({
            elem: '#chooseOverview'
            ,auto: false //选择文件后不自动上传
            ,choose: function(obj){
                obj.preview(function(index, file, result) {
                    $("#overview").val('/images/commodity/'+'<%=commodity.getId()%>'+'/' + file.name);
                });
            }
        });

        <% for (int i = 1; i <= 5; i++) {%>
            upload.render({
                elem: '#chooseMainPic<%=i%>'
                ,auto: false //选择文件后不自动上传
                ,choose: function(obj){
                    obj.preview(function(index, file, result) {
                        $("#mainPic<%=i%>").val('/images/commodity/'+'<%=commodity.getId()%>'+'/' + file.name);
                    });
                }
            });
        <% } %>
        var layer = layui.layer;

        $("#deleteGoods").on("click", function () {
            layer.confirm('是否删除该商品数据？', function(index) {
                $("#deleteForm").submit();
                layer.close(index);
                return false;
            });
        });

    });
</script>
<% } else {%>
<div class="my-page-box">
    <h3>404</h3>
    <p class="msg">Page Not Found</p>
    <p class="text">对不起,没有找到您所需要的页面,可能是URL不确定,或者页面已被移除。</p>
</div>
<% } %>
</body>
</html>