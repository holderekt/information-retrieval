package document;

import java.util.HashMap;
import java.util.Set;

public class WordBag {
    private HashMap<String, Integer> wordbag;

    public WordBag(){
        wordbag = new HashMap<>();
    }

    public void addWord(String word, Integer number){
        wordbag.put(word, number);
    }

    public Set<String> getWords(){
        return wordbag.keySet();
    }


    public boolean contains(String word){
        if(wordbag.get(word) == null){
            return false;
        }
        return true;
    }

    public void addToNumber(String word, Integer number){
        if(contains(word)){
            addWord(word, getNumber(word) + number);
        }
    }

    public Integer getNumber(String word){
        return wordbag.get(word);
    }

    @Override
    public String toString() {
        return wordbag.toString();
    }
}
