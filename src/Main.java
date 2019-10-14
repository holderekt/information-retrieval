import index.Document;
import index.NotValidDocumentException;
import ranking.Query;
import ranking.Retriever;


public class Main {

    public static void main(String[] args) throws NotValidDocumentException {
        Retriever ir = new Retriever();
        ir.addDocument("Emma Marrone", "document.txt");
        ir.addDocument("Hitler", "document2.txt");
        ir.addDocument("Berlino", "document3.txt");
        ir.addDocument("Videogiochi e Nazismo", "documento4.txt");

        System.out.println(ir);
        Query query = new Query("berlino");

        for(Document d : ir.getDocuments()){
            System.out.println(d.getName() + " - Score: " + ir.cosineSimilarity(query, d));
        }

    }
}
