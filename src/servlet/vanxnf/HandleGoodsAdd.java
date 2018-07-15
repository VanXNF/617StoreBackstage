package servlet.vanxnf;

import tools.MySQL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet(name = "GoodsAddServlet", urlPatterns = "/api/goodsAdd")
public class HandleGoodsAdd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(MySQL.getUrl(),MySQL.getAccount(),MySQL.getPassword());
            ps = con.prepareStatement("INSERT INTO commodity(title,original_price,discount_price,quick_review,overview,sale_volume) VALUES ('正在编辑',0,0,'正在编辑','正在编辑',0);");
            int m = ps.executeUpdate();
            if (m != 0) {
                ps = con.prepareStatement("SELECT id FROM commodity WHERE title = '正在编辑'");
                rs = ps.executeQuery();
                while (rs.next()) {
                    PrintWriter writer = resp.getWriter();
                    writer.write(rs.getString("id"));
                    writer.close();
                    break;
                }
            } else {
                ps = con.prepareStatement("SELECT id FROM commodity WHERE title = '正在编辑'");
                rs = ps.executeQuery();
                while (rs.next()) {
                    PrintWriter writer = resp.getWriter();
                    writer.write(rs.getString("id"));
                    writer.close();
                    break;
                }
            }
        } catch (Exception e) {
            try {
                ps = con.prepareStatement("SELECT id FROM commodity WHERE title = '正在编辑'");
                rs = ps.executeQuery();
                while (rs.next()) {
                    PrintWriter writer = resp.getWriter();
                    writer.write(rs.getString("id"));
                    writer.close();
                    break;
                }
            } catch (Exception e1) {
                PrintWriter writer = resp.getWriter();
                writer.write("获取ID失败");
                writer.close();
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        String commodityID = req.getParameter("commodityID");
        if (commodityID == null) {
            resp.sendRedirect("../pages/AddGoods.jsp?status=ERROR");
        }
        String commodityTitle = req.getParameter("commodityTitle");
        String oPrice = req.getParameter("OPrice");
        String dPrice = req.getParameter("DPrice");
        String quickView = req.getParameter("quickView");
        String overView = req.getParameter("overview");
        String imageParam = req.getParameter("imageParam");
        String noneImageParam = req.getParameter("noneImageParam");

        ArrayList<String> mainPic = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mainPic.add(req.getParameter("mainPic" + i));
        }
        Connection con;
        PreparedStatement ps;
        try {
            //            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(MySQL.getUrl(),MySQL.getAccount(),MySQL.getPassword());
            ps = con.prepareStatement("UPDATE commodity SET title=?,original_price=?,discount_price=?,quick_review=?,overview=? WHERE id=?;");
            ps.setString(1, commodityTitle);
            ps.setString(2, oPrice);
            ps.setString(3, dPrice);
            ps.setString(4, quickView);
            ps.setString(5, overView);
            ps.setString(6, commodityID);

            int m = ps.executeUpdate();
            if (m != 0) {
                for (int i = 0; i < mainPic.size(); i++) {
                    ps = con.prepareStatement("INSERT INTO image(image,commodity_id) VALUES (?,?);");
                    ps.setString(1, mainPic.get(i));
                    ps.setString(2, commodityID);
                    int n = ps.executeUpdate();
                    if (n == 0) {
                        resp.sendRedirect("../pages/AddGoods.jsp?status=ERROR");
                    }
                }
                if (!handleImageParam(imageParam, commodityID, req, con) || !handleNoneImageParam(noneImageParam, commodityID, req, con)) {
                    resp.sendRedirect("../pages/AddGoods.jsp?status=ERROR");
                }
                resp.sendRedirect("../pages/AddGoods.jsp?status=OK");
            } else {
                resp.sendRedirect("../pages/AddGoods.jsp?status=ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean handleImageParam(String imageParam, String commodityID, HttpServletRequest req, Connection con) {
        String[] imageParams;
        PreparedStatement ps;
        if (imageParam != null) {
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
                            ps.setString(1, link);
                            ps.setString(2, commodityID);
                            ps.setString(3, parameterId);
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
        if (noneImageParam != null) {
            noneImageParams = noneImageParam.split(";");
            for (int i = 0; i < noneImageParams.length; i++) {
                String[] action = noneImageParams[i].split("#");
                String value = req.getParameter(noneImageParams[i]);
                try {
                    ps = con.prepareStatement("INSERT INTO parameter(commodity_id,attribute_id,`value`) VALUES(?,?,?);");
                    ps.setString(1, commodityID);
                    ps.setString(2, action[0]);
                    ps.setString(3, value);
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
