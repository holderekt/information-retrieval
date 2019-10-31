package index;

import document.WordBag;

import java.util.Set;

public abstract class Resource {
    private String name;
    private WordBag wordbag;

    public Resource(String name, WordBag wordBag){
        this.name = name;
        this.wordbag = wordBag;
    }

    public String getName(){
        return this.name;
    }

    public Set<String> getContent(){
        return wordbag.getWords();
    }

    public Integer getWordOccurrence(String word){
        return wordbag.getNumber(word);
    }
}
