package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class ScaleDown {

    private static final float A4 = PageSize.A4.getWidth();
    private static final String SRC = "C:\\Users\\1710002NB01\\Downloads\\8_invt.pdf";
    private static final String DEST = "C:\\Users\\1710002NB01\\Downloads\\8_invt_new.pdf";

    public class ScaleEvent extends PdfPageEventHelper {

        float scale = 1;
        PdfDictionary pageDict;

        public ScaleEvent(float scale) {
            this.scale = scale;
        }

        public void setPageDict(PdfDictionary pageDict) {
            this.pageDict = pageDict;
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            writer.addPageDictEntry(PdfName.ROTATE, pageDict.getAsNumber(PdfName.ROTATE));
            writer.addPageDictEntry(PdfName.MEDIABOX, scaleDown(pageDict.getAsArray(PdfName.MEDIABOX), scale));
            writer.addPageDictEntry(PdfName.CROPBOX, scaleDown(pageDict.getAsArray(PdfName.CROPBOX), scale));
        }
    }

    public static void main(String[] args) throws IOException, DocumentException, NoSuchFieldException, IllegalAccessException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ScaleDown().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException, IllegalAccessException, NoSuchFieldException {
        PdfReader reader = new PdfReader(src);
        Field f = reader.getClass().getDeclaredField("encrypted");
        f.setAccessible(true);
        f.set(reader, false);
        int n = reader.getNumberOfPages();
        if (n < 1) return;

        float scale = calcScaleCoefficient(reader.getPageSize(1));
        if (scale == 0) return;
        ScaleEvent event = new ScaleEvent(scale);
        event.setPageDict(reader.getPageN(1));
        System.out.println("縮放係數: " + scale);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        writer.setPageEvent(event);
        document.open();
        Image page;
        for (int p = 1; p <= n; p++) {
            page = Image.getInstance(writer.getImportedPage(reader, p));
            page.setAbsolutePosition(0, 0);
            page.scalePercent(scale * 100);
            document.add(page);
            if (p < n) {
                event.setPageDict(reader.getPageN(p + 1));
            }
            document.newPage();
        }
        document.close();
    }

    private float calcScaleCoefficient(Rectangle pageSize) {
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
//        return width < A4 ? 0 : A4 / width;
        return height < PageSize.A4.getHeight() ? 0 : PageSize.A4.getHeight() / height;
    }

    public PdfArray scaleDown(PdfArray original, float scale) {
        if (original == null)
            return null;
        float width = original.getAsNumber(2).floatValue()
                - original.getAsNumber(0).floatValue();
        float height = original.getAsNumber(3).floatValue()
                - original.getAsNumber(1).floatValue();
        return new PdfRectangle(width * scale, height * scale);
    }
}