package model;

import utils.DBDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PublishDiscoveryModel {
    /**
     * 插入部分内容，获取 discoveryID
     * @param phone 电话
     * @param content   内容
     * @return  article_id
     */
    public String insertAndGetDiscoveryID(String phone, String content, String tag) {

        String discoveryID = null;
        try {
            // 获取连接
            Connection connection = DBDAO.getConnection();
            // 获取 statement
            Statement statement = connection.createStatement();
            // 插入语句
            String sql = String.format("INSERT INTO Discovery(phone, content, tag, dateTime) VALUES('%s', '%s', '%s', NOW());",
                    phone, content, tag);
            // 执行插入
            statement.executeUpdate(sql);

            // 查询语句
            sql = String.format("SELECT MAX(discoveryID) FROM Discovery WHERE phone='%s';", phone);
            // 执行查询
            ResultSet resultSet = statement.executeQuery(sql);
            // 获取结果
            if (resultSet.next()) {
                discoveryID = resultSet.getString("MAX(discoveryID)");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discoveryID;
    }

    /**
     * 插入图片的 url 到数据库中
     * @param discoveryID 主键
     * @param imgURL  json 格式的 url
     */
    public void insertImgUrl(String discoveryID, String imgURL) {
        try {
            // 获取连接
            Connection connection = DBDAO.getConnection();
            // 获取 statement
            Statement statement = connection.createStatement();
            // 插入语句
            String sql = String.format("UPDATE Discovery SET imgURL = '%s' WHERE discoveryID = %s;",
                    imgURL, discoveryID);
            // 执行插入
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
