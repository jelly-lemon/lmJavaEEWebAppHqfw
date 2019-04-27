package servlet.table;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;



@WebServlet(name = "PurchasedItemServlet", urlPatterns = "/PurchasedItemServlet")
public class PurchasedItemServlet extends HttpServlet {
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
                String orderFormID = request.getAttribute("orderFormID").toString();
                Type type = new TypeToken<List<JsonObject>>(){}.getType();
                List<JsonObject> purchasedItemList = new Gson().fromJson(request.getParameter("purchasedItemList"), type);
                String sql = String.format("INSERT INTO PurchasedItem(orderFormID, commodityID, number) VALUES(%s, ?, ?);", orderFormID);
                batchInsert(sql, purchasedItemList);

                // 回复给客户端
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("orderFormID", orderFormID);
                Writer writer = response.getWriter();
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    void batchInsert(String sql, List<JsonObject> purchasedItemList) {
        Connection connection = DBDAO.getConnection();

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (JsonObject purchasedItem : purchasedItemList) {
                preparedStatement.setString(1, purchasedItem.get("commodityID").getAsString());
                preparedStatement.setString(2, purchasedItem.get("number").getAsString());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
