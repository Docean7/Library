package Lucene;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class ReadIndex {
    private static final String INDEX_DIR = "D:/Lucene/LibIndex";



    public static TopDocs searchByTitle(String title, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("title", new StandardAnalyzer());
        Query titleQuery = qp.parse(title);
        TopDocs hits = searcher.search(titleQuery, 10);
        return hits;
    }

    public static TopDocs searchByAuthor(String author, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("author", new StandardAnalyzer());
        Query authorQuery = qp.parse(author);
        TopDocs hits = searcher.search(authorQuery, 10);
        return hits;
    }

    public static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
}