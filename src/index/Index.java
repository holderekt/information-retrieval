package index;

import java.io.IOException;

public interface Index {
    void populateIndex(Document document) throws NotValidDocumentException, IOException;
}