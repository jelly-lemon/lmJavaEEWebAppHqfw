package model;


import com.google.gson.Gson;
import entity.Discovery;
import entity.DiscoveryCard;
import entity.User;
import interfaces.IDiscoveryServlet;
import utils.DBDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取发现页面模型
 */
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
            String sql = String.format("SELECT * FROM DiscoveryCard limit %d,%d;", start, start + n);
            ResultSet resultSet = statement.executeQuery(sql);  // 查询数据库

            //JSONArray jsonArray = new JSONArray();
            //JsonArray jsonArray = new JsonArray();
            List<DiscoveryCard> discoveryCardList = new ArrayList<>();
            while (resultSet.next()) {
                /*ResultSetMetaData resultSetMetaData = resultSet.getMetaData();  // 获取元信息
                int len = resultSetMetaData.getColumnCount();                   // 获取列数
                JSONObject jsonObject = new JSONObject();                       // 放入 JSONObject 中
                // i 从 1 开始
                for (int i = 1; i <= len; i++) {
                    jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
                jsonArray.put(jsonObject);// 放入 JSONArray 中*/
                Discovery discovery = new Discovery();
                discovery.setDiscoveryID(resultSet.getInt("discoveryID"));
                discovery.setPhone(resultSet.getString("phone"));
                discovery.setDateTime(resultSet.getTimestamp("dateTime"));
                discovery.setTag(resultSet.getString("tag"));
                discovery.setContent(resultSet.getString("content"));
                discovery.setImgURL(resultSet.getString("imgURL"));
                User user = new User();
                user.setHeadURL("headURL");
                user.setName("name");
                user.setGender("gender");
                DiscoveryCard discoveryCard = new DiscoveryCard();
                discoveryCard.setDiscovery(discovery);
                discoveryCard.setUser(user);
                discoveryCardList.add(discoveryCard);


                //Gson gson = new Gson();

                // 添加到数组中
                //jsonArray.add(gson.toJson(discoveryCard));
            }
            Gson gson = new Gson();
            String r = gson.toJson(discoveryCardList);

            //String r = jsonArray.toString();
            // 打印在控制台
            System.out.println("DiscoveryModel:" + r);
            // 回调接口
            iDiscoveryServlet.response(r);

            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
