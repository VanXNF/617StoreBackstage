<%@ page import="bean.vanxnf.Commodity" %>
<%@ page import="bean.vanxnf.Parameter" %>
<%--
  Created by IntelliJ IDEA.
  User: VanXN
  Date: 2018/7/13
  Time: 18:41
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
    <title>发布新商品</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
<%
    String status = request.getParameter("status");
    if (status != null) {
        if (status.equals("OK")) status = "发布成功";
        else if (status.equals("ERROR")) status = "发布失败";
        else if (status.equals("ID_ERROR")) status = "请求分配商品ID失败";
    }
%>
<body class="body">


<form id="dataForm" class="layui-form layui-form-pane" action="../api/goodsAdd" method="post">
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
            <input type="text" id="commodityID" name="commodityID" value="" autocomplete="off" class="layui-input layui-disabled">
        </div>
        <button type="button" class="layui-btn" id="requestID">
            <i class="layui-icon">&#43;</i>点击分配ID
        </button>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品标题</label>
        <div class="layui-input-block">
            <input type="text" name="commodityTitle" autocomplete="off" placeholder="请输入商品标题" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">原价</label>
            <div class="layui-input-inline">
                <input type="text" name="OPrice" placeholder="请输入商品原价" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">现价</label>
            <div class="layui-input-inline">
                <input type="text" name="DPrice" placeholder="请输入商品折扣价" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">简介</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea" name="quickView" placeholder="请输入商品简介"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">详情图片</label>
            <div class="layui-input-inline">
                <input type="text" id="overview" name="overview" placeholder="请选择商品详情图片" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
            <button type="button" class="layui-btn" id="chooseOverview">
                <i class="layui-icon">&#xe67c;</i>选择图片
            </button>
        </div>
    </div>
    <div class="layui-form-item">
        <% for (int i = 0; i < 5; i++) {%>
        <div class="layui-inline">
            <label class="layui-form-label">主图 <%=i+1%></label>
            <div class="layui-input-inline">
                <input type="text" id="mainPic<%=i%>" name="mainPic<%=i%>" placeholder="请选择图片" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
            <button type="button" class="layui-btn" id="chooseMainPic<%=i%>">
                <i class="layui-icon">&#xe67c;</i>选择商品主图
            </button>
        </div>
        <% } %>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <input type="submit" class="layui-btn layui-btn-normal" value="发布商品">
        </div>
    </div>
</form>

<script src="../frame/layui/layui.js" charset="utf-8"></script>
<script>

    layui.use(['upload', 'layer'], function() {
        var upload = layui.upload;
        var $ = layui.jquery;
        var layer = layui.layer;

        $('#requestID').on('click', function () {
            $.ajax({
                type:'get',
                url: "/api/goodsAdd",
                dataType:'text',
                success: function(result) {
                    $('#commodityID').val(result);
                    $('#requestID').attr("disabled", 'disabled');
                    $('#requestID').addClass("layui-disabled");
                }
            });
        });

        // 监听overview
        upload.render({
            elem: '#chooseOverview'
            ,auto: false //选择文件后不自动上传
            ,choose: function(obj) {
                obj.preview(function(index, file, result) {
                    $("#overview").val('/images/commodity/'+$('#commodityID').val()+'/' + file.name);
                });
            }
        });

        // 监听主图
        <% for (int i = 0; i < 5; i++) {%>
        upload.render({
            elem: '#chooseMainPic<%=i%>'
            ,auto: false //选择文件后不自动上传
            ,choose: function(obj){
                obj.preview(function(index, file, result) {
                    $("#mainPic<%=i%>").val('/images/commodity/'+$('#commodityID').val()+'/' + file.name);
                });
            }
        });
        <% } %>

        $("#deleteGoods").on("click", function () {
            layer.confirm('是否删除该商品数据？', function(index) {
                $("#deleteForm").submit();
                layer.close(index);
                return false;
            });
        });

    });
</script>
</body>
</html>
