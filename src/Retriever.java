import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import ranking.Query;
import ranking.RankingTool;

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

    public double tfidfSimilarity(Query query, Document document){

        RankingTool rtool = RankingTool.getInstance();
        double rank = 0.0;
        for(String word : query.getWords()){
            if(index.get(word) != null){
                if(index.get(word).contains(document)){
                    rank += rtool.tfidf(word, document, index);
                }
            }
        }

        return rank;
    }

    public Vector<Document> getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
