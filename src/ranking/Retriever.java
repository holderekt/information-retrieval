package ranking;

import document.*;
import index.Resource;
import index.ReverseIndex;
import java.io.*;
import java.util.*;

public class Retriever implements Serializable{
    private ReverseIndex<Resource> index = new ReverseIndex<>();
    private HashMap<Resource, Double> documents = new HashMap<>();
    private boolean vectorLengthCalculated = false;
    private transient DocumentProcessor processor = new DocumentProcessor(true);

    public void loadFile(String filepath) throws DocumentException{
        FileDocument document = processor.loadFile(filepath);
        index.populateIndex(document);
        this.documents.put(document, 0.0);
        vectorLengthCalculated = false;
    }

    public void loadFolder(String folderpath) {
        Collection<FileDocument> fileDocuments = processor.loadFolderRecursive(folderpath);
        for(FileDocument fileDocument : fileDocuments){
            index.populateIndex(fileDocument);
            this.documents.put(fileDocument, 0.0);
        }
        vectorLengthCalculated = false;
    }

    private void generateLength(){
        RankingTool ranker = RankingTool.getInstance();
        for(Resource document : documents.keySet()){
            documents.put(document, ranker.vectorLength(ranker.tfidf(document.getContent(),document,index)));
        }
        vectorLengthCalculated = true;
    }


    public double cosineSimilarity(Query query, Resource document){
        if(!vectorLengthCalculated)
                generateLength();

        RankingTool rtool = RankingTool.getInstance();
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

        queryVector = rtool.vectorNormalize(queryVector);
        documentVector = rtool.vectorNormalize(documentVector, documents.get(document));

        return rtool.vectorProduct(queryVector, documentVector);
    }


    public Set<Resource> getDocuments(){
        return documents.keySet();
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
