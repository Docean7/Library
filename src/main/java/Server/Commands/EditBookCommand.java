package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EditBookCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(EditBookCommand.class);
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        Book book = new Book();
        book.setId(Integer.parseInt(requestContent.getParameter("book_id")));
        book.setTitle(requestContent.getParameter("title"));
        book.setAuthor(requestContent.getParameter("author"));
        book.setGenre(requestContent.getParameter("genre"));
        book.setCategory(requestContent.getParameter("category"));
        book.setPublisher(requestContent.getParameter("publisher"));
        book.setCountry(requestContent.getParameter("country"));
        book.setYear(Integer.parseInt(requestContent.getParameter("year")));
        book.setRating(Double.parseDouble(requestContent.getParameter("rating")));
        book.setQuantity(Integer.parseInt(requestContent.getParameter("quantity")));

        DBManager.getInstance().updateBook(book);
        LOG.info("Updating book " + book);
        return page;
    }
}
