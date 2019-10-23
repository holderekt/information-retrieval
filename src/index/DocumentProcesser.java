package index;

import utils.DocumentLoader;
import utils.TextUtils;

import java.io.IOException;
import java.util.Scanner;

public class DocumentProcesser {
    private DocumentLoader loader;
    private TextUtils filter;

    public DocumentProcesser() throws IOException {
        loader = new DocumentLoader();
        filter = TextUtils.getInstance();
    }


    public WordBag generateWordBag(Document document) throws IOException {
        String documentText = loader.loadDocument(document);

        Scanner scanner;
        scanner = new Scanner(documentText);
        scanner.useDelimiter(" ");

        WordBag wordbag = new WordBag();
        String[] buffer;

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
}
