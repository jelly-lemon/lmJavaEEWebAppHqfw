package servlet;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDao {

    public String login(String phone, String password) {

        try {
            // 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 建立连接
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql_service:3306/company?useSSL=false","root","mysql");
            // 创建状态
            Statement state = con.createStatement();
            // 查询
            String sql = String.format("select * from user where phone='%s';", phone);
            ResultSet rs = state.executeQuery(sql);

            JSONObject jsonObject = new JSONObject();
            String code;
            String msg;

            // 账号正确
            if (rs.next()) {

                //String phone = rs.getString("phone");
                String passwordCorrect = rs.getString("password");

                if ( passwordCorrect.equals(password) ) {
                    // 密码正确
                    code = "1";
                    msg = "登录成功";

                } else {
                    // 密码错误
                    code = "2";
                    msg = "密码错误";
                }
            } else {
                // 账号错误
                code = "3";
                msg = "账号不存在";
            }

            jsonObject.put("code", code);
            jsonObject.put("msg", msg);

            state.close();
            con.close();

            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
