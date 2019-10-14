import index.Document;
import index.NotValidDocumentException;
import index.ReverseIndex;
import ranking.Query;
import ranking.RankingTool;

public class Main {

    public static void main(String[] args) throws NotValidDocumentException {
        Retriever ir = new Retriever();
        ir.addDocument("Emma Marrone", "document.txt");
        ir.addDocument("Hitler", "document2.txt");
        ir.addDocument("Berlino", "document3.txt");

        System.out.println(ir);
        Query query = new Query("un concerto a berlino");

        for(Document d : ir.getDocuments()){
            System.out.println(d.getName() + " - Score: " + ir.tfidfSimilarity(query, d));
        }

    }
}
