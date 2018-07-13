package servlet.vanxnf;

import bean.vanxnf.Commodity;
import bean.vanxnf.ParamWithImage;
import bean.vanxnf.ParamWithoutImage;
import bean.vanxnf.Parameter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet(name = "GoodsEditServlet", urlPatterns = "/api/goodsEdit")
public class HandleGoodsEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);
        String commodityId = req.getParameter("id");
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ResultSet rsMainImage;
        ResultSet rsImageParam;
        ResultSet rsParam;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
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
                ps = con.prepareStatement("SELECT image FROM mainPicture WHERE commodity_id = ?;");
                ps.setString(1, commodityId);
                rsMainImage = ps.executeQuery();
                ArrayList<String> mainImages = new ArrayList<>();
                while (rsMainImage.next()) {
                    mainImages.add(rsMainImage.getString("image"));
                }
                commodity.setMainImage(mainImages);

//                首先查询带图属性
                ps = con.prepareStatement("SELECT * FROM paramWithImage WHERE commodity_id = ?");
                ps.setString(1, commodityId);
                rsImageParam = ps.executeQuery();
//                存放商品属性id
                ArrayList<Integer> ids = new ArrayList<>();
//                存放商品属性名
                ArrayList<String> attrs = new ArrayList<>();
//                存放带图属性的图片链接与对应值
                ParamWithImage imageParam = new ParamWithImage();
                ArrayList<String> value = new ArrayList<>();
                ArrayList<String> image = new ArrayList<>();

                while (rsImageParam.next()) {
                    int id = rsImageParam.getInt("attribute_id");
                    if (!ids.contains(id)) {
                        ids.add(id);
                    }
                    String attribute = rsImageParam.getString("attribute");
                    if (!attrs.contains(attribute)) {
                        attrs.add(attribute);
                    }
                    value.add(rsImageParam.getString("value"));
                    image.add(rsImageParam.getString("image"));
                }
//                ImageFlag的值为带图属性个数
                if (value.size() != 0 && image.size() != 0 && ids.size() != 0 && attrs.size() != 0) {
                    parameter.setImageFlag(ids.size());
                    imageParam.setImage(image);
                    imageParam.setValue(value);
                } else {
                    ids.clear();
                    attrs.clear();
                    parameter.setImageFlag(0);
                }
//                查询不带图属性
                ps = con.prepareStatement("SELECT * FROM paramWithoutImage WHERE commodity_id = ?");
                ps.setString(1, commodityId);
                rsParam = ps.executeQuery();

//                用于存放各不带图属性名及对应属性值
                ArrayList<ParamWithoutImage> params = new ArrayList<>();
                while (rsParam.next()) {
                    int id = rsParam.getInt("attribute_id");
                    if (!ids.contains(id)) {
                        ids.add(id);
                    }
                    String attribute = rsParam.getString("attribute");
                    if (!attrs.contains(attribute)) {
                        attrs.add(attribute);
                        ParamWithoutImage param = new ParamWithoutImage();
                        ArrayList<String> values = new ArrayList<>();
                        values.add(rsParam.getString("value"));
                        param.setKey(attribute);
                        param.setValue(values);
                        params.add(param);
                    } else {
                        for (int i = 0; i < params.size(); i++ ) {
                            if (params.get(i).getKey().equals(attribute)) {
                                params.get(i).getValue().add(rsParam.getString("value"));
                                break;
                            }
                        }
                    }
                }
                parameter.setImageParams(imageParam);
                parameter.setParams(params);
                parameter.setId(ids);
                parameter.setAttrs(attrs);
                break;
            }

            session.setAttribute("Commodity" + commodityId, commodity);
            session.setAttribute("Parameter" + commodityId, parameter);
            resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityId);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("../pages/GoodsEdit.jsp?id="+ commodityId);
        }
    }
}
