package document;

import java.io.Serializable;
import java.util.Set;

public class Document implements Serializable {
    private String name;
    private String filename;
    private WordBag wordBag;

    public Document(String name, String filename, WordBag wordBag){
        this.name = name;
        this.filename = filename;
        this.wordBag = wordBag;
    }

    public Set<String> getContent() {
        return wordBag.getWords();
    }

    public Integer getWordOccurence(String word){
        return wordBag.getNumber(word);
    }

    public String getName(){
        return this.name;
    }

    public String getFilename(){
        return this.filename;
    }

    @Override
    public String toString() {
        return name;
    }
}
