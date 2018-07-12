package servlet.vanxnf;

import bean.vanxnf.Commodity;
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

@WebServlet(name = "GoodsListServlet", urlPatterns = "/api/goodsList")
public class HandleGoodsList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","abcphotovalley");
            ps = con.prepareStatement("SELECT * FROM commodity;");
            rs = ps.executeQuery();
            ArrayList<Commodity> commodities = new ArrayList<>();
            while (rs.next()) {
                Commodity commodity = new Commodity();
                commodity.setId(rs.getInt("id"));
                commodity.setTitle(rs.getString("title"));
                commodity.setOriginalPrice(rs.getDouble("original_price"));
                commodity.setDiscountPrice(rs.getDouble("discount_price"));
                commodity.setSaleVolume(rs.getInt("sale_volume"));
                commodity.setQuickReview(rs.getString("quick_review"));
                commodity.setOverview(rs.getString("overview"));
                commodity.setDate(rs.getDate("date"));
                commodities.add(commodity);
            }
            JSONArray array = new JSONArray(commodities.subList(limit * (page-1),
                    limit * page >= commodities.size() ? commodities.size() : limit * page));
            JSONObject json = new JSONObject();
            json.put("code", 0);
            json.put("msg", "");
            json.put("count", commodities.size());
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
