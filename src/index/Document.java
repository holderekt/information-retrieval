package index;

import java.util.Set;

public class Document {
    private String name;
    private String filename;
    private Set<String> content;

    public Document(String name, String filename){
        this.name = name;
        this.filename = filename;
    }

    public String getFileType(){
        String[] stringTokens = filename.split("\\.");
        return  stringTokens[stringTokens.length - 1];
    }

    public void setContent(Set<String> content) {
        this.content = content;
    }

    public String getName(){
        return this.name;
    }

    public String getFilename(){
        return this.filename;
    }

    @Override
    public String toString() {
        String result = this.name;
        return result;
    }

    public Set<String> getWords(){return content;}
}
