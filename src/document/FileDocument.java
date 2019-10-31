package document;

import index.Resource;
import java.io.Serializable;

public class FileDocument extends Resource implements Serializable {
    private String filename;

    public FileDocument(String name, String filename, WordBag wordBag){
        super(name, wordBag);
        this.filename = filename;
    }

    public String getFilename(){
        return this.filename;
    }

    @Override
    public String toString() {
        return getName();
    }
}
