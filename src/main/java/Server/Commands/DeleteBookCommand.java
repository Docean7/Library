package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;

public class DeleteBookCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        DBManager.getInstance().deleteBook(bookId);
        return page;
    }
}
