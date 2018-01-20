package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;

import javax.servlet.http.HttpServletResponse;

public class BookDeliveredCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.account");
        int orderId = Integer.parseInt(String.valueOf(requestContent.getParameter("order_id")));
        DBManager.getInstance().UpdateBookDeliveryStatus(true, orderId);
        return page;
    }
}
