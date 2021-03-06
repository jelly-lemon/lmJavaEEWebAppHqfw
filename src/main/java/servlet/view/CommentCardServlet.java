package servlet.view;

import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CommentCardServlet", urlPatterns = "/CommentCardServlet")
public class CommentCardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");


        String method = request.getParameter("method");
        switch (method) {
            case "refresh": {
                String discoveryID = request.getParameter("discoveryID");
                String sql = String.format("SELECT * FROM CommentCard WHERE discoveryID = %s ORDER BY dateTime ASC LIMIT 20;", discoveryID);
                DBDAO.queryList(sql, response);
                break;
            }
            case "loadMore": {
                String discoveryID = request.getParameter("discoveryID");
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM CommentCard WHERE discoveryID = %s ORDER BY dateTime ASC LIMIT %s,20;", discoveryID, start);
                DBDAO.queryList(sql, response);
                break;
            }
        }
    }


}
