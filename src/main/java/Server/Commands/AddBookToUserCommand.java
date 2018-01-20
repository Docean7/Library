package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;

import javax.servlet.http.HttpServletResponse;

public class AddBookToUserCommand implements ActionCommand{
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.account");
        DBManager dbManager = DBManager.getInstance();

        int bookID = Integer.parseInt(requestContent.getParameter("book_id"));
        int userID = Integer.parseInt(requestContent.getParameter("user_id"));
        if(dbManager.addBookToUser(bookID, userID)){
            requestContent.addSessionAttribute( "bookList", dbManager.getBooksForUser(userID));
        }
        else {
            System.out.println("Duplicate");
        }
        return page;
    }
}
