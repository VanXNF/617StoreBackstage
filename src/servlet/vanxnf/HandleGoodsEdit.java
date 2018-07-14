package servlet.vanxnf;

import bean.vanxnf.*;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "GoodsEditServlet", urlPatterns = "/api/goodsEdit")
public class HandleGoodsEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);
        String commodityId = req.getParameter("id");
        Connection con;
        PreparedStatement ps;
        ResultSet rsAttr;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("SELECT * FROM attribute");
            rsAttr = ps.executeQuery();
            ArrayList<Attribute> attributes = new ArrayList<>();
            while (rsAttr.next()) {
                Attribute attribute = new Attribute();
                attribute.setAttribute(rsAttr.getString("attribute"));
                attribute.setId(rsAttr.getInt("id"));
                attribute.setImageFlag(rsAttr.getInt("image_flag"));
                attributes.add(attribute);
            }
            if (attributes.size() > 0) {
                session.setAttribute("Attribute", attributes);
            }
            getCommodity(con, commodityId, req);
            resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityId);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityId);
        }
    }

    private boolean getCommodity(Connection con, String commodityId, HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        PreparedStatement ps;
        ResultSet rs;
        ResultSet rsMainImage;
        ResultSet rsImageParam;
        ResultSet rsParam;
        try {
            ps = con.prepareStatement("SELECT * FROM commodity WHERE id = ?;");
            ps.setString(1, commodityId);
            rs = ps.executeQuery();
            Commodity commodity = new Commodity();
            Parameter parameter = new Parameter();
            while (rs.next()) {
                commodity.setId(rs.getInt("id"));
                commodity.setTitle(rs.getString("title"));
                commodity.setOriginalPrice(rs.getDouble("original_price"));
                commodity.setDiscountPrice(rs.getDouble("discount_price"));
                commodity.setSaleVolume(rs.getInt("sale_volume"));
                commodity.setQuickReview(rs.getString("quick_review"));
                commodity.setOverview(rs.getString("overview"));
                commodity.setDate(rs.getDate("date"));
//                获取商品主图
                ps = con.prepareStatement("SELECT image_id,image FROM mainPicture WHERE commodity_id = ?;");
                ps.setString(1, commodityId);
                rsMainImage = ps.executeQuery();
                ArrayList<Image> mainImages = new ArrayList<>();
                while (rsMainImage.next()) {
                    Image image = new Image();
                    image.setId(rsMainImage.getInt("image_id"));
                    image.setUrl(rsMainImage.getString("image"));
                    mainImages.add(image);
                }
                commodity.setMainImage(mainImages);

//                首先查询带图属性
                ps = con.prepareStatement("SELECT * FROM paramWithImage WHERE commodity_id = ?");
                ps.setString(1, commodityId);
                rsImageParam = ps.executeQuery();
//                存放属性
                ArrayList<Attribute> attributeArrayList = new ArrayList<>();
//                存放商品属性id
                ArrayList<Integer> ids = new ArrayList<>();
//                存放商品属性名
                ArrayList<String> attrs = new ArrayList<>();
//                存放带图属性的图片链接与对应值
                ParamWithImage imageParam = new ParamWithImage();
                ArrayList<Param> value = new ArrayList<>();
                ArrayList<Image> images = new ArrayList<>();

                while (rsImageParam.next()) {
                    Attribute attr = new Attribute();
                    int id = rsImageParam.getInt("attribute_id");
                    if (!ids.contains(id)) {
                        ids.add(id);
                        attr.setId(id);
                        attr.setImageFlag(1);
                    }
                    String attribute = rsImageParam.getString("attribute");
                    if (!attrs.contains(attribute)) {
                        attrs.add(attribute);
                        attr.setAttribute(attribute);
                        attributeArrayList.add(attr);
                    }
                    Param param = new Param();
                    param.setId(rsImageParam.getInt("parameter_id"));
                    param.setContent(rsImageParam.getString("value"));
                    value.add(param);
                    Image image = new Image();
                    image.setId(rsImageParam.getInt("image_id"));
                    image.setUrl(rsImageParam.getString("image"));
                    images.add(image);
                }
//                ImageFlag的值为带图属性个数
                if (value.size() != 0 && images.size() != 0 && ids.size() != 0 && attrs.size() != 0) {
                    parameter.setImageFlag(ids.size());
                    imageParam.setImage(images);
                    imageParam.setValue(value);
                } else {
                    ids.clear();
                    attrs.clear();
                    attributeArrayList.clear();
                    parameter.setImageFlag(0);
                }
//                查询不带图属性
                ps = con.prepareStatement("SELECT * FROM paramWithoutImage WHERE commodity_id = ?");
                ps.setString(1, commodityId);
                rsParam = ps.executeQuery();

//                用于存放各不带图属性名及对应属性值
                ArrayList<ParamWithoutImage> params = new ArrayList<>();
                while (rsParam.next()) {
                    Attribute attr = new Attribute();
                    int id = rsParam.getInt("attribute_id");
                    if (!ids.contains(id)) {
                        ids.add(id);
                        attr.setId(id);
                        attr.setImageFlag(0);
                    }
                    String attribute = rsParam.getString("attribute");
                    if (!attrs.contains(attribute)) {
                        attrs.add(attribute);
                        attr.setAttribute(attribute);
                        attributeArrayList.add(attr);
                        ParamWithoutImage param = new ParamWithoutImage();
                        ArrayList<String> values = new ArrayList<>();
                        ArrayList<Integer> Ids = new ArrayList<>();
                        values.add(rsParam.getString("value"));
                        Ids.add(rsParam.getInt("parameter_id"));
                        param.setKey(attribute);
                        param.setIds(Ids);
                        param.setValue(values);
                        params.add(param);
                    } else {
                        for (int i = 0; i < params.size(); i++ ) {
                            if (params.get(i).getKey().equals(attribute)) {
                                params.get(i).getIds().add(rsParam.getInt("parameter_id"));
                                params.get(i).getValue().add(rsParam.getString("value"));
                                break;
                            }
                        }
                    }
                }
                parameter.setImageParams(imageParam);
                parameter.setParams(params);
                parameter.setAttrs(attributeArrayList);
                break;
            }

            session.setAttribute("Commodity" + commodityId, commodity);
            session.setAttribute("Parameter" + commodityId, parameter);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);

        String commodityID = req.getParameter("commodityID");
        String commodityTitle = req.getParameter("commodityTitle");
        String oPrice = req.getParameter("OPrice");
        String dPrice = req.getParameter("DPrice");
        String quickView = req.getParameter("quickView");
        String overView = req.getParameter("overview");
        String imageParam = req.getParameter("imageParam");
        String noneImageParam = req.getParameter("noneImageParam");

        Commodity commodity = (Commodity) session.getAttribute("Commodity" + commodityID);
        Parameter parameter = (Parameter) session.getAttribute("Parameter" + commodityID);
        if (commodity != null && parameter != null) {
            ArrayList<Image> mainImage = new ArrayList<>();
            for (int i = 0; i < commodity.getMainImage().size(); i++) {
                Image image = new Image();
                image.setId(commodity.getMainImage().get(i).getId());
                image.setUrl(req.getParameter("mainPic" + i));
                mainImage.add(image);
            }

            Connection con;
            PreparedStatement ps;
            String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
            try {
                con = DriverManager.getConnection(url,"root","abcphotovalley");
                ps = con.prepareStatement("UPDATE commodity SET title = ?, original_price = ?, discount_price = ?, quick_review = ?, overview = ? WHERE id = ?;");
                ps.setString(1, commodityTitle);
                ps.setString(2, oPrice);
                ps.setString(3, dPrice);
                ps.setString(4, quickView);
                ps.setString(5, overView);
                ps.setString(6, commodityID);
                int i = ps.executeUpdate();
                if (i != 0) {
                    commodity.setTitle(commodityTitle);
                    commodity.setOriginalPrice(Double.parseDouble(oPrice));
                    commodity.setDiscountPrice(Double.parseDouble(dPrice));
                    commodity.setQuickReview(quickView);
                    commodity.setOverview(overView);
                } else {
                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=1");
                }
                for (int j = 0; j < mainImage.size(); j++) {
                    ps = con.prepareStatement("UPDATE image SET image = ? WHERE id = ?");
                    ps.setString(1, mainImage.get(j).getUrl());
                    ps.setString(2, String.valueOf(mainImage.get(j).getId()));
                    int m = ps.executeUpdate();
                    if (m == 0) {
                        resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=2");
                    }
                }
//                修改带图属性
                if (parameter.getImageFlag() != 0) {
                    for (int m = 0; m < parameter.getImageFlag(); m++) {
                        for (int n = 0; n < parameter.getImageParams().getImage().size(); n++) {
                            String value = req.getParameter(m+"imageValue"+n);
                            String link = req.getParameter(m+"imageParam"+n);
                            int id = parameter.getImageParams().getValue().get(n).getId();
                            if (value == null || link == null) {
                                ps = con.prepareStatement("DELETE FROM parameter WHERE id = ?;");
                                ps.setInt(1, id);
                                int x = ps.executeUpdate();
                                if (x == 0) {
                                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=3");
                                }
                            } else {
                                ps = con.prepareStatement("UPDATE parameter SET `value` = ? WHERE id = ?;");
                                ps.setInt(2, id);
                                ps.setString(1, value);
                                int x = ps.executeUpdate();
                                if (x != 0) {
                                    ps = con.prepareStatement("UPDATE image SET image = ? WHERE parameter_id = ?;");
                                    ps.setString(1, link);
                                    ps.setInt(2, id);
                                    int y = ps.executeUpdate();
                                    if (y == 0) {
                                        resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=3");
                                    }
                                } else {
                                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=3");
                                }
                            }
                        }
                    }
                }

//                修改无图属性
                if (parameter.getAttrs().size() - parameter.getImageFlag() != 0) {
                    for (int m = 0; m < parameter.getAttrs().size() - parameter.getImageFlag(); m++) {
                        for (int n = 0; n < parameter.getParams().get(m).getValue().size(); n++) {
                            String paramValue = req.getParameter(m + "paramValue" + n);
                            int id = parameter.getParams().get(m).getIds().get(n);
                            if (paramValue == null) {
                                ps = con.prepareStatement("DELETE FROM parameter WHERE id = ?;");
                                ps.setInt(1, id);
                                int x = ps.executeUpdate();
                                if (x == 0) {
                                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=4");
                                }
                            } else {
                                ps = con.prepareStatement("UPDATE parameter SET `value` = ? WHERE id = ?;");
                                ps.setString(1, paramValue);
                                ps.setInt(2, id);
                                int x = ps.executeUpdate();
                                if (x == 0) {
                                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=4");
                                }
                            }
                        }
                    }
                }

                if (!handleImageParam(imageParam, commodityID, req, con)) {
                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=3");
                }
                if (!handleNoneImageParam(noneImageParam, commodityID, req, con)) {
                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=4");
                }

                if (getCommodity(con, commodityID, req)) {
                    resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityID+"&status=OK");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean handleImageParam(String imageParam, String commodityID, HttpServletRequest req, Connection con) {
        String[] imageParams;
        PreparedStatement ps;
        if (imageParam != null && !imageParam.equals("")) {
            imageParams = imageParam.split(";");
            for (int i = 0; i < imageParams.length; i++) {
                String[] action = imageParams[i].split("#");
                String value = req.getParameter(action[0]+"value"+action[1]);
                String link = req.getParameter(action[0]+"image"+action[1]);
                try {
                    ps = con.prepareStatement("INSERT INTO parameter(commodity_id,attribute_id,`value`) VALUES(?,?,?);");
                    ps.setString(1, commodityID);
                    ps.setString(2, action[0]);
                    ps.setString(3, value);
                    int m = ps.executeUpdate();
                    if (m == 0) {
                        return false;
                    } else {
                        ps = con.prepareStatement("SELECT id FROM parameter WHERE commodity_id=? AND attribute_id=? AND `value`=?;");
                        ps.setString(1, commodityID);
                        ps.setString(2, action[0]);
                        ps.setString(3, value);
                        ResultSet rs = ps.executeQuery();
                        String parameterId = null;
                        while (rs.next()) {
                            parameterId = rs.getString("id");
                        }
                        if (parameterId != null) {
                            ps = con.prepareStatement("INSERT INTO image(image,commodity_id,parameter_id) VALUES(?,?,?);");
                            ps.setString(3, parameterId);
                            ps.setString(1, link);
                            ps.setString(2, commodityID);
                            int n = ps.executeUpdate();
                            if (n == 0) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            }
            return true;
        }
        return true;
    }

    private boolean handleNoneImageParam(String noneImageParam, String commodityID, HttpServletRequest req, Connection con) {
        PreparedStatement ps;
        String[] noneImageParams;
        if (noneImageParam != null && !noneImageParam.equals("")) {
            noneImageParams = noneImageParam.split(";");
            for (int i = 0; i < noneImageParams.length; i++) {
                String[] action = noneImageParams[i].split("#");
                String value = req.getParameter(noneImageParams[i]);
                try {
                    ps = con.prepareStatement("INSERT INTO parameter(commodity_id,attribute_id,`value`) VALUES(?,?,?);");
                    ps.setString(3, value);
                    ps.setString(1, commodityID);
                    ps.setString(2, action[0]);
                    int m = ps.executeUpdate();
                    if (m == 0) {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}
