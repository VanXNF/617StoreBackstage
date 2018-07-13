<%--
  Created by IntelliJ IDEA.
  User: VanXN
  Date: 2018/7/11
  Time: 15:25
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
    <title>用户列表</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="icon" href="../images/favicon.png">
</head>
<body class="body">

<!-- 工具集 -->
<div class="layui-btn-group toolTable">
    <button class="layui-btn" data-type="getCheckData">获取选中行数据</button>
    <button class="layui-btn" data-type="getCheckLength">获取选中数目</button>
    <button class="layui-btn" id="btn-refresh">刷新</button>
</div>

<!-- 表格 -->
<div id="dataTable" class="layui-table" lay-filter="tables"></div>

<script type="text/javascript" src="../frame/layui/layui.js"></script>
<script type="text/javascript" src="../js/index.js"></script>
<script type="text/javascript">

    // layui方法
    layui.use(['table', 'form', 'layer', 'Backstage_table', 'Backstage_tab'], function () {

        // 操作对象
        var table = layui.table
            , layer = layui.layer
            , BackstageTable = layui.Backstage_table
            , $ = layui.jquery
            , BackstageTab = layui.Backstage_tab;

        // 表格渲染
        var tableIns = table.render({
            elem: '#dataTable'                  //指定原始表格元素选择器（推荐id选择器）
            , cols: [[                  //标题栏
                {checkbox: true, sort: true, fixed: true, space: true}
                , {field: 'id', title: 'ID', width: 80, sort:true}
                , {field: 'username', title: '用户名', width: 150}
                , {field: 'avatar', title: '头像', width: 200}
                , {field: 'email', title: '邮箱', width: 200}
                , {field: 'registerdate', title: '注册时间', width: 160, sort:true}
                , {fixed: 'right', title: '隐藏', width: 160, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
                , {fixed: 'right', title: '编辑', width: 160, align: 'center', toolbar: '#go'}
            ]]
            , id: 'UserLists'
            , url: '/api/userList'
            , method: 'get'
            , page: true
            , limits: [5, 10, 15, 20, 30]
            , limit: 15 //默认采用5
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
            if(obj.event === 'hide') {
                layer.confirm('暂时隐藏此数据？', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit') {
                BackstageTab.add($(this), "编辑"+ data[''+'id'+''] +"号用户信息", '/api/userEdit?id='+ data[''+'id'+'']);
            }
        });

        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('UserLists')
                    ,data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
            ,getCheckLength: function(){ //获取选中数目
                var checkStatus = table.checkStatus('UserLists')
                    ,data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
            }
        };

        $('.toolTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
</script>

<!-- 表格操作按钮集 -->
<script type="text/html" id="barOption">
    <a class="layui-btn layui-btn-small layui-btn-danger" lay-event="hide">隐藏</a>
</script>

<script type="text/html" id="go">
    <button type="button" class="layui-btn layui-btn-small add-tab2" lay-event="edit">编辑</button>
</script>

</body>
</html>