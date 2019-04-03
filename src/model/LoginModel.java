package model;

import org.json.JSONObject;
import utils.DBDAO;

import java.sql.*;

public class LoginModel {
    public String checkAccount(String phone, String password) {
        String result = null;
        try {
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();


            // 查询
            String sql = String.format("select * from user where phone='%s';", phone);
            ResultSet rs = statement.executeQuery(sql);

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
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();  // 获取元信息
                    int len = resultSetMetaData.getColumnCount();                   // 获取列数
                    // i 从 1 开始遍历
                    for (int i = 1; i <= len; i++) {
                        jsonObject.put(resultSetMetaData.getColumnName(i), rs.getString(i));
                    }

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
            result = jsonObject.toString();

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
