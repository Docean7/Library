package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Order;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BookReturnedCommand implements ActionCommand{
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.librarian.acc");
        int orderId = Integer.parseInt(String.valueOf(requestContent.getParameter("order_id")));
        DBManager dbManager = DBManager.getInstance();
        dbManager.deleteOrder(orderId);
        List<Order> orders = dbManager.getAllOrders();
        requestContent.addSessionAttribute("orders", orders);
        return page;
    }
}
