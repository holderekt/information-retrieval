package ranking;

import utils.TextUtils;
import java.util.HashMap;

public class Query {
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
}
