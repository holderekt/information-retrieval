package index;

import java.io.IOException;
import java.util.Set;

public interface Index {
  Set<String> populateIndex(Document document) throws NotValidDocumentException, IOException;
}