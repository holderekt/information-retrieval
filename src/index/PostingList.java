package index;

import java.util.HashMap;

class PostingList {
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
}
