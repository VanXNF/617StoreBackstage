package servlet.vanxnf;

import tools.Hasher;
import tools.MySQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "AddUser", urlPatterns = "/api/addUser")
public class HandleUserAdd extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("html/text; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String userName = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String email = request.getParameter("email").trim();
        String avatar = request.getParameter("avatar").trim();


        Connection con;
        PreparedStatement ps;

        boolean boo = userName.length() > 0 && password.length() > 0 && email.length() > 0;
        Hasher hasher = new Hasher();
        password = hasher.encode(password);
        try {
            con = DriverManager.getConnection(MySQL.getUrl(),MySQL.getAccount(),MySQL.getPassword());
            ps = con.prepareStatement("INSERT INTO user(username,password,email,avatar) VALUES (?,?,?,?);");
            if (boo) {
                ps.setString(1, userName);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, avatar);
                int m = ps.executeUpdate();
                if (m != 0) {
                    con.close();
                    response.sendRedirect("../pages/AddUser.jsp?status=OK");
                }
            } else {
                response.sendRedirect("../pages/AddUser.jsp?status=ERROR");
            }
            con.close();
        } catch(SQLException exp) {
            exp.printStackTrace();
            response.sendRedirect("../pages/AddUser.jsp?status=ERROR");
        }
    }
}
