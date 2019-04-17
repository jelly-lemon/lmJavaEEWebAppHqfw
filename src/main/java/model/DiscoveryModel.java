package model;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

            // 查询最新 n 条 article
            String sql = String.format("SELECT * FROM DiscoveryCard  ORDER BY dateTime DESC limit %d,%d;", start, start + n);
            ResultSet resultSet = statement.executeQuery(sql);  // 查询数据库

            List<DiscoveryCard> discoveryCardList = new ArrayList<>();
            while (resultSet.next()) {
                Discovery discovery = new Discovery();
                discovery.setDiscoveryID(resultSet.getInt("discoveryID"));
                discovery.setPhone(resultSet.getString("phone"));
                discovery.setDateTime(resultSet.getTimestamp("dateTime"));
                discovery.setTag(resultSet.getString("tag"));
                discovery.setContent(resultSet.getString("content"));
                discovery.setImgURL(resultSet.getString("imgURL"));
                discovery.setContactQQ(resultSet.getString("contactQQ"));
                discovery.setContactPhone(resultSet.getString("contactPhone"));

                User user = new User();
                user.setHeadURL(resultSet.getString("headURL"));
                user.setName(resultSet.getString("name"));
                user.setRole(resultSet.getString("role"));
                user.setGender(resultSet.getString("gender"));

                DiscoveryCard discoveryCard = new DiscoveryCard();
                discoveryCard.setDiscovery(discovery);
                discoveryCard.setUser(user);

                discoveryCardList.add(discoveryCard);
            }
            Gson gson = new Gson();
            String r = gson.toJson(discoveryCardList);
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
