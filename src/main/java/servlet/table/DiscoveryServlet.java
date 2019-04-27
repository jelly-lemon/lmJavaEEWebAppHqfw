package servlet.table;

import base.BaseHttpServlet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.xml.internal.ws.encoding.ContentType;
import entity.Comment;
import org.apache.commons.fileupload.FileItem;
import utils.DBDAO;
import utils.UploadFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DiscoveryServlet", urlPatterns = "/DiscoveryServlet")
public class DiscoveryServlet extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);




        List<FileItem> fileItemList = UploadFile.getFileItemList(request);
        Map<String, String> parameterMap = UploadFile.getParameterMap(fileItemList);
        JsonObject discovery = new Gson().fromJson(parameterMap.get("discovery"), JsonObject.class);
        // 先插入基本内容
        String sql = String.format("INSERT INTO Discovery(phone, content, tag, dateTime, contactQQ, contactPhone, imgURL) VALUES('%s', '%s', '%s', NOW(), '%s', '%s', '[]');",
                discovery.get("phone").getAsString(), discovery.get("content").getAsString(), discovery.get("tag").getAsString(), discovery.get("contactQQ").getAsString(), discovery.get("contactPhone").getAsString());
        String discoveryID = DBDAO.insertAndGeID(sql);
        // 根据 ID 保存图片
        String imgURL = UploadFile.saveDiscoveryImage(fileItemList, discoveryID);
        // 再保存图片路径
        sql = String.format("UPDATE Discovery SET imgURL = '%s' WHERE discoveryID = %s;", imgURL, discoveryID);
        DBDAO.update(sql);
    }
}
