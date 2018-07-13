package servlet.vanxnf;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "GoodsDeleteServlet", urlPatterns = "/api/goodsDelete")
public class HandleGoodsDelete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);
        String id = req.getParameter("id");
        Connection con;
        PreparedStatement ps;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
            //            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("DELETE FROM commodity WHERE id = ?;");
            ps.setString(1, id);
            int i = ps.executeUpdate();
            if (i != 0) {
                session.removeAttribute("Commodity" + id);
                session.removeAttribute("Parameter" + id);
                resp.setHeader("refresh", "1;url='../pages/information.html'");
            } else {
                resp.sendRedirect("../pages/GoodsEdit.jsp?id="+id+"&status=5");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("../pages/GoodsEdit.jsp?id="+id+"&status=5");
        }
    }
}
