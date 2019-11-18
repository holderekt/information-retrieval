package document;

import utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class DocumentProcessor {

    private TextProcessor processor = new TextProcessor();
    private DocumentLoader loader = new DocumentLoader();
    private FileUtils fileutil = new FileUtils();
    private boolean logMessages;

    public DocumentProcessor(boolean logActive){
        logMessages = logActive;
    }

    public FileDocument loadFile(String filepath) throws DocumentException {
        return loadFile(new File(filepath));
    }

    private FileDocument loadFile(File documentFile) throws DocumentException {
        if(fileutil.isFile(documentFile)){
            try {
                String text = loader.loadDocumentText(documentFile);
                WordBag wordBag = processor.generateWordBag(text);
                return new FileDocument(documentFile.getName(), documentFile.getAbsolutePath(), wordBag);
            } catch (IOException e) {
                throw new DocumentException("Could not load file");
            }
        }else{
            throw new DocumentException("File is not valid");
        }
    }

    public Collection<FileDocument> loadFolder(String folderpath) {
        Collection<File> files = fileutil.getFolderFiles(folderpath, "pdf");
        return loadDocuments(files);
    }

    public Collection<FileDocument> loadFolderRecursive(String folderpath) {
        Collection<File> files = fileutil.getFolderFilesRecursive(folderpath, "pdf");
        return loadDocuments(files);
    }

    // TODO Rimuovere merda
    private Collection<FileDocument> loadDocuments(Collection<File> files){
        System.out.println("Loading Files:");
        Vector<FileDocument> fileDocuments = new Vector<>();
        int n = files.size();
        int i=1;
        for(File documentfile : files){
            FileDocument loadedFile = null;
            try {
                loadedFile = loadFile(documentfile);
                fileDocuments.add(loadedFile);

            } catch (DocumentException e) {
                if(logMessages)
                    System.out.println("[ WARNING] Could not load file: " + documentfile.getAbsolutePath());
            }
            printPercentage(i,n);
            i++;
        }
        if(logMessages)
            System.out.println("\n[    INFO]: Loaded " + fileDocuments.size() + " files of " + files.size());
        return fileDocuments;
    }

    // TODO Rimuovere questa merda
    public static void printPercentage(int percentage, int n){

        int value = (percentage * 25) / n;
        System.out.print("\r[");
        for(int i=0; i<=value; i++){
            System.out.print("#");
        }
        for(int i=value; i<=24; i++){
            System.out.print(" ");
        }

        System.out.print("] "+ ((percentage * 100) / n) + "%");
    }
}
