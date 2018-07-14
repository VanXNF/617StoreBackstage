package servlet.vanxnf;

import bean.vanxnf.Attribute;

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

@WebServlet(name = "GoodsAttributeServlet", urlPatterns = "/api/goodsAttribute")
public class HandleGoodsAttribute extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);
        String page = req.getParameter("page");
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
            //            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("SELECT * FROM attribute");
            rs = ps.executeQuery();
            ArrayList<Attribute> attributes = new ArrayList<>();
            while (rs.next()) {
                Attribute attribute = new Attribute();
                attribute.setId(rs.getInt("id"));
                attribute.setAttribute(rs.getString("attribute"));
                attribute.setImageFlag(rs.getInt("image_flag"));
                attributes.add(attribute);
            }
            if (attributes.size() > 0) {
                session.setAttribute("Attribute", attributes);
            }
            if (page.equals("AddGoods")) {
                resp.sendRedirect("../pages/AddGoods.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
