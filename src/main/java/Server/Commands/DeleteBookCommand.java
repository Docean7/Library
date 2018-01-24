package Server.Commands;

import Lucene.WriteIndex;
import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

public class DeleteBookCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(DeleteBookCommand.class);
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        int bookId = Integer.parseInt(requestContent.getParameter("book_id"));
        DBManager.getInstance().deleteBook(bookId);
        deleteIndex(bookId);
        LOG.info("Deleting book " + bookId);
        return page;
    }

    public void deleteIndex(int bookId){
        IndexSearcher searcher = null;
        try {
            QueryParser qp = new QueryParser("id", new StandardAnalyzer());
            Query idQuery = qp.parse(String.valueOf(bookId));
            WriteIndex.createWriter().deleteDocuments(idQuery);
            LOG.debug("Deleting index of book id" + bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
