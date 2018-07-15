<%--
  Created by IntelliJ IDEA.
  User: VanXN
  Date: 2018/7/15
  Time: 8:57
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
    <title>订单详情</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
<% String id = request.getParameter("id");%>
<body class="body">

<!-- 工具集 -->
<div class="layui-btn-group toolTable">
    <button class="layui-btn" data-type="getCheckData">获取选中行数据</button>
    <button class="layui-btn" id="btn-refresh">刷新</button>
</div>

<!-- 表格 -->
<table id="dataTable" class="layui-table" lay-filter="tables"></table>

<script type="text/javascript" src="../frame/layui/layui.js"></script>
<script type="text/javascript" src="../js/index.js"></script>
<script type="text/javascript">

    // layui方法
    layui.use(['table', 'form', 'layer', 'Backstage_table', 'Backstage_tab'], function () {

        // 操作对象
        var table = layui.table
            , layer = layui.layer
            , $ = layui.jquery;

        // 表格渲染
        var tableIns = table.render({
            elem: '#dataTable'                  //指定原始表格元素选择器（推荐id选择器）
            , cols: [[                  //标题栏
                {checkbox: true, sort: true, fixed: true, space: true}
                , {field: 'commodityId', title: '商品ID', width: 160, sort:true, align:'center'}
                , {field: 'title', title: '商品名', width: 200, align:'center'}
                , {field: 'quantity', title: '数量', width: 80, sort:true, align:'center'}
                , {field: 'sumPrice', title: '金额', width: 100, align:'center'}
                , {field: 'attrWithImage', title: '带图属性', width: 200, align:'center'}
                , {field: 'attrWithoutImage', title: '无图属性', width: 200, align:'center'}
            ]]
            , id: 'OrderDetail'
            , url: '/api/orderDetail?id='+<%=id%>
            , method: 'get'
            , page: true
            , limits: [5, 10, 15, 20, 30]
            , limit: 15 //默认采用15
            , loading: true
            , done: function (res, curr, count) {

            }
        });

        // 刷新
        $('#btn-refresh').on('click', function () {
            tableIns.reload();
        });

        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('OrderDetail')
                    ,data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
        };

        $('.toolTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
</script>

</body>
</html>
