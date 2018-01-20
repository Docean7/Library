package Server.Commands;

import Lucene.WriteIndex;
import Server.Managers.RequestContent;
import db.DBManager;
import db.Entity.Book;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Lucene.WriteIndex.createBookDocument;

public class IndexBookCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
        IndexWriter writer = null;
        String page = null;
        try {
            writer = WriteIndex.createWriter();
            List<Document> documents = new ArrayList<>();
            DBManager db = DBManager.getInstance();

            for (int i = 4; i < 17; i++) {
                Book book = db.getBookById(i);
                Document document = createBookDocument(book);
                documents.add(document);
            }

//        //Let's clean everything first
           writer.deleteAll();
//
            writer.addDocuments(documents);
            writer.commit();
            System.out.println("ADDED INDEXES");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}
