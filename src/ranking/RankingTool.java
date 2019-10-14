package ranking;
import index.Document;
import index.PostingList;
import index.ReverseIndex;

import java.util.Vector;

public class RankingTool {
    private static RankingTool ranker = new RankingTool();
    private RankingTool(){}
    public static final RankingTool getInstance(){return ranker;}

    public double termFrequency(String word, Document document, ReverseIndex index){
        PostingList list = index.get(word);

        if(list != null){
            if(list.contains(document)){
                return Math.log(1 + list.get(document).doubleValue());
            }
        }

        return 0.0;
    }

    public double termFrequency(String word, Query query){
        if(query.getWords().contains(word)){
            return Math.log(1 + query.get(word));
        }

        return 0.0;
    }

    public double documentFrequency(String word, ReverseIndex index){
        PostingList list = index.get(word);

        if(list != null){
            return (double) list.size();
        }

        return 0.0;
    }

    public double inverseDocumentFrequency(int documentNumber, double documentFrequency){
        return Math.log(documentNumber / documentFrequency);
    }

    public double tfidf(String word, Document document, ReverseIndex index){
        double tf = termFrequency(word, document, index);
        double df = documentFrequency(word, index);
        double idf = inverseDocumentFrequency(index.getDocumentNumber(), df);

        return tf * idf;
    }

    public double tfidf(String word, Query query, ReverseIndex index){
        double tf = termFrequency(word, query);
        double df = documentFrequency(word, index);
        double idf = inverseDocumentFrequency(index.getDocumentNumber(), df);

        return tf * idf;
    }

    public double vectorLength(Vector<Double> vector){
        double length = 0.0;
        for(Double value : vector){
            length += Math.pow(value, 2);
        }
        length = Math.sqrt(length);
        return length;
    }

    public Vector<Double> vectorNormalize(Vector<Double> vector){
        Vector<Double> normalVector = new Vector<>();
        double length = vectorLength(vector);
        for(Double value : vector){
            normalVector.add(value / length);
        }
        
        return normalVector;
    }

}
