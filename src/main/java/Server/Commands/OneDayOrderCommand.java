package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Order;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OneDayOrderCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(OneDayOrderCommand.class);
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.librarian.acc");
        String login = requestContent.getParameter("login");
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        DBManager dbManager = DBManager.getInstance();
        dbManager.addOneDayOrder(login,bookId);
        List<Order> orders = dbManager.getAllOrders();
        requestContent.addSessionAttribute("orders", orders);
        LOG.info("Creating 1 day order for user " + login + " , book id: " + bookId);
        return page;
    }
}
