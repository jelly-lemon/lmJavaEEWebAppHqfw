package servlet.view;

import base.BaseHttpServlet;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DiscoveryCardServlet", urlPatterns = {"/DiscoveryCardServlet"})
public class DiscoveryCardServlet extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        String method = request.getParameter("method");
        switch (method) {
            case "refresh": {
                String sql = String.format("SELECT * FROM DiscoveryCard  ORDER BY dateTime DESC limit 5;");
                DBDAO.queryList(sql, response);
                break;
            }
            case "loadMore": {
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM DiscoveryCard  ORDER BY dateTime DESC limit %s,5;", start);
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
