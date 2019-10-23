package index;

import utils.DocumentLoader;
import utils.TextUtils;
import java.io.IOException;
import java.util.Scanner;

public class DocumentProcesser {
    private DocumentLoader loader;

    public DocumentProcesser() throws IOException {
        loader = new DocumentLoader();
    }

    public WordBag generateWordBag(Document document) throws IOException {
        String documentText = loader.loadDocument(document);

        Scanner scanner;
        scanner = new Scanner(documentText);
        scanner.useDelimiter(" ");

        WordBag wordbag = new WordBag();
        String[] buffer;

        while(scanner.hasNext()){
            buffer = textFilter(scanner.next());
            for(String word : buffer){
                if(wordCheck(word)){
                    if(wordbag.contains(word)){
                        wordbag.addToNumber(word, 1);
                    }else{
                        wordbag.addWord(word, 1);
                    }
                }
            }
        }

        return wordbag;
    }

    private String[] textFilter(String word){
        return word.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
    }

    private boolean wordCheck(String word){
        return word.length() > 1;
    }
}
