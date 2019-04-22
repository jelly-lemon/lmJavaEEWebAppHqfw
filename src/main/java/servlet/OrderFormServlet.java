package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.OrderForm;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "OrderFormServlet", urlPatterns = "/OrderFormServlet")
public class OrderFormServlet extends HttpServlet {

    // 数量
    //private int n = 5;
    // 开始
    //private int start = n;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // encoding
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String method = request.getParameter("method");
        if (method == null) {
            return;
        }

        switch (method) {
            case "insert": {
                JsonObject orderForm = new Gson().fromJson(request.getParameter("orderForm"), JsonObject.class);
                String sql = String.format("INSERT INTO OrderForm(dateTime, buyerPhone, receiveName, receivePhone, receiveAddress, orderFormStatus, totalPrice) " +
                                "VALUES(NOW(), '%s', '%s', '%s', '%s', '%s', %s);",
                        orderForm.get("buyerPhone").getAsString(),
                        orderForm.get("receiveName").getAsString(),
                        orderForm.get("receivePhone").getAsString(),
                        orderForm.get("receiveAddress").getAsString(),
                        orderForm.get("orderFormStatus").getAsString(),
                        orderForm.get("totalPrice").getAsString());


                String orderFormID = insertOrderFormAndGetOrderFormID(sql);


                // 转发给 PurchasedItemServlet
                request.setAttribute("orderFormID", orderFormID);
                request.getRequestDispatcher("PurchasedItemServlet").forward(request, response);
                break;
            }

            case "update": {
                String orderFormID = request.getParameter("orderFormID");
                String sql = String.format("UPDATE OrderForm SET orderFormStatus = '交易完成' WHERE orderFormID = %s;", orderFormID);
                DBDAO.update(sql);
                break;
            }
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
                String sql = "SELECT * FROM OrderForm ORDER BY dateTime DESC limit 5;";
                DBDAO.query(sql, response);
                break;
            }
            case "loadMore": {
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM OrderForm ORDER BY dateTime DESC LIMIT %s,5;", start);
                DBDAO.query(sql, response);
                break;
            }
            case "unpaidRefresh": {
                String sql = "SELECT * FROM OrderForm  WHERE orderFormStatus = '等待付款' ORDER BY dateTime DESC LIMIT 5;";
                DBDAO.query(sql, response);
                break;
            }
            case "unpaidLoadMore": {
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM OrderForm WHERE orderFormStatus = '等待付款' ORDER BY dateTime DESC LIMIT %s,5;", start);
                DBDAO.query(sql, response);
                break;
            }
        }

    }

    //private void insert

    private void loadMoreUnPaidOrderForm(HttpServletResponse response, int start) {
        String sql = String.format("SELECT * FROM OrderForm WHERE orderFormStatus = '等待付款' ORDER BY dateTime DESC LIMIT %d,5;", start);

        onWrite(response, getOrderForm(sql));
    }

    private void getUnpaidOrderForm(HttpServletResponse response) {
        String sql = String.format("SELECT * FROM OrderForm  WHERE orderFormStatus = '等待付款' ORDER BY dateTime DESC LIMIT %d;", 5);

        onWrite(response, getOrderForm(sql));
    }


    private void onUpdateStatus(String sql) {
        // get connection
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String insertOrderFormAndGetOrderFormID(String sql) {
        String orderFormID = null;
        try {
            // get connection
            // 首先插入
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);


            // 再获取 ID
            sql = "SELECT LAST_INSERT_ID()";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                orderFormID = resultSet.getString(1);
            }


            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderFormID;
    }

    /**
     * 请求刷新时返回的数据
     * @param response
     */
    private void getAllOrderForm(HttpServletResponse response) {
        String sql = String.format("SELECT * FROM OrderForm ORDER BY dateTime DESC LIMIT %d;", 5);

        onWrite(response, getOrderForm(sql));
    }

    /**
     * 请求加载更多时返回的数据
     * @param response
     */
    private void loadMoreOrderForm(HttpServletResponse response, int start) {
        String sql = String.format("SELECT * FROM OrderForm ORDER BY dateTime DESC LIMIT %d,5;", start);

        onWrite(response, getOrderForm(sql));
    }


    private void onWrite(HttpServletResponse response, String result) {
        try {
            Writer writer = response.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getOrderForm(String sql) {
        try {
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            List<OrderForm> orderFormList = new ArrayList<>();
            while (resultSet.next()) {
                OrderForm orderForm = new OrderForm();
                orderForm.setOrderFormID(resultSet.getInt("orderFormID"));
                orderForm.setDateTime(resultSet.getTimestamp("dateTime"));
                orderForm.setShoppingList(resultSet.getString("shoppingList"));
                orderForm.setBuyerPhone(resultSet.getString("buyerPhone"));
                orderForm.setReceiveName(resultSet.getString("receiveName"));
                orderForm.setReceivePhone(resultSet.getString("receivePhone"));
                orderForm.setReceiveAddress(resultSet.getString("receiveAddress"));
                orderForm.setOrderFormStatus(resultSet.getString("orderFormStatus"));
                orderForm.setTotalPrice(resultSet.getFloat("totalPrice"));

                orderFormList.add(orderForm);
            }
            statement.close();
            connection.close();

            Gson gson = new Gson();
            String r = gson.toJson(orderFormList);

            System.out.println("OrderFormServlet:getOrderForm:" + r);


            return r;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
