package servlet.table;

import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CommodityServlet", urlPatterns = "/CommodityServlet")
public class CommodityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 获取请求参数
        String method = request.getParameter("method");
        if (method == null) {
            return;
        }

        switch (method) {
            case "refresh": {
                String sql = "SELECT * FROM Commodity;";
                DBDAO.queryList(sql, response);
                break;
            }
        }
    }
}
