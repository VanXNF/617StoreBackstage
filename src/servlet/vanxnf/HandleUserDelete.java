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

@WebServlet(name = "UserDeleteServlet", urlPatterns = "/api/userDelete")
public class HandleUserDelete extends HttpServlet {

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
            ps = con.prepareStatement("DELETE FROM user WHERE id = ?;");
            ps.setString(1, id);
            int i = ps.executeUpdate();
            if (i != 0) {
                session.removeAttribute("user"+id);
                resp.setHeader("refresh", "3;url='../pages/information.html'");
            } else {
                resp.sendRedirect("../pages/UserEdit.jsp?id="+id+"&status=ERROR");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
