package ranking;

import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import ranking.Query;
import ranking.RankingTool;

import javax.xml.bind.util.ValidationEventCollector;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class Retriever {
    Vector<Document> documents = new Vector<>();
    ReverseIndex index;

    public Retriever() throws IOException {
        index = new ReverseIndex();
    };

    public void addDocument(String title, String filename) throws NotValidDocumentException, IOException {
        Document doc = new Document(title, filename);
        index.populateIndex(doc);
        documents.add(doc);
    }


    public void addDocument(Document doc) throws NotValidDocumentException, IOException {
        index.populateIndex(doc);
        documents.add(doc);
    }

    public void loadFolder(String folder_path) throws IOException, NotValidDocumentException {
        // TODO ONLY TEST I GOTTA REMOVE THIS SHIT
        File folder = new File(folder_path);
        for(File file : folder.listFiles()){
            if(file.isFile()){
                Document a = new Document(file.getName(), file.getAbsolutePath());
                index.populateIndex(a);
                documents.add(a);
            }
        }
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

        //System.out.println(query.getWords());
        //System.out.println(rtool.tfidf(query.getWords(), query, index));

        double queryLength = rtool.vectorLength(rtool.termFrequency(query.getWords(), query));
        double documentLength = rtool.vectorLength(rtool.tfidf(document.getWords(), document, index));
        Vector<Double> queryVector = new Vector<>();
        Vector<Double> documentVector = new Vector<>();

        for(String word : query.getWords()){
            if(index.get(word) != null){
                if(index.get(word).contains(document)){
                    queryVector.add(rtool.termFrequency(word, query));
                    documentVector.add(rtool.tfidf(word, document, index));
                }
            }
        }

        //System.out.println("DL: " + documentLength + " QL:" + queryLength);
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
