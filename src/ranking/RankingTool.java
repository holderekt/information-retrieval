package ranking;
import index.Document;
import index.PostingList;
import index.ReverseIndex;

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

}
