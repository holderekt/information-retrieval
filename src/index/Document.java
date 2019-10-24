package index;

import java.io.Serializable;

public class Document implements Serializable {
    private String name;
    private String filename;
    private String[] content;

    public Document(String name, String filename){
        this.name = name;
        this.filename = filename;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public String[] getContent() {
        return content;
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
}
