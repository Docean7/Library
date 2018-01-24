package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;
import exception.AppException;
import exception.DBException;
import exception.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class GetCatalogCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(GetCatalogCommand.class);
    private static final int PAGESIZE = 6;
    private static List<Book> allCatalog;
    private static String previousSort;
    private static int pages;
    private static boolean checkedPages;

    public GetCatalogCommand() {
        try {
            allCatalog = DBManager.getInstance().getCatalog();
        } catch (DBException e) {
            LOG.error(Messages.ERR_GET_CATALOG,e);
        }
        pages = (int)Math.ceil((double)allCatalog.size()/PAGESIZE);

    }

    @Override
    public String execute(RequestContent requestContent) throws AppException {
        if(!checkedPages){
            requestContent.addSessionAttribute("pages", pages);
        }
        String page = null;
        int p = 1;
        String pageNumber = requestContent.getParameter("page");
        if(pageNumber != null){
            p = Integer.parseInt(pageNumber);
        }
        List<Book> curCatalog = null;
        Comparator<Book> comp = null;


        String sort = requestContent.getParameter("sort");
        if(sort != null) {
            requestContent.addSessionAttribute("sorting", sort);
        }

        sort = String.valueOf(requestContent.getSessionAttribute("sorting"));
        if(!sort.equals(previousSort)) {
            switch (sort) {
                case "name":
                    comp = Comparator.comparing(Book::getTitle);
                    requestContent.addSessionAttribute("name", "selected");
                    requestContent.removeSessionAttribute(previousSort);
                    break;
                case "author":
                    comp = Comparator.comparing(Book::getAuthor);
                    requestContent.addSessionAttribute("author", "selected");
                    requestContent.removeSessionAttribute(previousSort);
                    break;
                case "rating":
                    comp = Comparator.comparingDouble(Book::getRating).reversed();
                    requestContent.addSessionAttribute("rating", "selected");
                    requestContent.removeSessionAttribute(previousSort);
                    break;
                case "year":
                    comp = Comparator.comparingInt(Book::getYear).reversed();
                    requestContent.addSessionAttribute("year", "selected");
                    requestContent.removeSessionAttribute(previousSort);
                    break;
                case "publisher":
                    comp = Comparator.comparing(Book::getPublisher);
                    requestContent.addSessionAttribute("publisher", "selected");
                    requestContent.removeSessionAttribute(previousSort);
                    break;

            }
            if (comp != null && allCatalog != null) {
                allCatalog.sort(comp);
                previousSort = sort;
                LOG.debug("Sorting catalog by " + sort);
            }
        }

        //тут баг !!!
        if(allCatalog.size() >= p*PAGESIZE-1) {
            curCatalog = allCatalog.subList((p - 1) * PAGESIZE, p * PAGESIZE);
        }
        else if(allCatalog.size() >= (p-1)*PAGESIZE){
            curCatalog = allCatalog.subList((p - 1) * PAGESIZE, allCatalog.size());
        }
        else {
            LOG.error("Out of bounds. Catalog size: " + allCatalog.size() + " Requested: " + (p-1)*PAGESIZE);
        }


        requestContent.addSessionAttribute("catalog", curCatalog);
        requestContent.addSessionAttribute("allCatalog", allCatalog);
        page = ConfigurationManager.getProperty("path.page.catalog");
        return page;
    }
}
