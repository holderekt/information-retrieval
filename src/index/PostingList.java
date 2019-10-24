package index;

import java.io.Serializable;
import java.util.HashMap;

public class PostingList implements Serializable {

    private HashMap<Document, Integer> list;

    PostingList(){
        this.list = new HashMap<>();
    }

    public void add(Document document, Integer number){
        if(list.get(document) == null ){
            list.put(document, number);
        }else{
            list.put(document, list.get(document) + number);
        }
    }

    @Override
    public String toString() {
        String result = "[";
        for(Document doc : list.keySet()){
            result += doc.toString() + ":" + list.get(doc).toString() + " ";
        }
        result += "]";
        return  result;
    }

    public Integer get(Document document){
        return list.get(document);
    }

    public int size(){
        return list.keySet().size();
    }

    public boolean contains(Document document){
        if(list.get(document) == null){
            return false;
        }
        return true;
    }
}
