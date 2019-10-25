package document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class DocumentLoader {
    private PDFTextStripper stripper;

    public DocumentLoader() throws IOException {
        stripper = new PDFTextStripper();
    }

    public String loadDocumentText(String filepath) throws IOException {
        PDDocument pdfdoc = PDDocument.load(new File(filepath));
        String result = stripper.getText(pdfdoc);
        pdfdoc.close();
        return result;
    }
}
