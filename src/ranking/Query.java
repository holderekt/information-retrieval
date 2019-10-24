package ranking;

import index.DocumentProcessor;
import index.WordBag;
import java.util.Iterator;
import java.util.Set;

public class Query implements Iterable{
    private WordBag list;

    public Query(String query) {
        DocumentProcessor processor = new DocumentProcessor();
        list = processor.generateWordBag(query);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public Iterator iterator() {
        return list.getWords().iterator();
    }

    public Integer get(String word){
        return list.getNumber(word);
    }

    public Set<String> getWords(){return list.getWords();}
}
