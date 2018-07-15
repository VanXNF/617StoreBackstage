<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="admin" class="bean.vanxnf.Admin" scope="session" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>首页 · 617STORE后台管理</title>
    <link rel="stylesheet" href="frame/layui/css/layui.css">
    <link rel="stylesheet" href="frame/static/css/style.css">
    <link rel="icon" href="images/favicon.png">
    <%  Object object = session.getAttribute("admin");
        if (object == null) {
            response.sendRedirect("Login.jsp");
        }
    %>
</head>
<body>
<!-- layout admin -->
<div class="layui-layout layui-layout-admin">
    <!-- header -->
    <div class="layui-header my-header">
        <a href="Main.jsp">
            <img id="logo" class="my-header-logo" alt="logo">
            <div class="my-header-logo">后台管理</div>
        </a>
        <div class="my-header-btn">
            <button class="layui-btn layui-btn-small btn-nav" style="background-color: #00000000">
                <i class="layui-icon" ><img id="nav_toggle" width="16px" height="16px"></i>
            </button>
        </div>

        <!-- 顶部右侧添加选项卡监听 -->
        <ul class="layui-nav my-header-user-nav" lay-filter="side-top-right">
            <%--主题切换--%>
            <li class="layui-nav-item">
                <a class="name" href="javascript:;"><i class="layui-icon">&#xe629;</i>主题</a>
                <dl class="layui-nav-child">
                    <dd data-skin="0"><a href="javascript:;">默认</a></dd>
                    <dd data-skin="1"><a href="javascript:;">纯白</a></dd>
                    <dd data-skin="2"><a href="javascript:;">蓝白</a></dd>
                </dl>
            </li>
            <%--账号操作--%>
            <li class="layui-nav-item">
                <a class="name" href="javascript:;">
                    <i class="layui-icon">&#9784;</i><jsp:getProperty name="admin" property="account"/>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="/"><i class="layui-icon">&#x1006;</i>退出</a></dd>
                </dl>
            </li>
        </ul>
    </div>
    <!-- side -->
    <div class="layui-side my-side">
        <div class="layui-side-scroll">
            <!-- 左侧主菜单添加选项卡监听 -->
            <ul class="layui-nav layui-nav-tree" lay-filter="side-main">
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="javascript:;"><i class="layui-icon">&#xe628;</i>管理商品</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" href-url="pages/AddGoods.jsp"><i class="layui-icon">&#xe621;</i>发布商品</a></dd>
                        <dd><a href="javascript:;" href-url="pages/GoodsList.jsp"><i class="layui-icon">&#xe621;</i>商品列表</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="layui-icon">&#xe628;</i>管理订单</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" href-url="pages/PaymentList.jsp"><i class="layui-icon">&#xe621;</i>历史订单</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="layui-icon">&#xe628;</i>管理用户</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" href-url="pages/AddUser.jsp"><i class="layui-icon">&#xe621;</i>新增用户</a></dd>
                        <dd><a href="javascript:;" href-url="pages/UserList.jsp"><i class="layui-icon">&#xe621;</i>用户列表</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- body -->
    <div class="layui-body my-body">
        <div class="layui-tab layui-tab-card my-tab" lay-filter="card" lay-allowClose="true">
            <ul class="layui-tab-title">
                <li class="layui-this" lay-id="1"><span><i class="layui-icon">&#xe638;</i>欢迎页</span></li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <iframe id="iframe" src="pages/Welcome.jsp" frameborder="0"></iframe>
                </div>
            </div>
        </div>
    </div>
    <!-- footer -->
    <div id="bodyFooter" class="layui-footer my-footer">
        <p>© 2018 浙江科技学院·信息与电子工程学院·软件工程 许楠钒 陈喆 </p>
    </div>
</div>

<!-- 右键菜单 -->
<div class="my-dblclick-box none">
    <table class="layui-tab dblclick-tab">
        <tr class="card-refresh">
            <td><i class="layui-icon">&#x1002;</i>刷新当前标签</td>
        </tr>
        <tr class="card-close">
            <td><i class="layui-icon">&#x1006;</i>关闭当前标签</td>
        </tr>
        <tr class="card-close-all">
            <td><i class="layui-icon">&#x1006;</i>关闭所有标签</td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="frame/layui/layui.js"></script>
<script type="text/javascript" src="frame/static/js/Backstage_comm.js"></script>
<script type="text/javascript">
layui.use(['layer','Backstage_nav'], function () {

    // 操作对象
    var layer = layui.layer
        ,BackstageNav = layui.Backstage_nav
        ,$ = layui.jquery;

    // 主体菜单生成 [请求地址,过滤ID,是否展开,携带参数]
    BackstageNav.main('./json/nav_main.json','side-main',true);

});
</script>
</body>
</html>