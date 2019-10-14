import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import ranking.RankingTool;

public class Main {

    public static void main(String[] args) throws NotValidDocumentException {
        Document docemmma = new Document("Emma Marrone", "document.txt");
        Document dochit = new Document("Hitler", "document2.txt");
        ReverseIndex rvind = new ReverseIndex();
        rvind.populateIndex(docemmma);
        rvind.populateIndex(dochit);
        System.out.println(rvind.toString());

        RankingTool ranker = RankingTool.getInstance();

        System.out.println(ranker.termFrequency("nella", docemmma, rvind));
        System.out.println(ranker.termFrequency("nella", dochit, rvind));
        System.out.println(ranker.documentFrequency("nella", rvind));
        System.out.println(rvind.getDocumentNumber());
        System.out.println(ranker.inverseDocumentFrequency(rvind.getDocumentNumber(), ranker.documentFrequency("song", rvind)));
        System.out.println(ranker.tfidf("song", docemmma, rvind));
        System.out.println(ranker.tfidf("song", dochit, rvind));
        System.out.println(ranker.tfidf("nella", docemmma, rvind));
        System.out.println(ranker.tfidf("nella", dochit, rvind));


    }
}
