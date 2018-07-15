package servlet.vanxnf;

import bean.vanxnf.Payment;
import org.json.JSONArray;
import org.json.JSONObject;
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

@WebServlet(name = "PaymentListServlet", urlPatterns = "/api/paymentList")
public class HandlePaymentList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        Connection con;
        PreparedStatement ps;
        ResultSet rs;

        try{
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(MySQL.getUrl(),MySQL.getAccount(),MySQL.getPassword());
            ps = con.prepareStatement("SELECT * FROM paymentList;");
            rs = ps.executeQuery();
            ArrayList<Payment> payments = new ArrayList<>();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setPayDate(rs.getString("pay_date"));
                payment.setPrice(rs.getDouble("price"));
                payment.setUserId(rs.getInt("user_id"));
                payment.setUserName(rs.getString("username"));
                payment.setEmail(rs.getString("email"));
                payments.add(payment);
            }
            JSONArray array = new JSONArray(payments.subList(limit * (page-1),
                    limit * page >= payments.size() ? payments.size() : limit * page));
            JSONObject json = new JSONObject();
            json.put("code", 0);
            json.put("msg", "");
            json.put("count", payments.size());
            json.put("data", array);
            PrintWriter out = resp.getWriter();
            out.append(json.toString());
            con.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
