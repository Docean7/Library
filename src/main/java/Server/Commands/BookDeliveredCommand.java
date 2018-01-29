package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Order;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookDeliveredCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(BookDeliveredCommand.class.getName());
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.librarian.acc");
        int orderId = Integer.parseInt(String.valueOf(requestContent.getParameter("order_id")));
        DBManager dbManager = DBManager.getInstance();
        dbManager.markDelivered(true, orderId);
        LOG.debug("Book delivered. Order id: " + orderId);
        List<Order> orders = dbManager.getAllOrders();
        requestContent.addSessionAttribute("orders", orders);
        return page;
    }
}
