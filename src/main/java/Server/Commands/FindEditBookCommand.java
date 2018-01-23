package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;

public class FindEditBookCommand implements ActionCommand {

    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        Book book = DBManager.getInstance().getBookById(bookId);
        requestContent.addRequestAttribute("returnedBook", book);

        return page;
    }
}
