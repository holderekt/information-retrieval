import index.*;
import org.apache.pdfbox.text.PDFTextStripper;
import ranking.Pair;
import ranking.Query;
import ranking.Retriever;
import org.apache.pdfbox.pdmodel.PDDocument;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class  Main {

    public static void main(String[] args) throws NotValidDocumentException, IOException {

        FileUtils fu = new FileUtils();
        Vector<File> mario = fu.getFolderFilesRecursive("/home/navis/workspace", "pdf");

        for(File a : mario){
            System.out.println(a.getName());
        }

        Document a = new Document("Mario", "/home/navis/workspace/cosine-similarity/documents/Astrazione_Progettazione.pdf");
        DocumentProcesser processer = new DocumentProcesser();
        WordBag bag = processer.generateWordBag(a);
        System.out.println(bag);
        ReverseIndex index = new ReverseIndex();
        index.populateIndex(a);
        System.out.println(index);


        Retriever ir = new Retriever();
        //Document a = new Document("a", "...mario...ciao ....jaaja..exe.abc.pdf");
        //System.out.println(a.getFileType());
        ir.loadFolder("documents/");

        Query query = new Query("funzionale");
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
