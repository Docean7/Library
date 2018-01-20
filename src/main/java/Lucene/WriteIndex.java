package Lucene;

import db.Entity.Book;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class WriteIndex
{
    private static final String INDEX_DIR = "D:/Lucene/LibIndex";

    public static Document createBookDocument(Book book)
    {

        Document document = new Document();
        System.out.println("CREATING DOCUMENT");
//        document.add(new StringField("id", id.toString() , Field.Store.YES));
//        document.add(new TextField("firstName", firstName , Field.Store.YES));
        document.add(new StringField("id", String.valueOf(book.getId()), Field.Store.YES));
        document.add(new TextField("title", book.getTitle(), Field.Store.YES));
        document.add(new TextField("author", book.getAuthor(), Field.Store.YES));

        return document;
    }

    public static IndexWriter createWriter() throws IOException
    {
        System.out.println("CREATING WRITER");
        FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }
}