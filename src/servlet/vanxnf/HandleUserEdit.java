package servlet.vanxnf;

import bean.vanxnf.User;
import tools.Hasher;

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

@WebServlet(name = "UserEdit", urlPatterns = "/api/userEdit")
public class HandleUserEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);
        String id = req.getParameter("id");
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("SELECT * FROM user WHERE id = ?;");
            ps.setString(1, id);
            rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("avatar"));
                user.setRegisterdate(rs.getDate("registerDate"));
                break;
            }
            session.setAttribute("user"+id, user);
            resp.sendRedirect("../pages/UserEdit.jsp?id="+id);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(true);
        String userID = req.getParameter("userID");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String avatar = req.getParameter("avatar");
        if (password.length() <= 15 || !(password.substring(0,14).equals("pbkdf2_sha256$"))) {
            password = new Hasher().encode(password);
        }
        Connection con;
        PreparedStatement ps;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("UPDATE user SET username = ?, password = ?, email = ?, avatar = ? WHERE id = ?;");
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, avatar);
            ps.setString(5, userID);
            int i = ps.executeUpdate();
            if (i != 0) {
                User user = (User) session.getAttribute("user"+userID);
                user.setUsername(userName);
                user.setPassword(password);
                user.setEmail(email);
                user.setAvatar(avatar);
                session.setAttribute("user"+userID, user);
                resp.sendRedirect("../pages/UserEdit.jsp?id="+userID+"&status=OK");
            } else {
                resp.sendRedirect("../pages/UserEdit.jsp?id="+userID+"&status=ERROR");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
