package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Order;
import db.UserTypeEnum;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeleteOrderCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(DeleteOrderCommand.class.getName());
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.librarian.acc");
        int userType = Integer.parseInt(String.valueOf(requestContent.getSessionAttribute("userType")));
        int userId = Integer.parseInt(String.valueOf(requestContent.getParameter("user_id")));
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        DBManager dbManager = DBManager.getInstance();
        if(dbManager.isBookDelivered(userId, bookId) && userType == UserTypeEnum.REGISTERED_USER.value()){
            requestContent.addRequestAttribute("errorMessage", "You have no permission");
            page = ConfigurationManager.getProperty("path.page.apperror");
        } else {
            dbManager.deleteOrder(userId, bookId);
            LOG.debug("Deleting order of book " + bookId + " by user " + userId);
            if (userType == UserTypeEnum.REGISTERED_USER.value()) {
                page = ConfigurationManager.getProperty("path.page.account");
                requestContent.addSessionAttribute("bookList", dbManager.getBooksForUser(userId));
            }
            List<Order> orders = dbManager.getAllOrders();
            requestContent.addSessionAttribute("orders", orders);
        }
        return page;
    }
}
