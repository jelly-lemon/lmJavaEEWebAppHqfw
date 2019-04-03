package servlet;

import model.PublishDiscoveryModel;
import utils.UploadFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PublishDiscoveryServlet", urlPatterns = {"/PublishDiscoveryServlet"})
public class PublishDiscoveryServlet extends HttpServlet {
    private PublishDiscoveryModel publishDiscoveryModel = new PublishDiscoveryModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");           // 字符编码
        response.setCharacterEncoding("utf-8");

        String phone = request.getParameter("phone");       // phone
        String content = request.getParameter("content");   // content
        String tag = request.getParameter("tag");           // tag
        System.out.println("phone:" + phone);
        System.out.println("content:" + content);
        System.out.println("tag:" + tag);
        String article_id = publishDiscoveryModel.insertAndGetArticleId(phone, content, tag);    // 先插入部分内容，获取 article_id
        System.out.println("article_id:" + article_id);
        // 图片 url 的 json
        String img_url_json = UploadFile.saveFile(request, article_id);
        // 把图片路径保存到数据库当中
        publishDiscoveryModel.insertImgUrl(img_url_json, article_id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
