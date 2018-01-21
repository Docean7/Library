package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Order;

import java.util.List;

public class OneDayOrderCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.librarian.acc");
        String login = requestContent.getParameter("login");
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        DBManager dbManager = DBManager.getInstance();
        dbManager.addOneDayOrder(login,bookId);
        List<Order> orders = dbManager.getAllOrders();
        requestContent.addSessionAttribute("orders", orders);
        return page;
    }
}
