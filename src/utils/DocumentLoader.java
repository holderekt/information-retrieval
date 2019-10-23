package utils;
import index.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class DocumentLoader {
    private PDFTextStripper stripper;

    public DocumentLoader() throws IOException {
        stripper = new PDFTextStripper();
    }

    public String loadDocument(Document document) throws IOException {
        PDDocument pdfdoc = PDDocument.load(new File(document.getFilename()));
        String result = stripper.getText(pdfdoc);
        pdfdoc.close();
        return result;
    }
}
