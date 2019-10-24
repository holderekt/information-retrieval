package index;

import utils.DocumentLoader;
import utils.TextUtils;

import java.io.IOException;
import java.util.Scanner;

public class DocumentProcessor {
    private TextUtils filter;

    public DocumentProcessor() {
        filter = TextUtils.getInstance();
    }

    public WordBag generateWordBag(String text){

        WordBag wordbag = new WordBag();
        String[] buffer;
        Scanner scanner = new Scanner(text);

        while(scanner.hasNext()){
            buffer = filter.textFilter(scanner.next());
            for(String word : buffer){
                if(filter.wordCheck(word)){
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


    public WordBag generateWordBag(Document document) throws IOException {
        DocumentLoader loader = new DocumentLoader();
        String documentText = loader.loadDocument(document);
        return generateWordBag(documentText);
    }
}
