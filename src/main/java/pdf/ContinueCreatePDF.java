package pdf;

import com.lowagie.text.pdf.BaseFont;
import file.FileEvent;
import file.FileListener;
import file.FileManager;
import formate.HTMLFormatter;
import freemarker.FreeMarkerKeyValue;
import freemarker.FreeMarkerTemplate;
import freemarker.template.TemplateException;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;
import util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static java.io.File.separator;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/18, MarkHuang,new
 * </ul>
 * @since 2018/10/18
 */
public class ContinueCreatePDF {
    private final File          ftlJsonDataFile;
    private final File          ftlFile;
    private final File          resultHtmlFile;
    private final PDFResource   pdfResource;
    
    public ContinueCreatePDF(PDFResource pdfResource) {
        this.pdfResource = pdfResource;
        ftlJsonDataFile = pdfResource.getFtlJsonDataFile();
        ftlFile = pdfResource.getFtlFile();
        resultHtmlFile = new File(pdfResource.getResultHtmlDir(), Util.getFileNameWithoutExtension(pdfResource.getFtlFile().getName()) + ".html");
    }
    
    @SuppressWarnings("unchecked")
    private final FileListener ftlResourceListener = new FileListener() {
        @Override
        public void onChange(FileEvent event) {
            File currentFile = event.getCurrentTarget();
            if (ftlJsonDataFile.getParentFile().getAbsolutePath().equalsIgnoreCase(currentFile.getAbsolutePath())) {
                JSONObject ftlJsonData = pdfResource.readFtlJsonData();
                FreeMarkerKeyValue<String, String> ftlKeyVal = pdfResource.getFtlKeyVal();
                ftlJsonData.forEach((k, v) -> ftlKeyVal.put(String.valueOf(k), String.valueOf(v)));
                pdfResource.setFtlKeyVal(ftlKeyVal);
            }
            createHTML();
        }
    };
    
    private final FileListener resultHtmlListener = new FileListener() {
        @Override
        public void onChange(FileEvent event) {
            createPdf();
        }
    };
    
    public void createPDFWhenResourceChange() {
        FileManager manager = FileManager.getInstance();
        manager.addListener(ftlFile, ftlResourceListener);
        manager.addListener(ftlJsonDataFile.getParentFile(), ftlResourceListener);
        manager.addListener(resultHtmlFile, resultHtmlListener);
        manager.addListener(new File(Util.rmFileProtocol(pdfResource.getCssPath())), ftlResourceListener);
        manager.addListener(new File(Util.rmFileProtocol(pdfResource.getImagePath())), ftlResourceListener);
    }
    
    public void createHtmlAndPdf() {
        createHTML();
        createPdf();
    }
    
    /**
     * ftl生成html
     */
    private void createHTML() {
        FreeMarkerTemplate freeMarkerTemplate = FreeMarkerTemplate.getInstance();
        String ftlDirPath = pdfResource.getResultFtlDir().getAbsolutePath();
        String ftlFileName = pdfResource.getFtlFile().getName();
        freeMarkerTemplate.getTemplate(ftlDirPath, ftlFileName).ifPresent(template -> {
            try {
                Writer stringWriter = new FileWriter(resultHtmlFile);
                FreeMarkerKeyValue<String, String> kv = pdfResource.getFtlKeyVal();
                kv.resetUnUseKey();
                template.process(kv, stringWriter);
                System.out.println("Ftl to html success!");
                System.out.println("Not used keys:" + kv.getUnUseKey().toString());
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
    }
    
    /**
     * 創建pdf
     */
    private void createPdf() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultHtmlFile.getAbsoluteFile()), StandardCharsets.UTF_8));
            File dest = new File(pdfResource.getResultPdfDir(), Util.getFileNameWithoutExtension(ftlFile.getName()) + ".pdf");
            Document document = XMLResource.load(br).getDocument();
            ITextRenderer iTextRenderer = new ITextRenderer();
            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            fontResolver.addFont(pdfResource.getResourcesDir().getAbsolutePath() + separator + "font" + separator + pdfResource.getPdfFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            iTextRenderer.setDocument(document, null);
            iTextRenderer.layout();
            iTextRenderer.createPDF(new FileOutputStream(dest));
            System.out.println("Html to pdf success!");
            System.out.println("==================================================================\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
