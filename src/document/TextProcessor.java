package document;
import java.util.Scanner;

public class TextProcessor {

    public TextProcessor() {

    }

    public WordBag generateWordBag(String text){

        WordBag wordbag = new WordBag();
        String[] buffer;
        Scanner scanner = new Scanner(text);

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
