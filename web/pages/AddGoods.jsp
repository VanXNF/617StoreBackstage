<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.vanxnf.Attribute" %><%--
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
    ArrayList<Attribute> attributes = null;
    Object object = session.getAttribute("Attribute");
    if (object instanceof ArrayList) {
        attributes = (ArrayList<Attribute>) object;
    }
    if (attributes == null) {
        response.sendRedirect("../api/goodsAttribute?page=AddGoods");
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
            <input type="text" id="commodityID" placeholder="请先获取ID" autocomplete="off" class="layui-input layui-disabled" disabled>
        </div>
        <input type="hidden" name="commodityID" id="cID">
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

    <% if (attributes != null) {%>
    <div class="layui-form-item">
        <label class="layui-form-label">带图属性</label>
        <div class="layui-input-inline">
            <select id="attrImageSelect">
                <option value="" selected></option>
                <% for (int i = 0; i < attributes.size(); i++) {%>
                    <% if (attributes.get(i).getImageFlag() == 1) {%>
                    <option value="<%=attributes.get(i).getId()%>"><%=attributes.get(i).getAttribute()%></option>
                    <% } %>
                <% } %>
            </select>
        </div>
        <div class="layui-input-inline">
            <button type="button" class="layui-btn layui-btn-normal" id="chooseImageAttr">添加</button>
        </div>
    </div>
    <div class="layui-form-item" id="imageItem">
        <input type="hidden" id="imageParam" name="imageParam">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">无图属性</label>
        <div class="layui-input-inline">
            <select id="attrSelect">
                <option value="" selected></option>
                <% for (int i = 0; i < attributes.size(); i++) {%>
                    <% if (attributes.get(i).getImageFlag() == 0) {%>
                    <option value="<%=attributes.get(i).getId()%>"><%=attributes.get(i).getAttribute()%></option>
                    <% } %>
                <% } %>
            </select>
        </div>
        <div class="layui-input-inline">
            <button type="button" class="layui-btn layui-btn-normal" id="chooseAttr">添加</button>
        </div>
    </div>
    <div class="layui-form-item" id="noneImageItem">
        <input type="hidden" id="noneImageParam" name="noneImageParam">
    </div>
    <% } %>
</form>
<div style="margin-bottom: 5%">
    <div class="layui-inline">
        <button id="release" type="button" class="layui-btn layui-btn-normal">发布商品</button>
    </div>
</div>

<script src="../frame/layui/layui.js" charset="utf-8"></script>
<script>

    layui.use(['upload', 'layer','form'], function() {
        var upload = layui.upload;
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layer.form;
        var imageNum = 0;
        var noneImageNum = 0;

        $('#requestID').on('click', function () {
            $.ajax({
                type:'get',
                url: "/api/goodsAdd",
                dataType:'text',
                success: function(result) {
                    $('#commodityID').val(result);
                    $('#cID').val(result);
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

        $('#chooseImageAttr').on('click', function () {
            var attrId = $('#attrImageSelect').val();//属性id
            var attr = $('#attrImageSelect').find('option:selected').text();//属性名
            // 非空时有效
            if (attr !== "") {
                var ele = '<div class="layui-inline">\n' +
                    '<label class="layui-form-label">'+ attr +'</label>\n' +
                    '<input class="layui-input" type="hidden" value="'+ attrId +'#'+ imageNum + '">'+
                    '<div class="layui-input-inline"><input type="text" name="'+ attrId +'value'+ imageNum + '" placeholder="请输入属性值" lay-verify="required"  autocomplete="off" class="layui-input"></div>\n' +
                    '<div class="layui-input-inline" ><input type="text" id="file'+ imageNum +'" name="'+ attrId +'image'+ imageNum + '" placeholder="请选择图片" lay-verify="required"  autocomplete="off" class="layui-input image"></div>\n' +
                    '<label for="'+ imageNum +'" class="layui-btn layui-btn-small" style="margin-top: 5px"><input type="file" class="file" style="display: none" id="'+ imageNum +'"><i class="layui-icon">&#xe67c;</i></label>\n' +
                    '<button type="button" class="layui-btn layui-btn-danger layui-btn-small" style="margin-top: 5px"><i class="layui-icon">&#x1006;</i></button>\n' +
                    '</div>';

                $("#imageItem").append(ele);
                imageNum = imageNum + 1;
            }

        });

        $('#imageItem').on('click', '.layui-btn.layui-btn-danger.layui-btn-small', function(){
            $(this).parent().remove();
        });

        $('#imageItem').on('click', '.layui-btn.layui-btn-small', function() {
            var id = $(this).children('.file').attr('id');
            var linkId = '#file'+ id;
            $('#'+id).on('change', function (e) {
                $(linkId).val('/images/commodity/'+$('#commodityID').val()+'/' + e.currentTarget.files[0].name);
            })


        });

        $('#chooseAttr').on('click', function () {
            var attrId = $('#attrSelect').val();
            var attr = $('#attrSelect').find('option:selected').text();
            // 非空时有效
            if (attr !== "") {
                var ele = '<div class="layui-inline">\n' +
                    '<label class="layui-form-label">'+ attr +'</label>\n' +
                    '<input class="layui-input" type="hidden" value='+ attrId +'#'+ noneImageNum +'>'+
                    '<div class="layui-input-inline"><input type="text" name='+ attrId +'#'+ noneImageNum +' autocomplete="off" placeholder="请输入属性值" class="layui-input"></div>\n' +
                    '<button type="button" class="layui-btn layui-btn-danger layui-btn-small" style="margin-top: 5px"><i class="layui-icon">&#x1006;</i></button>\n'+
                    '</div>';
                $("#noneImageItem").append(ele);
                noneImageNum = noneImageNum + 1;
            }
        });

        $('#noneImageItem').on('click', '.layui-btn.layui-btn-danger.layui-btn-small', function(){
            $(this).parent().remove();
        });

        $('#release').on('click', function () {

            var check = $('#commodityID').val();

            if (check === "" || check == null) {
                $(":text").val("");
                layer.msg('请先分配ID');
                return false;
            } else {
                $('#imageItem').children(".layui-inline").children(".layui-input").each(function () {
                    var item = $(this).val() + ';';
                    var str = $('#imageParam').val();
                    str += item;
                    $('#imageParam').val(str);
                });

                // 无图属性提交名称域
                $('#noneImageItem').children(".layui-inline").children(".layui-input").each(function () {
                    var item = $(this).val() + ';';
                    var str = $('#noneImageParam').val();
                    str += item;
                    $('#noneImageParam').val(str);
                });

                $("#dataForm").submit();
            }
        });

    });
</script>
</body>
</html>
