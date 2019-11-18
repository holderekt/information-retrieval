package document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import utils.FileUtils;
import java.io.File;
import java.io.IOException;

class DocumentLoader {
    private FileUtils fileutil = new FileUtils();

    String loadDocumentText(File file) throws IOException {
        if(fileutil.getFileType(file.getName()).equals("pdf")) {
            return loadPDFDocument(file);
        }

        return null;
    }
    private String loadPDFDocument(File file) throws IOException{
        PDFTextStripper stripper = new PDFTextStripper();
        PDDocument pdfdoc = PDDocument.load(file);
        String result = stripper.getText(pdfdoc);
        pdfdoc.close();
        return result;
    }
}
