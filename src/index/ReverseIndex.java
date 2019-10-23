package index;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReverseIndex implements Index{
    private HashMap<String, PostingList> dictionary = new HashMap<>();
    private int documentNumber = 0;
    private DocumentProcesser processer = new DocumentProcesser();

    public ReverseIndex() throws IOException {
    }

    @Override
    public Set<String> populateIndex(Document document) throws IOException {

       WordBag wordbag = processer.generateWordBag(document);
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

        documentNumber += 1;
        return words;
    }


    public PostingList get(String word){
        return dictionary.get(word);
    }

    public int getDocumentNumber(){
        return  documentNumber;
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
