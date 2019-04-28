package servlet.view;

import base.BaseHttpServlet;
import com.google.gson.Gson;
import utils.DBDAO;
import utils.GsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "DiscoveryCardServlet", urlPatterns = {"/DiscoveryCardServlet"})
public class DiscoveryCardServlet extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        String method = request.getParameter("method");


        String selectedTagList = request.getParameter("selectedTagList");
        selectedTagList = selectedTagList.replace('[', ' ');
        selectedTagList = selectedTagList.replace(']', ' ');
        selectedTagList = selectedTagList.replace('"', '\'');


        switch (method) {



            case "refresh": {
                String sql = String.format("SELECT * FROM discoverycard WHERE tag IN (%s) ORDER BY dateTime DESC limit 5;", selectedTagList);
                DBDAO.queryList(sql, response);
                break;
            }
            case "loadMore": {
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM DiscoveryCard  WHERE tag IN (%s) ORDER BY dateTime DESC limit %s,5;", selectedTagList, start);
                DBDAO.queryList(sql, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
