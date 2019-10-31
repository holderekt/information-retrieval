package index;

import java.util.Set;

public abstract class Resource {
    private String name;

    public Resource(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract Set<String> getContent();
    public abstract Integer getWordOccurrence(String word);
}
