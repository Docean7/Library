package Server.Commands;

import Lucene.ReadIndex;
import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;

public class SearchBookCommand implements ActionCommand {
    public static final String QUERY = "query";
    @Override
    public String execute(RequestContent requestContent) {
        IndexSearcher searcher = null;
        String page = null;

        try {
            searcher = ReadIndex.createSearcher();
            //Search by Title
            String query = requestContent.getParameter(QUERY);
            System.out.println(query  );
            TopDocs foundBookDocs = ReadIndex.searchByTitle(query, searcher);

            System.out.println("Total Results :: " + foundBookDocs.totalHits);
            DBManager dbManager = DBManager.getInstance();
            ArrayList<Book> foundBooks = new ArrayList<>();
            for (ScoreDoc sd : foundBookDocs.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                Book book = dbManager.getBookById(Integer.parseInt(d.get("id")));
                if(book != null){
                    foundBooks.add(book);
                    System.out.println(book);
                }
            }
            TopDocs foundByAuthor = ReadIndex.searchByAuthor(query, searcher);
            for(ScoreDoc sd : foundByAuthor.scoreDocs){
                Document d = searcher.doc(sd.doc);
                Book book = dbManager.getBookById(Integer.parseInt(d.get("id")));
                if(book != null){
                    foundBooks.add(book);
                    System.out.println(book);
                }
            }

            requestContent.addSessionAttribute("foundBooks", foundBooks);
            page = ConfigurationManager.getProperty("path.page.search");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}
