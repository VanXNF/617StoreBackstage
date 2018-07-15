package servlet.vanxnf;

import bean.vanxnf.Admin;
import tools.Hasher;
import tools.MySQL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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

@WebServlet(name = "HandleLogin", urlPatterns = "/api/login")
public class HandleLogin extends HttpServlet {

    private Admin admin = new Admin();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/text; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String account = request.getParameter("account").trim();
        String password = request.getParameter("password").trim();
        HttpSession session = request.getSession();
        if (check(account, password)) {
            try {
                admin.setAccount(account);
                session.setAttribute("admin", admin);
                response.sendRedirect("/Main.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                /* 通过参数status显示失败提示框 */
                response.sendRedirect("/Login.jsp?status=Error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean check(String account, String password) {
        Connection con;
        PreparedStatement sql;
        ResultSet rs;

        String queryPassword = null;
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(MySQL.getUrl(), MySQL.getAccount(), MySQL.getPassword());
            sql = con.prepareStatement("SELECT * FROM admin WHERE account = ?;");
            sql.setString(1, account);
            rs = sql.executeQuery();
            while (rs.next()) {
                admin.setId(rs.getInt("id"));
                queryPassword = rs.getString("password");
                break;
            }
            con.close();
            /* 判断加密后的密码与数据库中存储的密码是否相同 */
            if(Hasher.checkPassword(password, queryPassword)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
