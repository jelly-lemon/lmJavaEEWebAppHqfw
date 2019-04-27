package servlet.table;

import base.BaseHttpServlet;
import utils.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "HydropowerBillServlet", urlPatterns = "/HydropowerBillServlet")
public class HydropowerBillServlet extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        String method = request.getParameter("method");
        switch (method) {
            case "refresh": {
                String room = request.getParameter("room");
                String sql = String.format("SELECT * FROM HydropowerBill WHERE room = '%s' ORDER BY month DESC LIMIT 0,5;", room);
                DBDAO.query(sql, response);
                break;
            }
            case "loadMore": {
                String start = request.getParameter("start");
                String room = request.getParameter("room");
                String sql = String.format("SELECT * FROM HydropowerBill WHERE room = '%s' ORDER BY month DESC LIMIT %s,5;", room, start);
                DBDAO.query(sql, response);
                break;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        String method = request.getParameter("method");
        switch (method) {
            case "paySuccess": {
                String month = request.getParameter("month");
                String room = request.getParameter("room");
                String buyerPhone = request.getParameter("buyerPhone");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String billNumber = simpleDateFormat.format(new Date()) + buyerPhone.substring(3, 7);
                String wechatDealNumber = simpleDateFormat.format(new Date()) + buyerPhone.substring(7, 11);

                String sql = String.format("UPDATE HydropowerBill SET `status` = '交易完成', buyerPhone = '%s', billNumber = '%s', wechatDealNumber = '%s', dealDateTime = NOW() WHERE `month` = %s AND room = '%s';",
                        buyerPhone, billNumber, wechatDealNumber, month, room);
                DBDAO.update(sql);
                break;
            }
            case "loadMore": {

                break;
            }
        }

    }
}
