package index;

public interface Index {
    void populateIndex(Document document) throws NotValidDocumentException;
}