package servlet.table;

import base.BaseHttpServlet;
import org.apache.commons.fileupload.FileItem;
import utils.DBDAO;
import utils.UploadFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UsersServlet", urlPatterns = "/UsersServlet")
public class UsersServlet extends BaseHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        String method = request.getParameter("method");
        if (method == null)
            return;

        switch (method) {
            case "login": {
                // 获取电话、密码
                String phone = request.getParameter("phone");
                String password = request.getParameter("password");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

                System.out.println(simpleDateFormat.format(new Date()) + ":" + phone + ":" + password);
                DBDAO.checkAccount(phone, password, response);
                break;
            }
            case "get": {
                String phone = request.getParameter("phone");
                String sql = String.format("SELECT * FROM users WHERE phone = %s;", phone);
                DBDAO.query(sql, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        String contentType = request.getContentType();
        if (contentType.contains("multipart/form-data")) {


            List<FileItem> fileItemList = UploadFile.getFileItemList(request);
            Map<String, String> parameterMap = UploadFile.getParameterMap(fileItemList);


            String oldHeadURL = parameterMap.get("headURL");
            UploadFile.deleteFile(oldHeadURL);


            String phone = parameterMap.get("phone");
            String newHeadURL = UploadFile.saveHeadImage(UploadFile.getFileItem(fileItemList), phone);
            String sql = String.format("UPDATE users set headURL='%s' where phone='%s';", newHeadURL, phone);
            DBDAO.update(sql);
        } else {
            String method = request.getParameter("method");

            if (method == null)
                return;

            switch (method) {
                case "changeName": {
                    // 获取电话、密码
                    String phone = request.getParameter("phone");
                    String name = request.getParameter("name");
                    String sql = String.format("UPDATE users SET name = '%s' WHERE phone = '%s';", name, phone);
                    DBDAO.update(sql);
                    break;
                }
                case "changeAddress": {
                    // 获取电话、密码
                    String phone = request.getParameter("phone");
                    String building = request.getParameter("building");
                    String roomNumber = request.getParameter("roomNumber");
                    String sql = String.format("UPDATE users SET building = '%s', roomNumber='%s' WHERE phone = '%s';", building, roomNumber, phone);
                    DBDAO.update(sql);
                    break;
                }
                case "changeGender": {
                    // 获取电话、密码
                    String phone = request.getParameter("phone");
                    String gender = request.getParameter("gender");
                    String sql = String.format("UPDATE users SET gender = '%s' WHERE phone = '%s';", gender, phone);
                    DBDAO.update(sql);
                    break;
                }
                case "changeStudentID": {
                    // 获取电话、密码
                    String phone = request.getParameter("phone");
                    String studentID = request.getParameter("studentID");
                    String sql = String.format("UPDATE users SET studentID = '%s' WHERE phone = '%s';", studentID, phone);
                    DBDAO.update(sql);
                    break;
                }


            }
        }



    }
}
