import index.Document;
import index.NotValidDocumentException;
import ranking.Query;
import ranking.Retriever;


public class Main {

    public static void main(String[] args) throws NotValidDocumentException {
        Retriever ir = new Retriever();
        ir.addDocument("Emma", "document.txt");
        ir.addDocument("Roma", "document3.txt");
        ir.addDocument("Jackson", "document2.txt");

        System.out.println(ir);
        Query query = new Query("concerto di micheal jackson a roma con emma marrone");

        for(Document d : ir.getDocuments()){
            System.out.println(d.getName() + " - Score: "  + (ir.cosineSimilarity(query, d)));
        }

    }
}
