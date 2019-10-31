package ranking;
import index.PostingList;
import index.Resource;
import index.ReverseIndex;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class RankingTool {
    private static RankingTool ranker = new RankingTool();
    private RankingTool(){}
    public static final RankingTool getInstance(){return ranker;}

    public double termFrequency(String word, Resource document, ReverseIndex index){
        PostingList<Resource> list = index.get(word);

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

    public Vector<Double> termFrequency(Set<String> words, Query query){
        Vector<Double> result = new Vector<>();
        for(String word : words){
            if(query.getWords().contains(word)){
                result.add(Math.log(1 + query.get(word)));
            }
        }

        return result;
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

    public double tfidf(String word, Resource document, ReverseIndex index){
        double tf = termFrequency(word, document, index);
        double df = documentFrequency(word, index);
        double idf = inverseDocumentFrequency(index.getDocumentNumber(), df);

        return tf * idf;
    }

    public Vector<Double> tfidf(Set<String> word, Resource document, ReverseIndex index){
        Vector<Double> tfidfvector = new Vector<Double>();
        for(String s : word){
            tfidfvector.add(tfidf(s, document, index));
        }

        return tfidfvector;
    }

    public double tfidf(String word, Query query, ReverseIndex index){
        double tf = termFrequency(word, query);
        double df = documentFrequency(word, index);
        double idf = inverseDocumentFrequency(index.getDocumentNumber(), df);

        return tf * idf;
    }

    public Vector<Double> tfidf(Set<String> word, Query query, ReverseIndex index){
        Vector<Double> tfidfvector = new Vector<Double>();
        for(String s : word){
            //System.out.println("[" + s +  ":" + tfidf(s, query, index));
            //System.out.println("TF:" + termFrequency(s, query));
            //System.out.println("N: "+index.getDocumentNumber() + " DF:" + documentFrequency(s, index));
            //System.out.println("IDF:" + inverseDocumentFrequency(index.getDocumentNumber(), documentFrequency(s, index)));
            tfidfvector.add(tfidf(s, query, index));
        }

        return tfidfvector;
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

    public Vector<Double> vectorNormalize(Vector<Double> vector, double length){
        Vector<Double> normalVector = new Vector<>();
        for(Double value : vector){
            normalVector.add(value / length);
        }

        return normalVector;
    }

    public double vectorProduct(Vector<Double> v1, Vector<Double> v2){
        double product = 0.0;

        for(Iterator<Double> itv1 = v1.iterator(), itv2 = v2.iterator(); itv1.hasNext() && itv2.hasNext();){
            product += itv1.next().doubleValue() * itv2.next().doubleValue();
        }

        return product;
    }

}
