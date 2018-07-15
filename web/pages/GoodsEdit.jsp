<%@ page import="bean.vanxnf.Commodity" %>
<%@ page import="bean.vanxnf.Parameter" %>
<%@ page import="bean.vanxnf.Attribute" %>
<%@ page import="java.util.ArrayList" %>
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
        else if (status.equals("1")) status = "基本属性更改失败";
        else if (status.equals("2")) status = "主图更改失败";
        else if (status.equals("3")) status = "带图商品属性更改失败";
        else if (status.equals("4")) status = "无图商品属性更改失败";
        else if (status.equals("5")) status = "删除商品失败";
    }
    ArrayList<Attribute> attributes = null;
    Object object = session.getAttribute("Attribute");
    if (object instanceof ArrayList) {
        attributes = (ArrayList<Attribute>) object;
    }
%>
<body class="body">

<% if (commodity != null && parameter != null) {%>
<form id="dataForm" class="layui-form layui-form-pane" action="../api/goodsEdit" method="post">
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
            <input type="text" value="<%=commodity.getId()%>" autocomplete="off" class="layui-input layui-disabled" disabled>
            <input type="hidden" id="commodityID" name="commodityID" value="<%=commodity.getId()%>">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品标题</label>
        <div class="layui-input-block">
            <input type="text" name="commodityTitle" autocomplete="off" value="<%=commodity.getTitle()%>" class="layui-input">
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
            <textarea class="layui-textarea" name="quickView"><%=commodity.getQuickReview()%></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">详情图片</label>
            <div class="layui-input-inline">
                <input type="text" id="overview" name="overview" value="<%=commodity.getOverview()%>" lay-verify="required"  autocomplete="off" class="layui-input">
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
        <% for (int i = 0; i < commodity.getMainImage().size(); i++) {%>
        <div class="layui-inline">
            <label class="layui-form-label">主图 <%=i+1%></label>
            <div class="layui-input-inline">
                <input type="text" id="mainPic<%=i%>" name="mainPic<%=i%>" value="<%=commodity.getMainImage().get(i).getUrl()%>" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
            <button type="button" class="layui-btn" id="chooseMainPic<%=i%>">
                <i class="layui-icon">&#xe67c;</i>选择商品主图
            </button>
        </div>
        <% } %>
    </div>
    <% if (parameter.getImageFlag() != 0) {%>
        <% for (int i = 0; i < parameter.getImageParams().size(); i++) {%>
        <div class="layui-form-item">
            <% for (int j = 0; j < parameter.getImageParams().get(i).getImage().size(); j++) {%>
            <div class="layui-inline" id="<%=i%>imageLine<%=j%>">
                <label class="layui-form-label"><%=parameter.getAttrs().get(i).getAttribute()%></label>
                <div class="layui-input-inline">
                    <input type="text" id="<%=i%>imageValue<%=j%>" name="<%=i%>imageValue<%=j%>" value="<%=parameter.getImageParams().get(i).getValue().get(j).getContent()%>" lay-verify="required"  autocomplete="off" class="layui-input">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="<%=i%>imageParam<%=j%>" name="<%=i%>imageParam<%=j%>" value="<%=parameter.getImageParams().get(i).getImage().get(j).getUrl()%>" lay-verify="required"  autocomplete="off" class="layui-input">
                </div>
                <button type="button" class="layui-btn layui-btn-small" id="<%=i%>chooseImageParam<%=j%>" style="margin-top: 5px">
                    <i class="layui-icon">&#xe67c;</i>
                </button>
                <button type="button" class="layui-btn layui-btn-danger layui-btn-small" id="<%=i%>deleteImageParam<%=j%>" style="margin-top: 5px">
                    <i class="layui-icon">&#x1006;</i>
                </button>
            </div>
            <% } %>
        </div>
        <% } %>
    <% } %>
    <% if (parameter.getAttrs().size() - parameter.getImageFlag() != 0) {%>
        <% for (int i = 0; i < parameter.getAttrs().size() - parameter.getImageFlag(); i++) {%>
        <div class="layui-form-item">
            <% for (int j = 0; j < parameter.getParams().get(i).getValue().size(); j++) {%>
            <div class="layui-inline" id="<%=i%>paramLine<%=j%>">
                <label class="layui-form-label"><%=parameter.getParams().get(i).getKey()%></label>
                <div class="layui-input-inline">
                    <input type="text" name='<%=i%>paramValue<%=j%>' value="<%=parameter.getParams().get(i).getValue().get(j)%>" autocomplete="off" class="layui-input">
                </div>
                <button type="button" class="layui-btn layui-btn-danger layui-btn-small" id="<%=i%>deleteParam<%=j%>" style="margin-top: 5px">
                    <i class="layui-icon">&#x1006;</i>
                </button>
            </div>
            <% } %>
        </div>
        <% } %>
    <% } %>
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
    <div class="layui-form-item" style="margin-bottom: 5%">
        <div class="layui-inline">
            <button type="button" id="release" class="layui-btn layui-btn-normal">提交修改</button>
            <button type="button" id="deleteGoods" class="layui-btn layui-btn-danger">删除商品</button>
        </div>
    </div>
</form>
<form id="deleteForm" action="/api/goodsDelete" method="post">
    <input type="hidden" name="id" value="<%=commodity.getId()%>">
</form>


<script src="../frame/layui/layui.js" charset="utf-8"></script>
<script>

    layui.use(['upload', 'layer', 'form'], function() {
        var upload = layui.upload;
        var $ = layui.jquery;
        var form = layui.form;
        var layer = layui.layer;
        var imageNum = 0;
        var noneImageNum = 0;

        // 监听overview
        upload.render({
            elem: '#chooseOverview'
            ,auto: false //选择文件后不自动上传
            ,choose: function(obj){
                obj.preview(function(index, file, result) {
                    $("#overview").val('/images/commodity/'+'<%=commodity.getId()%>'+'/' + file.name);
                });
            }
        });
        // 监听主图
        <% for (int i = 0; i < commodity.getMainImage().size(); i++) {%>
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
        // 监听带图片属性
        <% if (parameter.getImageFlag() != 0) {%>
            <% for (int i = 0; i < parameter.getImageFlag(); i++) {%>
                <% for (int j = 0; j < parameter.getImageParams().get(i).getImage().size(); j++) {%>
                    upload.render({
                        elem: '#<%=i%>chooseImageParam<%=j%>'
                        ,auto: false //选择文件后不自动上传
                        ,choose: function(obj) {
                            obj.preview(function(index, file, result) {
                                $("#<%=i%>imageParam<%=j%>").val('/images/commodity/'+'<%=commodity.getId()%>'+'/' + file.name);
                            });
                        }
                    });
                    $("#<%=i%>deleteImageParam<%=j%>").on("click", function () {
                        layer.confirm('是否删除该属性数据？', function(index) {
                            $('#<%=i%>imageLine<%=j%>').remove();
                            layer.close(index);
                            return false;
                        });
                    });
                <% } %>
            <% } %>
        <% } %>
        // 监听无图属性
        <% for (int i = 0; i < parameter.getAttrs().size() - parameter.getImageFlag(); i++) {%>
            <% for (int j = 0; j < parameter.getParams().get(i).getValue().size(); j++) {%>
                $("#<%=i%>deleteParam<%=j%>").on("click", function () {
                    layer.confirm('是否删除该属性数据？', function(index) {
                        $('#<%=i%>paramLine<%=j%>').remove();
                        layer.close(index);
                        return false;
                    });
                });
            <% } %>
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
                    '<div class="layui-input-inline" ><input type="text" id="file'+ imageNum +'" name="'+ attrId +'image'+ imageNum + '" placeholder="请输入图片链接" lay-verify="required"  autocomplete="off" class="layui-input image"></div>\n' +
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
                    '<div class="layui-input-inline"><input type="text" name='+ attrId +'#'+ noneImageNum +' autocomplete="off" placeholder="请输入" class="layui-input"></div>\n' +
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
        });

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