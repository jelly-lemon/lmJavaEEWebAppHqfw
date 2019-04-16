package servlet;

import com.google.gson.Gson;
import entity.Commodity;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CommodityServlet", urlPatterns = "/CommodityServlet")
public class CommodityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Commodity;";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Commodity> commodityList = new ArrayList<>();
            while (resultSet.next()) {
                int commodityID = resultSet.getInt("commodityID");
                String name = resultSet.getString("name");
                String detail = resultSet.getString("detail");
                float price = resultSet.getFloat("price");
                String imgURL = resultSet.getString("imgURL");

                Commodity commodity = new Commodity();
                commodity.setCommodityID(commodityID);
                commodity.setName(name);
                commodity.setDetail(detail);
                commodity.setPrice(price);
                commodity.setImgURL(imgURL);

                commodityList.add(commodity);
            }
            Gson gson = new Gson();
            String r = gson.toJson(commodityList);

            Writer writer = response.getWriter();
            writer.write(r);
            writer.flush();
            writer.close();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
