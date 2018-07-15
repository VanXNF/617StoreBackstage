package servlet.vanxnf;

import bean.vanxnf.User;
import org.json.JSONArray;
import org.json.JSONObject;
import tools.MySQL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet(name = "UserListServlet", urlPatterns = "/api/userList")
public class HandleUserList extends HttpServlet {

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
            ps = con.prepareStatement("SELECT id,username,email,avatar,registerDate FROM user;");
            rs = ps.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("avatar"));
                user.setRegisterDate(rs.getString("registerDate"));
                users.add(user);
            }
            JSONArray array = new JSONArray(users.subList(limit * (page-1),
                    limit * page >= users.size() ? users.size() : limit * page));
            JSONObject json = new JSONObject();
            json.put("code", 0);
            json.put("msg", "");
            json.put("count", users.size());
            json.put("data", array);
//            saveDataToFile("UserList", json.toString());
            PrintWriter out = resp.getWriter();
            out.append(json.toString());
            con.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void saveDataToFile(String fileName, String data) {
        BufferedWriter writer = null;
        File file = new File("D:/IdeaWork/617StoreBackstage/web/json/"+ fileName + ".json");
        //如果文件不存在，则新建一个
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
