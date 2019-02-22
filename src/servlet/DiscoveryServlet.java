package servlet;

import interfaces.IDiscoveryServlet;
import model.DiscoveryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet(name = "DiscoveryServlet", urlPatterns = {"/DiscoveryServlet"})
public class DiscoveryServlet extends HttpServlet implements IDiscoveryServlet {
    private DiscoveryModel discoveryModel = new DiscoveryModel(this);
    private HttpServletRequest mRequest;
    private HttpServletResponse mResponse;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mRequest = request;
        mResponse = response;

        mRequest.setCharacterEncoding("utf8");
        mResponse.setCharacterEncoding("utf8");

        // 获取结果
        discoveryModel.getArticleCard(5);
    }

    @Override
    public void response(String r)  {
        try {
            Writer writer = mResponse.getWriter();
            writer.write(r);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
