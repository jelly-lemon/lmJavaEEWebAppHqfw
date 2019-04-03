package model;

import utils.DBDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PublishDiscoveryModel {
    /**
     * 插入部分内容，获取 article_id
     * @param phone 电话
     * @param content   内容
     * @return  article_id
     */
    public String insertAndGetArticleId(String phone, String content, String tag) {

        try {
            // 获取连接
            Connection connection = DBDAO.getConnection();
            // 获取 statement
            Statement statement = connection.createStatement();
            // 插入语句
            String sql = String.format("INSERT INTO article(phone, content, tag, time) VALUES('%s', '%s', '%s', NOW());",
                    phone, content, tag);
            // 执行插入
            statement.executeUpdate(sql);
            // 提交更新
            //connection.commit();

            // 查询语句
            sql = String.format("SELECT MAX(article_id) FROM article WHERE phone='%s';", phone);
            // 执行查询
            ResultSet resultSet = statement.executeQuery(sql);
            // 获取结果
            String article_id = null;
            if (resultSet.next()) {
                article_id = resultSet.getString("MAX(article_id)");
            }
            statement.close();
            connection.close();
            return article_id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入图片的 url 到数据库中
     * @param article_id 主键
     * @param img_url_json  json 格式的 url
     */
    public void insertImgUrl(String article_id, String img_url_json) {
        try {
            // 获取连接
            Connection connection = DBDAO.getConnection();
            // 获取 statement
            Statement statement = connection.createStatement();
            // 插入语句
            String sql = String.format("UPDATE article SET img_url_json = '%s' WHERE article_id = %s;",
                    img_url_json, article_id);
            // 执行插入
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
