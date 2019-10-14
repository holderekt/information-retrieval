package ranking;

import utils.TextUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Query implements Iterable{
    private HashMap<String, Integer> list = new HashMap<String, Integer>();


    public Query(String query){
        TextUtils filter = TextUtils.getInstance();
        String[] buffer;
        for(String word : query.split(" ")){
            buffer = filter.textFilter(word);
            for(String w : buffer){
                if(filter.wordCheck(w)){
                    if(list.get(w) == null){
                        list.put(w, 1);
                    }else{
                        list.put(w, list.get(w) + 1);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public Iterator iterator() {
        return list.keySet().iterator();
    }

    public Integer get(String word){
        return list.get(word);
    }

    public Set<String> getWords(){return list.keySet();}
}
