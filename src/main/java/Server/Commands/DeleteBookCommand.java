package Server.Commands;

import Lucene.WriteIndex;
import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

public class DeleteBookCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        DBManager.getInstance().deleteBook(bookId);
        deleteIndex(bookId);
        return page;
    }

    public void deleteIndex(int bookId){
        IndexSearcher searcher = null;
        try {
            QueryParser qp = new QueryParser("id", new StandardAnalyzer());
            Query idQuery = qp.parse(String.valueOf(bookId));
            WriteIndex.createWriter().deleteDocuments(idQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
