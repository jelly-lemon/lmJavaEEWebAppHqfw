package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import utils.DBDAO;

import java.sql.*;

/**
 * 登录模型
 */
public class LoginModel {

    /**
     * 检查账号是否正确
     * @param phone 电话号码
     * @param password  密码
     * @return  检查结果，正确则返回该用户所有信息
     */
    public String checkAccount(String phone, String password) {
        String result = null;
        try {
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();


            // 查询
            String sql = String.format("select * from Users where phone='%s';", phone);
            ResultSet rs = statement.executeQuery(sql);

            //JSONObject jsonObject = new JSONObject();
            JsonObject jsonObject = new JsonObject();
            String code;
            String msg;

            // 账号正确
            if (rs.next()) {
                String passwordCorrect = rs.getString("password");
                if ( passwordCorrect.equals(password) ) {
                    // 密码正确
                    code = "1";
                    msg = "登录成功";

                    Gson gson = new Gson();
                    String userJSON = gson.toJson(getUser(phone));
                    jsonObject.addProperty("user", userJSON);
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

            jsonObject.addProperty("code", code);
            jsonObject.addProperty("msg", msg);
            result = jsonObject.toString();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据号码获取用户所有信息
     * @param phone 号码
     * @return User 对象
     */
    private User getUser(String phone) {
        User user = new User();
        try {
            Connection con = DBDAO.getConnection();
            Statement statement = con.createStatement();

            String sql = String.format("SELECT * FROM Users WHERE phone='%s';", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setStudentID(resultSet.getString("studentID"));
                user.setHeadURL(resultSet.getString("headURL"));
                user.setName(resultSet.getString("name"));
                user.setGender(resultSet.getString("gender"));
                user.setBuilding(resultSet.getString("building"));
                user.setRoomNumber(resultSet.getString("roomNumber"));
            }

            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


}
