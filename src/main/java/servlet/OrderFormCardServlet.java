package servlet;

import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "OrderFormCardServlet", urlPatterns = "/OrderFormCardServlet")
public class OrderFormCardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 字符编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String method = request.getParameter("method");
        switch (method) {
            case "query": {
                String sql = "SELECT * FROM OrderFormCard ORDER BY dateTime DESC limit 5;";
                DBDAO.query(sql, response);
                break;
            }
        }
    }
}
