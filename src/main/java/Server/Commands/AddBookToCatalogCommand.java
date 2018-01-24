package Server.Commands;

import Lucene.WriteIndex;
import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;

import static Lucene.WriteIndex.createBookDocument;

public class AddBookToCatalogCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(AddBookToCatalogCommand.class);
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        Book book = getBookFromRequest(requestContent);
        DBManager.getInstance().addBookToCatalog(book);
        indexBook(book);
        LOG.info("Adding book" + book);
        return page;
    }

    private Book getBookFromRequest(RequestContent requestContent){
        Book book = new Book();
        String title = requestContent.getParameter("title");
        book.setTitle(title);
        String author = requestContent.getParameter("author");
        book.setAuthor(author);
        String genre = requestContent.getParameter("genre");
        book.setGenre(genre);
        String category = requestContent.getParameter("category");
        book.setCategory(category);
        String publisher = requestContent.getParameter("publisher");
        book.setPublisher(publisher);
        String country = requestContent.getParameter("country");
        book.setCountry(country);
        int year = Integer.parseInt(requestContent.getParameter("year"));
        book.setYear(year);
        double rating = Double.parseDouble(requestContent.getParameter("rating"));
        book.setRating(rating);
        int quantity = Integer.parseInt(requestContent.getParameter("quantity"));
        book.setQuantity(quantity);
        return book;
    }

   public void indexBook(Book book){
       IndexWriter writer = null;
       String page = null;
       try {
           writer = WriteIndex.createWriter();
           Document document = createBookDocument(book);
           writer.addDocument(document);
           writer.commit();
           LOG.debug("Indexed " + book);
           writer.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
