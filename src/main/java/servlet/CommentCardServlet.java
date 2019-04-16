package servlet;

import com.google.gson.Gson;
import entity.Comment;
import entity.CommentCard;
import entity.User;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                String sql = String.format("SELECT * FROM CommentCard WHERE discoveryID = %s ORDER BY dateTime ASC;", discoveryID);
                write(response, getCommentCard(sql));
                break;
            }
            case "loadMore": {
                String discoveryID = request.getParameter("discoveryID");
                String start = request.getParameter("start");
                String sql = String.format("SELECT * FROM CommentCard WHERE discoveryID = %s ORDER BY dateTime ASC LIMIT %s,5;", discoveryID, start);
                write(response, getCommentCard(sql));
                break;
            }
        }
    }

    private String getCommentCard(String sql) {
        List<CommentCard> commentCardList = new ArrayList<>();
        try {
            Connection connection = DBDAO.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                CommentCard commentCard = new CommentCard();

                Comment comment = new Comment();
                comment.setCommentID(resultSet.getInt("commentID"));
                comment.setSenderPhone(resultSet.getString("senderPhone"));
                comment.setDateTime(resultSet.getTimestamp("dateTime"));
                comment.setContent(resultSet.getString("content"));
                comment.setDiscoveryID(resultSet.getInt("discoveryID"));

                User user = new User();
                user.setName(resultSet.getString("senderName"));
                user.setRole(resultSet.getString("senderRole"));
                user.setHeadURL(resultSet.getString("senderHeadURL"));

                commentCard.setComment(comment);
                commentCard.setUser(user);
                commentCardList.add(commentCard);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String r = new Gson().toJson(commentCardList);
        System.out.println("CommentCardServlet:" + r);
        return r;
    }

    private void write(HttpServletResponse response, String result) {
        try {
            Writer writer = response.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
