package servlet;

import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PurchasedItemCardServlet", urlPatterns = "/PurchasedItemCardServlet")
public class PurchasedItemCardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String method = request.getParameter("method");

        switch (method) {
            case "refresh": {
                String orderFormID = request.getParameter("orderFormID");
                String sql = String.format("SELECT * FROM PurchasedItemCard WHERE orderFormID = %s;", orderFormID);
                DBDAO.query(sql, response);
                break;
            }
        }
    }
}
