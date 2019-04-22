package utils;



import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作
 */
public class DBDAO {
    // 驱动类名
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // 数据库名
    static String dataBase = "hqfw";
    // 账户
    static String user = "root";
    // 密码
    static String password = "mysql";


    /**
     * 获取一个数据库连接对象
     * @return  Connection 连接对象
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            String url = String.format("jdbc:mysql://mysql_service:3306/%s?useSSL=false", dataBase);
            // 加载驱动
            Class.forName(JDBC_DRIVER);
            // 建立连接
            con = DriverManager.getConnection(url, user, password); // 创建连接对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }


    /**
     * 插入
     * @param sql 插入语句
     */
    public static int insert(String sql) {
        int n = 0;
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            n = statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public static int update(String sql) {
        return insert(sql);
    }


    /**
     * 查询
     * @param sql 查询语句
     * @param response 回复对象
     */
    public static void query(String sql, HttpServletResponse response) {
        //JsonArray jsonArray = new JsonArray();
        List<JsonObject> jsonObjectList = new ArrayList<>();
        try {
            // 查询
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // 提取
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                JsonObject jsonObject = new JsonObject();

                // 一条数据
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    /*System.out.println(resultSetMetaData.getColumnTypeName(i));
                    switch (resultSetMetaData.getColumnTypeName(i)) {
                        case "DATETIME": {
                            jsonObject.addProperty(resultSetMetaData.getColumnLabel(i), resultSet.getTimestamp(i));
                            break;
                        }

                        default:{
                            jsonObject.addProperty(resultSetMetaData.getColumnLabel(i), resultSet.getString(i));
                            break;
                        }
                    }*/

                    jsonObject.addProperty(resultSetMetaData.getColumnLabel(i), resultSet.getString(i));

                }

                jsonObjectList.add(jsonObject);
            }

            // 回复
            Writer writer = response.getWriter();
            writer.write(jsonObjectList.toString());
            writer.flush();

            System.out.println(jsonObjectList.toString());

            // 关闭
            resultSet.close();
            statement.close();
            connection.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String insertAndGeID(String sql) {
        String ID = null;
        try {
            // 首先插入
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            // 再获取 ID
            sql = "SELECT LAST_INSERT_ID()";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                ID = resultSet.getString(1);
            }

            // 关闭连接
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID;
    }


}
