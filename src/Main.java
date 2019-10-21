import index.Document;
import index.NotValidDocumentException;
import org.apache.pdfbox.text.PDFTextStripper;
import ranking.Pair;
import ranking.Query;
import ranking.Retriever;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

public class  Main {

    public static void main(String[] args) throws NotValidDocumentException, IOException {
        Retriever ir = new Retriever();
        ir.loadFolder("documents/");

        Query query = new Query("ingegneria e intelligenza artificiale");
        Vector<Pair> lista = new Vector<>();

        for(Document d : ir.getDocuments()){
            lista.add(new Pair(d.getName(), (ir.cosineSimilarity(query, d))));
        }

        shit(lista);
    }


    public static void shit(Vector<Pair> mario){
        for(int i = 0; i!= 10; i = i +1){
            Pair max = mario.firstElement();
            for(Pair el : mario){
                if(el.second > max.second){
                    max = el;
                }
            }
            System.out.println((i+1) + ") "+max.first + "  - " + max.second);
            mario.remove(max);
        }

    }
}
