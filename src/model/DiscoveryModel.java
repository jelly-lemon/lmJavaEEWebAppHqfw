package model;


import interfaces.IDiscoveryServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.DBDAO;

import java.sql.*;


public class DiscoveryModel {
    IDiscoveryServlet iDiscoveryServlet;

    public DiscoveryModel(IDiscoveryServlet iDiscoveryServlet) {
        this.iDiscoveryServlet = iDiscoveryServlet;
    }

    public void getArticleCard(int n) {
        try {
            Statement statement = DBDAO.getStatement();
            // 查询最新 5 条 article
            String sql = String.format("SELECT * FROM article_card limit 0,%d;", n);
            ResultSet resultSet = statement.executeQuery(sql);

            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int len = resultSetMetaData.getColumnCount();
                JSONObject jsonObject = new JSONObject();
                // i 从 1 开始
                for (int i = 1; i <= len; i++) {
                    jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
                jsonArray.put(jsonObject);
            }
            statement.close();
            String r = jsonArray.toString();

            // 回调接口
            iDiscoveryServlet.response(r);
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }
}
