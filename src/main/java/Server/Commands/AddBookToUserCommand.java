package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddBookToUserCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(AddBookToUserCommand.class);
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.catalog");
        DBManager dbManager = DBManager.getInstance();

        int bookID = Integer.parseInt(requestContent.getParameter("book_id"));
        int userID = Integer.parseInt(requestContent.getParameter("user_id"));
        if(dbManager.addBookToUser(bookID, userID)){
            requestContent.addSessionAttribute( "bookList", dbManager.getBooksForUser(userID));
            LOG.info("Book with id " + bookID + " added to user with id" + userID);
        }
        else {
            LOG.error("Error while adding");
        }
        return page;
    }
}
