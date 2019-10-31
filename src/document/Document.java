package document;

import index.Resource;

import java.io.Serializable;
import java.util.Set;

public class Document extends Resource implements Serializable {
    private String filename;
    private WordBag wordBag;

    public Document(String name, String filename, WordBag wordBag){
        super(name);
        this.filename = filename;
        this.wordBag = wordBag;
    }

    @Override
    public Set<String> getContent() {
        return wordBag.getWords();
    }

    @Override
    public Integer getWordOccurrence(String word){
        return wordBag.getNumber(word);
    }

    public String getFilename(){
        return this.filename;
    }

    @Override
    public String toString() {
        return getName();
    }
}
