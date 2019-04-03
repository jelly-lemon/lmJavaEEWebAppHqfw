package servlet;

import model.LoginModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private LoginModel loginModel = new LoginModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setContentType("text/json;charset=utf8");

        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        // 给自己看的控制台提示
        System.out.println("phone:" + phone);
        System.out.println("password:" + password );

        // 返回检查结果
        String result = loginModel.checkAccount(phone, password);
        Writer writer = response.getWriter();
        writer.write(result);
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
