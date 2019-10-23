package index;
import utils.DocumentLoader;
import utils.TextUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ReverseIndex implements Index{
    private HashMap<String, PostingList> dictionary = new HashMap<>();
    private int documentNumber;
    private DocumentLoader loader;
    DocumentProcesser processer = new DocumentProcesser();

    public ReverseIndex() throws IOException {
        loader = new DocumentLoader();
        documentNumber = 0;
    }


    @Override
    public void populateIndex(Document document) throws IOException {

       WordBag wordbag = processer.generateWordBag(document);
       document.setContent(wordbag.getWords());
       Set<String> words = wordbag.getWords();
       PostingList list;

        for(String word : words){
            if(dictionary.get(word) == null){
                list = new PostingList();
            }else{
                list = dictionary.get(word);
            }

            list.add(document, wordbag.getNumber(word));
            dictionary.put(word, list);
        }

        documentNumber = documentNumber + 1;
    }

    private HashMap<String, Integer> generateWordbag(Document document) throws IOException {

        String text = loader.loadDocument(document);
        TextUtils filter = TextUtils.getInstance();
        Scanner scanner;
        scanner = new Scanner(text);

        scanner.useDelimiter(" ");

        HashMap<String, Integer> content = new HashMap<String, Integer>();
        String[] buffer;

        while(scanner.hasNext()){
            buffer = filter.textFilter(scanner.next());
            for(String word : buffer){
                if(filter.wordCheck(word)){
                    if(content.get(word) == null){
                        content.put(word, 1);
                    }else{
                        content.put(word, content.get(word) + 1);
                    }
                }
            }
        }
        /*
            TODO
            SIDE EFFECT Non esplicito / Da risolvere
         */
        document.setContent(content.keySet());
        return content;
    }

    public PostingList get(String word){
        return dictionary.get(word);
    }

    public int getDocumentNumber(){
        return documentNumber;
    }

    @Override
    public String toString() {
        String result = "[\n";
        for(String str : dictionary.keySet()){
            result += str + " : " + dictionary.get(str).toString() + "\n";
        }

        result += "]";
        return result;
    }
}
