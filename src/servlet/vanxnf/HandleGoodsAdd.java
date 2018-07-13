package servlet.vanxnf;

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
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
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

        ArrayList<String> mainPic = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mainPic.add(req.getParameter("mainPic" + i));
        }
        Connection con;
        PreparedStatement ps;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
            //            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
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
                resp.sendRedirect("../pages/AddGoods.jsp?status=OK");
            } else {
                resp.sendRedirect("../pages/AddGoods.jsp?status=ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
