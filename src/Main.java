import index.Document;
import index.NotValidDocumentException;
import ranking.Query;
import ranking.Retriever;


public class Main {

    public static void main(String[] args) throws NotValidDocumentException {
        Retriever ir = new Retriever();
        ir.addDocument("DOC1", "document.txt");
        ir.addDocument("DOC2", "document2.txt");
        ir.addDocument("DOC3", "document3.txt");
        ir.addDocument("DOC4", "documento4.txt");

        System.out.println(ir);
        Query query = new Query("cinque otto");

        for(Document d : ir.getDocuments()){
            System.out.println(d.getName() + " - Score: "  + (int)(ir.cosineSimilarity(query, d)*100));
        }

    }
}
