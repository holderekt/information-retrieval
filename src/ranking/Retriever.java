package ranking;

import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Retriever {
    ReverseIndex index;
    HashMap<Document, Double> documents = new HashMap<>();

    public Retriever() throws IOException {
        index = new ReverseIndex();
    };

    public void addDocument(String title, String filename) throws NotValidDocumentException, IOException {
        Document doc = new Document(title, filename);
        index.populateIndex(doc);
    }


    public void addDocument(Document doc) throws NotValidDocumentException, IOException {
        index.populateIndex(doc);
    }

    public void loadFolder(String folder_path) throws IOException, NotValidDocumentException {
        RankingTool rtool = RankingTool.getInstance();
        HashMap<Document, Set<String>> map = new HashMap<>();
        // TODO ONLY TEST I GOTTA REMOVE THIS SHIT
        File folder = new File(folder_path);
        for(File file : folder.listFiles()){
            if(file.isFile()){
                Document a = new Document(file.getName(), file.getAbsolutePath());
                Set<String> mario = index.populateIndex(a);

               map.put(a, mario);
            }

            for(Document d : map.keySet()){
                Set<String> words = map.get(d);
                double val  = rtool.vectorLength(rtool.tfidf(words, d, index));
                documents.put(d, val);
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

        double queryLength = rtool.vectorLength(rtool.termFrequency(query.getWords(), query));
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


        queryVector = rtool.vectorNormalize(queryVector, queryLength);
        documentVector = rtool.vectorNormalize(documentVector, documents.get(document));

        score = rtool.vectorProduct(queryVector, documentVector);

        return score;
    }

    public Set<Document> getDocuments(){
        return documents.keySet();
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
