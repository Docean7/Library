package Server.Commands;

import Lucene.ReadIndex;
import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;

public class SearchBookCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(SearchBookCommand.class);
    public static final String QUERY = "query";
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        IndexSearcher searcher = null;
        String page = ConfigurationManager.getProperty("path.page.catalog");

        try {
            searcher = ReadIndex.createSearcher();
            //Search by Title
            String query = requestContent.getParameter(QUERY);
            LOG.debug("Searching for" + query);
            TopDocs foundBookDocs = ReadIndex.searchByTitle(query, searcher);

            LOG.debug("Total Results :: " + foundBookDocs.totalHits);
            DBManager dbManager = DBManager.getInstance();
            ArrayList<Book> foundBooks = new ArrayList<>();
            for (ScoreDoc sd : foundBookDocs.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                Book book = dbManager.getBookById(Integer.parseInt(d.get("id")));
                if(book != null){
                    foundBooks.add(book);
                    LOG.debug(book);
                }
            }
            TopDocs foundByAuthor = ReadIndex.searchByAuthor(query, searcher);
            for(ScoreDoc sd : foundByAuthor.scoreDocs){
                Document d = searcher.doc(sd.doc);
                Book book = dbManager.getBookById(Integer.parseInt(d.get("id")));
                if(book != null){
                    foundBooks.add(book);
                    LOG.debug(book);
                }
            }

            requestContent.addSessionAttribute("foundBooks", foundBooks);
            page = ConfigurationManager.getProperty("path.page.search");
        } catch (Exception e) {
            e.printStackTrace();
            requestContent.addRequestAttribute("searchError", "Incorrect input. Try again");

        }
        return page;
    }
}
