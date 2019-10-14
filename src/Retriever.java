import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import ranking.Query;

import java.util.Vector;

public class Retriever {
    Vector<Document> documents = new Vector<>();
    ReverseIndex index = new ReverseIndex();

    public Retriever(){};

    public void addDocument(String title, String filename) throws NotValidDocumentException {
        Document doc = new Document(title, filename);
        index.populateIndex(doc);
        documents.add(doc);
    }

    public void addDocument(Document doc) throws NotValidDocumentException {
        index.populateIndex(doc);
        documents.add(doc);
    }

    public Vector<String> computeQuery(Query query){
        return null;
    }

    public Vector<String> computeQuery(String querystring){
        Query query = new Query(querystring);
        return null;
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
