import index.Document;
import index.Index;
import index.NotValidDocumentException;
import index.ReverseIndex;

public class Main {

    public static void main(String[] args) throws NotValidDocumentException {
        Document docemmma = new Document("Emma Marrone", "document.txt");
        Document dochit = new Document("Hitler", "document2.txt");
        Index rvind = new ReverseIndex();
        rvind.populateIndex(docemmma);
        rvind.populateIndex(dochit);
        System.out.println(rvind.toString());
    }
}
