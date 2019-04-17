package servlet;

import com.google.gson.Gson;
import entity.Discovery;
import model.PublishDiscoveryModel;
//import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.commons.fileupload.FileItem;
import utils.DBDAO;
import utils.UploadFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PublishDiscoveryServlet", urlPatterns = {"/PublishDiscoveryServlet"})
public class PublishDiscoveryServlet extends HttpServlet {
    private PublishDiscoveryModel publishDiscoveryModel = new PublishDiscoveryModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 字符编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<FileItem> fileItemList = UploadFile.getFileItemList(request);
        // 获取参数键值对
        Map<String, String> parameterMap = UploadFile.getParameterMap(fileItemList);

        String method = parameterMap.get("method");
        switch (method) {
            case "insert": {
                Discovery discovery = new Gson().fromJson(parameterMap.get("discovery"), Discovery.class);
                // 插入语句
                String sql = String.format("INSERT INTO Discovery(phone, content, tag, dateTime, contactQQ, contactPhone) VALUES('%s', '%s', '%s', NOW(), '%s', '%s');",
                        discovery.getPhone(), discovery.getContent(), discovery.getTag(), discovery.getContactQQ(), discovery.getContactPhone());
                // 插入
                DBDAO.insert(sql);
                // 获取插入后得到的 discoveryID
                String discoveryID = getDiscoveryID(discovery.getPhone());
                // 保存图片到磁盘
                String imgURL = UploadFile.saveDiscoveryImage(fileItemList, discoveryID);
                // 保存图片名字到数据库
                publishDiscoveryModel.insertImgUrl(discoveryID, imgURL);
                break;
            }
        }

        /*String phone = parameterMap.get("phone");
        String content = parameterMap.get("content");
        String tag = parameterMap.get("tag");
        String discoveryID = publishDiscoveryModel.insertAndGetDiscoveryID(phone, content, tag);    // 先插入部分内容，获取 discoveryID
        // 打印在控制台
        System.out.println("PublishDiscoveryServlet:" + " discoveryID:" + discoveryID + " phone:" + phone + " content:" + content + " tag:" + tag);
        // 保存图片
        String imgURL = UploadFile.saveDiscoveryImage(fileItemList, discoveryID);
        // 保存图片名字到数据库
        publishDiscoveryModel.insertImgUrl(discoveryID, imgURL);*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    String getDiscoveryID(String phone) {
        String discoveryID = null;
        try {
            // 获取连接
            Connection connection = DBDAO.getConnection();
            // 获取 statement
            Statement statement = connection.createStatement();


            // 查询语句
            String sql = String.format("SELECT MAX(discoveryID) FROM Discovery WHERE phone='%s';", phone);
            // 执行查询
            ResultSet resultSet = statement.executeQuery(sql);
            // 获取结果
            if (resultSet.next()) {
                discoveryID = resultSet.getString("MAX(discoveryID)");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discoveryID;
    }


}
