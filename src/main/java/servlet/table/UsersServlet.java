package servlet.table;

import base.BaseHttpServlet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                DBDAO.checkAccount(phone, password, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);


    }
}
