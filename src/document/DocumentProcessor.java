package document;

import utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class DocumentProcessor {

    private TextProcessor processor = new TextProcessor();
    private DocumentLoader loader = new DocumentLoader();
    private FileUtils fileutil = new FileUtils();

    public DocumentProcessor() throws IOException {
    }

    public Document loadDocumentFromFile(File documentFile) throws DocumentException {
        if(documentFile.exists()){
            String text;
            try{
                text = loader.loadDocumentText(documentFile.getAbsolutePath());
            }catch(IOException e){
                throw new DocumentException("Error loading document file");
            }

            WordBag wordBag = processor.generateWordBag(text);
            return new Document(documentFile.getName(), documentFile.getAbsolutePath(), wordBag);
        }else{
            throw new DocumentException("Document file not found");
        }
    }

    public Document loadDocumentFromFile(String filepath) throws DocumentException {
        return loadDocumentFromFile(fileutil.getFile(filepath));
    }

    public Vector<Document> loadDocumentsFromFolder(String folderpath) throws DocumentException {
        Vector<File> PDFFiles = fileutil.getFolderFiles(folderpath, "pdf");
        Vector<Document> documents = new Vector<>();
        for(File documentfile : PDFFiles){
            documents.add(loadDocumentFromFile(documentfile));
        }
        return documents;
    }

    public Vector<Document> loadDocumentsFromFolderRecursive(String folderpath) throws DocumentException {
        Vector<File> PDFFiles = fileutil.getFolderFilesRecursive(folderpath, "pdf");
        Vector<Document> documents = new Vector<>();
        for(File documentfile : PDFFiles){
            documents.add(loadDocumentFromFile(documentfile));
        }
        return documents;
    }
}
