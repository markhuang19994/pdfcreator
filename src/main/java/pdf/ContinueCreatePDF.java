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
import java.util.Map;

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
    private final HTMLFormatter htmlFormatter;
    
    public ContinueCreatePDF(PDFResource pdfResource) {
        this.pdfResource = pdfResource;
        ftlJsonDataFile = pdfResource.getFtlJsonDataFile();
        ftlFile = new File(pdfResource.getResultFtlDir(), pdfResource.getFtlFileName());
        resultHtmlFile = new File(pdfResource.getResultHtmlDir(), Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".html");
        htmlFormatter = HTMLFormatter.getInstance(pdfResource);
    }
    
    @SuppressWarnings("unchecked")
    private final FileListener ftlResourceListener = new FileListener() {
        @Override
        public void onChange(FileEvent event) {
            File currentFile = event.getCurrentTarget();
            if (ftlJsonDataFile.getAbsolutePath().equalsIgnoreCase(currentFile.getAbsolutePath())) {
                JSONObject ftlJsonData = pdfResource.readFtlJsonData();
                FreeMarkerKeyValue<String, String> ftlKeyVal = pdfResource.getFtlKeyVal();
                ftlJsonData.forEach((k, v) -> ftlKeyVal.put(String.valueOf(k), String.valueOf(v)));
                pdfResource.setFtlKeyVal(ftlKeyVal);
                createHTML(ftlKeyVal);
            } else {
                createHTML(pdfResource.getFtlKeyVal());
            }
        }
    };
    
    private final FileListener sourceHtmlResourceListener = new FileListener() {
        @Override
        public void onChange(FileEvent event) {
            htmlFormatter.htmlToFtlFormat();
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
        manager.addListener(new File(pdfResource.getSourceHtmlDir(), pdfResource.getSourceHtmlName()), sourceHtmlResourceListener);
        manager.addListener(ftlFile, ftlResourceListener);
        manager.addListener(ftlJsonDataFile, ftlResourceListener);
        manager.addListener(resultHtmlFile, resultHtmlListener);
        manager.addListener(new File(Util.rmFileProtocol(pdfResource.getCssPath())), ftlResourceListener);
        manager.addListener(new File(Util.rmFileProtocol(pdfResource.getImagePath())), ftlResourceListener);
    }
    
    public void createHtmlAndPdf() {
        FreeMarkerKeyValue<String, String> keyVal = pdfResource.getFtlKeyVal();
        createHTML(keyVal);
        createPdf();
    }
    
    /**
     * ftl生成html
     */
    private void createHTML(final Map<String, String> ftlKeyVal) {
        FreeMarkerTemplate freeMarkerTemplate = FreeMarkerTemplate.getInstance();
        String ftlDirPath = pdfResource.getResultFtlDir().getAbsolutePath();
        String ftlFileName = pdfResource.getFtlFileName();
        freeMarkerTemplate.getTemplate(ftlDirPath, ftlFileName).ifPresent(template -> {
            try {
                Writer stringWriter = new FileWriter(resultHtmlFile);
                template.process(ftlKeyVal, stringWriter);
                System.err.println("FTL to HTML 轉換完成!");
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
            System.err.println("PDF 產生完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
