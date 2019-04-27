package servlet.table;

import com.google.gson.Gson;
import entity.Comment;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CommentServlet", urlPatterns = "/CommentServlet")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String method = request.getParameter("method");
        switch (method){
            case "insert": {
                Comment comment = new Gson().fromJson(request.getParameter("comment"), Comment.class);

                String sql = String.format("INSERT INTO `Comment`(dateTime, senderPhone,  discoveryID, content) VALUES(NOW() ,'%s', %s, '%s');",
                        comment.getSenderPhone(), comment.getDiscoveryID(), comment.getContent());
                DBDAO.insert(sql);
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
