package model;


import interfaces.IDiscoveryServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.DBDAO;

import java.sql.*;


public class DiscoveryModel {
    private IDiscoveryServlet iDiscoveryServlet;

    public DiscoveryModel(IDiscoveryServlet iDiscoveryServlet) {
        this.iDiscoveryServlet = iDiscoveryServlet;
    }



    /**
     * @param start 起始位置
     * @param n 从起始位置开始，需要的数量
     */
    public void getArticleCard(int start, int n) {
        try {
            Connection con = DBDAO.getConnection();
            Statement statement = con.createStatement();
            // 查询最新 5 条 article
            String sql = String.format("SELECT * FROM discovery_card limit %d,%d;", start, start + n);
            ResultSet resultSet = statement.executeQuery(sql);  // 查询数据库

            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();  // 获取元信息
                int len = resultSetMetaData.getColumnCount();                   // 获取列数
                JSONObject jsonObject = new JSONObject();                       // 放入 JSONObject 中
                // i 从 1 开始
                for (int i = 1; i <= len; i++) {
                    jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
                jsonArray.put(jsonObject);// 放入 JSONArray 中
            }
            statement.close();
            con.close();
            String r = jsonArray.toString();
            // 打印在控制台
            System.out.println(r);
            // 回调接口
            iDiscoveryServlet.response(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
