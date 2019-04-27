package servlet.table;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(name = "OrderFormServlet", urlPatterns = "/OrderFormServlet")
public class OrderFormServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // encoding
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String method = request.getParameter("method");
        if (method == null) {
            return;
        }

        switch (method) {
            case "paySuccess": {
                String orderFormID = request.getParameter("orderFormID");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String wechatDealID = simpleDateFormat.format(new Date()) + "1234";
                String sql = String.format("UPDATE OrderForm SET orderFormStatus = '交易完成', dealDateTime = NOW(), wechatDealID = '%s' WHERE orderFormID = %s;", wechatDealID, orderFormID);

                DBDAO.update(sql);
                break;
            }

            case "insert": {
                JsonObject orderForm = new Gson().fromJson(request.getParameter("orderForm"), JsonObject.class);
                String sql = String.format("INSERT INTO OrderForm(createDateTime, buyerPhone, receiveName, receivePhone, receiveAddress, orderFormStatus, totalPrice) " +
                                "VALUES(NOW(), '%s', '%s', '%s', '%s', '%s', %s);",
                        orderForm.get("buyerPhone").getAsString(),
                        orderForm.get("receiveName").getAsString(),
                        orderForm.get("receivePhone").getAsString(),
                        orderForm.get("receiveAddress").getAsString(),
                        orderForm.get("orderFormStatus").getAsString(),
                        orderForm.get("totalPrice").getAsString());


                String orderFormID = DBDAO.insertAndGeID(sql);

                // 转发给 PurchasedItemServlet
                request.setAttribute("orderFormID", orderFormID);
                request.getRequestDispatcher("PurchasedItemServlet").forward(request, response);
                break;
            }

            /*case "update": {
                String orderFormID = request.getParameter("orderFormID");
                String sql = String.format("UPDATE OrderForm SET orderFormStatus = '交易完成' WHERE orderFormID = %s;", orderFormID);
                DBDAO.update(sql);
                break;
            }*/
        }
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
                String sql = "SELECT * FROM OrderForm ORDER BY createDateTime DESC limit 5;";
                DBDAO.queryList(sql, response);
                break;
            }

            case "loadMore": {
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM OrderForm ORDER BY createDateTime DESC limit %s,5;", start);
                DBDAO.queryList(sql, response);
                break;
            }
        }

    }
}
