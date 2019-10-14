package ranking;

import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import ranking.Query;
import ranking.RankingTool;

import java.util.Collection;
import java.util.Iterator;
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
        double score = 0.0;
        for(String word : query.getWords()){
            if(index.get(word) != null){
                if(index.get(word).contains(document)){
                    score += rtool.tfidf(word, document, index);
                }
            }
        }

        return score;
    }

    public double cosineSimilarity(Query query, Document document){
        RankingTool rtool = RankingTool.getInstance();
        double score = 0.0;
        double queryLength = rtool.vectorLength(rtool.tfidf(query.getWords(), query, index));
        double documentLength = rtool.vectorLength(rtool.tfidf(document.getWords(), document, index));
        Vector<Double> queryVector = new Vector<>();
        Vector<Double> documentVector = new Vector<>();

        for(String word : query.getWords()){
            if(index.get(word) != null){
                if(index.get(word).contains(document)){
                    queryVector.add(rtool.tfidf(word, query, index));
                    documentVector.add(rtool.tfidf(word, document, index));
                }
            }
        }

        //System.out.println(queryVector);
        //System.out.println(documentVector);


        queryVector = rtool.vectorNormalize(queryVector, queryLength);
        documentVector = rtool.vectorNormalize(documentVector, documentLength);

        //System.out.println(queryVector);
        //System.out.println(documentVector);

        score = rtool.vectorProduct(queryVector, documentVector);

        return score;
    }

    public Vector<Document> getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
