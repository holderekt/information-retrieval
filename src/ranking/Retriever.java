package ranking;

import document.*;
import index.Resource;
import index.ReverseIndex;
import java.io.*;
import java.util.*;

public class Retriever implements Serializable{
    private ReverseIndex<Resource> index;
    private HashMap<Resource, Double> documents;
    private boolean vectorLengthCalculated = false;
    private transient String indexFolderPath = "";
    private transient DocumentProcessor processor = new DocumentProcessor();

    public Retriever(String folderpath) throws IOException, ClassNotFoundException {

        /*
        FileUtils flutil = new FileUtils();

        if(flutil.getFolderFiles(folderpath, "index").size() >= 1){
            System.out.println("[Index file found. Loading file]");
            Retriever ret = loadData(folderpath);
            this.index = ret.index;
            this.documents = ret.documents;
            this.vectorLengthCalculated = ret.vectorLengthCalculated;
            this.indexFolderPath = ret.indexFolderPath;
        }else{
            index = new ReverseIndex();
            documents = new HashMap<>();
        }
         */
    }

    public Retriever() throws IOException {
        index = new ReverseIndex();
        documents = new HashMap<>();
    }

    public void loadFolder(String folderpath) throws DocumentException {
        Vector<Document> documents = processor.loadDocumentsFromFolderRecursive(folderpath);

        for(Document document : documents){
            index.populateIndex(document);
            this.documents.put(document, 0.0);
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

    public double cosineSimilarity(Query query, Resource document){

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

    public void saveData() throws IOException {
        FileOutputStream stream = new FileOutputStream(indexFolderPath + "/docindex.index");
        ObjectOutputStream writer = new ObjectOutputStream(stream);
        writer.writeObject(this);
        writer.close();
        stream.close();
    }

    public static Retriever loadData(String folderpath) throws IOException, ClassNotFoundException {
        FileInputStream stream = new FileInputStream(folderpath + "/docindex.index");
        ObjectInputStream reader = new ObjectInputStream(stream);
        Retriever ret =  (Retriever) reader.readObject();
        reader.close();
        stream.close();
        return ret;
    }

    public Set<Resource> getDocuments(){
        return documents.keySet();
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
