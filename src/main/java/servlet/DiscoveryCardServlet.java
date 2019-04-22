package servlet;

import base.BaseHttpServlet;
import interfaces.IDiscoveryServlet;
import model.DiscoveryModel;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet(name = "DiscoveryCardServlet", urlPatterns = {"/DiscoveryCardServlet"})
public class DiscoveryCardServlet extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        String method = req.getParameter("method");
        switch (method) {
            case "refresh": {
                String sql = String.format("SELECT * FROM DiscoveryCard  ORDER BY dateTime DESC limit 5;");
                DBDAO.query(sql, resp);
                break;
            }
            case "loadMore": {
                String start = req.getParameter("start");
                String sql = String.format("SELECT * FROM DiscoveryCard  ORDER BY dateTime DESC limit %s,5;", start);
                DBDAO.query(sql, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
