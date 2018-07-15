<%--
  Created by IntelliJ IDEA.
  User: VanXN
  Date: 2018/7/15
  Time: 1:06
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
    <title>历史订单</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
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
            , $ = layui.jquery
            , BackstageTab = layui.Backstage_tab;

        // 表格渲染
        var tableIns = table.render({
            elem: '#dataTable'                  //指定原始表格元素选择器（推荐id选择器）
            , cols: [[                  //标题栏
                {checkbox: true, sort: true, fixed: true, space: true}
                , {field: 'id', title: 'ID', width: 80, sort:true, align:'center'}
                , {field: 'payDate', title: '支付时间', width: 200, sort:true, align:'center'}
                , {field: 'price', title: '价格', width: 100, sort:true, align:'center'}
                , {field: 'userId', title: '用户 ID', width: 80, align:'center'}
                , {field: 'userName', title: '用户名', width: 160, align:'center'}
                , {field: 'email', title: '邮箱', width: 160, align:'center'}
                , {fixed: 'right', title: '编辑', width: 160, align: 'center', toolbar: '#go'}
            ]]
            , id: 'PaymentLists'
            , url: '/api/paymentList'
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

        table.on('tool(tables)', function(obj) {
            var data = obj.data;
            if(obj.event === 'show') {
                BackstageTab.add($(this), data[''+'id'+''] +"号订单", '../pages/PaymentDetail.jsp?id='+ data[''+'id'+'']);
            }
        });

        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('PaymentLists')
                    ,data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
        };

        $('.toolTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        console.log($(".layui-tab-title .layui-this").attr("text"))

    });
</script>

<!-- 表格操作按钮集 -->

<script type="text/html" id="go">
    <button type="button" class="layui-btn layui-btn-small add-tab2" lay-event="show">查看详情</button>
</script>

</body>
</html>
