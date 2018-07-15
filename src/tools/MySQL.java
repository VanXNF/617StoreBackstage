package tools;

public class MySQL {

    public static String account = "root";

//    public static String password = "abcphotovalley";
    public static String password = "123456";

//    private static String url = "jdbc:mysql://120.79.162.134:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8";
    public static String url = "jdbc:mysql://192.168.4.213:3306/617Store?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";

    public static String getAccount() {
        return account;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }
}
