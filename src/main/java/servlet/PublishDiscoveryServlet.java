package servlet;

import model.PublishDiscoveryModel;
//import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.commons.fileupload.FileItem;
import utils.UploadFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PublishDiscoveryServlet", urlPatterns = {"/PublishDiscoveryServlet"})
public class PublishDiscoveryServlet extends HttpServlet {
    private PublishDiscoveryModel publishDiscoveryModel = new PublishDiscoveryModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");           // 字符编码
        response.setCharacterEncoding("utf-8");

        List<FileItem> fileItemList = UploadFile.getFileItemList(request);
        // 获取参数键值对
        Map<String, String> parameterMap = UploadFile.getParameterMap(fileItemList);

        String phone = parameterMap.get("phone");
        String content = parameterMap.get("content");
        String tag = parameterMap.get("tag");
        String discoveryID = publishDiscoveryModel.insertAndGetDiscoveryID(phone, content, tag);    // 先插入部分内容，获取 discoveryID
        // 打印在控制台
        System.out.println("PublishDiscoveryServlet:" + " discoveryID:" + discoveryID + " phone:" + phone + " content:" + content + " tag:" + tag);
        // 保存图片
        String imgURL = UploadFile.saveDiscoveryImage(fileItemList, discoveryID);
        // 保存图片名字到数据库
        publishDiscoveryModel.insertImgUrl(discoveryID, imgURL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
