import index.*;
import ranking.Pair;
import ranking.Query;
import ranking.Retriever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Vector;

public class  Main {

    public static void main(String[] args) throws NotValidDocumentException, IOException, ClassNotFoundException {

        Retriever ir = new Retriever("/home/navis/workspace/cosine-similarity");
        System.out.println("[Documents successfully indexed]");
        System.out.println("[Total documents: " + ir.getDocuments().size() + " ]");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        do{
            System.out.print("Inserisci query: ");
            String response = reader.readLine();
            if(!response.equals("esci")){
                System.out.println(" ");

                Query query = new Query(response);
                Vector<Pair> lista = new Vector<>();

                for(Document d : ir.getDocuments()){
                    lista.add(new Pair(d.getName(), (ir.cosineSimilarity(query, d))));
                }

                shit(lista);
            }else{
                exit = true;
            }

        }while(!exit);

        ir.saveData();
    }


    public static void shit(Vector<Pair> mario){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for(int i = 0; i!= 10; i = i +1){
            Pair max = mario.firstElement();
            for(Pair el : mario){
                if(el.second > max.second){
                    max = el;
                }
            }
            double val = max.second * 100.0;
            System.out.println((i+1) + ") "+max.first + " - " + df.format(val) + "%");
            mario.remove(max);
        }

    }
}
