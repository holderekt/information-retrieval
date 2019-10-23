package ranking;

import index.Document;
import index.ReverseIndex;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Retriever {
    ReverseIndex index;
    HashMap<Document, Double> documents;
    boolean vectorLengthCalculated = false;

    public Retriever() throws IOException {
        index = new ReverseIndex();
        documents = new HashMap<>();
    };

    public void loadFolder(String folderpath) throws IOException {
        FileUtils flutil = new FileUtils();
        Vector<File> PDFFiles = flutil.getFolderFilesRecursive(folderpath, "pdf");

        for(File file : PDFFiles){
            Document document = new Document(file.getName(), file.getAbsolutePath());
            Set<String> documentWordSet = index.populateIndex(document);
            document.setContent(documentWordSet);
            documents.put(document, 0.0);
        }

        vectorLengthCalculated = false;
    }

    private void generateLength(){
        RankingTool ranker = RankingTool.getInstance();
        for(Document document : documents.keySet()){
            documents.put(document, ranker.vectorLength(ranker.tfidf(document.getContent(),document,index)));
        }

        vectorLengthCalculated = true;
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

        if(!vectorLengthCalculated){
            generateLength();
        }


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
