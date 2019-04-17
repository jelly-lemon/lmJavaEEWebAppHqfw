package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.OrderFormCard;
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


    /*void query(String sql, HttpServletResponse response) {
        //List<JsonObject> jsonObjectList = new ArrayList<>();
        JsonArray jsonArray = new JsonArray();
        try {
            // 查询
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // 提取
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                JsonObject jsonObject = new JsonObject();

                // 一条数据
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    jsonObject.addProperty(resultSetMetaData.getColumnLabel(i), resultSet.getString(i));
                }

                jsonArray.add(jsonObject);
                //jsonObjectList.add(jsonObject);
            }

            // 回复
            Writer writer = response.getWriter();
            //writer.write(new Gson().toJson(jsonObjectList));
            writer.write(jsonArray.toString());
            writer.flush();

            // 关闭
            resultSet.close();
            statement.close();
            connection.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
