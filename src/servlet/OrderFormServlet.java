package servlet;

import com.google.gson.Gson;
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
import java.util.List;

@WebServlet(name = "OrderFormServlet", urlPatterns = "/OrderFormServlet")
public class OrderFormServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // encoding
        request.setCharacterEncoding("utf-8");
        // get json
        String json = request.getParameter("orderForm");
        // log
        System.out.println("OrderFormServlet:" + json);
        // convert into object
        Gson gson = new Gson();
        OrderForm orderForm = gson.fromJson(json, OrderForm.class);
        // get connection
        Connection connection = DBDAO.getConnection();
        try {
            String sql = "INSERT INTO OrderForm(dateTime, shoppingList, buyerPhone, receiveName, receivePhone, receiveAddress, orderFormStatus, totalPrice)  VALUES(NOW(), ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, orderForm.getShoppingList());
            preparedStatement.setString(2, orderForm.getBuyerPhone());
            preparedStatement.setString(3, orderForm.getReceiveName());
            preparedStatement.setString(4, orderForm.getReceivePhone());
            preparedStatement.setString(5, orderForm.getReceiveAddress());
            preparedStatement.setString(6, orderForm.getOrderFormStatus());
            preparedStatement.setFloat(7, orderForm.getTotalPrice());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM OrderForm;";
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

            System.out.println("OrderForm:" + r);

            Writer writer = response.getWriter();
            writer.write(r);
            writer.flush();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
