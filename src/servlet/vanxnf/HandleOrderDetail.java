package servlet.vanxnf;

import bean.vanxnf.Order;
import org.json.JSONArray;
import org.json.JSONObject;

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

@WebServlet(name = "OrderDetailServlet", urlPatterns = "/api/orderDetail")
public class HandleOrderDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        String id = req.getParameter("id");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("SELECT * FROM historyOrder WHERE payment_id = ?;");
            ps.setString(1, id);
            rs = ps.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setCommodityId(rs.getInt("commodity_id"));
                order.setTitle(rs.getString("title"));
                order.setQuantity(rs.getInt("quantity"));
                order.setSumPrice(rs.getDouble("sumPrice"));
                order.setAttrWithImage(rs.getString("attr_with_image"));
                order.setAttrWithoutImage(rs.getString("attr_without_image"));
                order.setUserId(rs.getInt("user_id"));
                order.setPaymentId(rs.getInt("payment_id"));
                orders.add(order);
            }
            JSONArray array = new JSONArray(orders.subList(limit * (page-1),
                    limit * page >= orders.size() ? orders.size() : limit * page));
            JSONObject json = new JSONObject();
            json.put("code", 0);
            json.put("msg", "");
            json.put("count", orders.size());
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
