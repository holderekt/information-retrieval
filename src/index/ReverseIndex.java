package index;

import document.Document;
import java.io.Serializable;
import java.util.HashMap;

public class ReverseIndex implements Serializable{
    private HashMap<String, PostingList> dictionary = new HashMap<>();
    private int documentNumber = 0;

    public ReverseIndex() {
    }

    public void populateIndex(Document document) {
        PostingList list;
        for(String word : document.getContent()){
            if(dictionary.get(word) == null){
                list = new PostingList();
            }else{
                list = dictionary.get(word);
            }
            list.add(document, document.getWordOccurence(word));
            dictionary.put(word, list);
        }
        documentNumber += 1;
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
