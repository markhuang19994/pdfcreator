package pdf;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import file.FileEvent;
import file.FileListener;
import file.FileManager;
import freemarker.FreeMarkerKeyValue;
import freemarker.FreeMarkerTemplate;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;
import util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import static java.io.File.separator;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/18, MarkHuang,new
 * </ul>
 * @since 2018/10/18
 */
public class ContinueCreatePDF {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContinueCreatePDF.class);

    private File ftlJsonDataFile;
    private File ftlFile;
    private File htmlFile;
    private File cssFile;
    private PDFResourceInfo pdfResourceInfo;

    public ContinueCreatePDF(PDFResourceInfo pdfResourceInfo) {
        this.pdfResourceInfo = pdfResourceInfo;
        ftlJsonDataFile = new File(pdfResourceInfo.getFtlKeyValJsonPath());
        ftlFile = new File(pdfResourceInfo.getFtlDirPath() + pdfResourceInfo.getFtlFileName());
        htmlFile = new File(pdfResourceInfo.getResultHtmlPath() + Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()) + ".html");
        File tempCssFile = new File(ftlFile.getParentFile().getAbsolutePath() + File.separator + Util.getFileNameWithoutExtension(ftlFile.getName()).toLowerCase() + "_style.ftl");
        if(tempCssFile.exists()) cssFile = tempCssFile;
    }

    @SuppressWarnings("unchecked")
    private FileListener fileListener = new FileListener() {
        @Override
        public void onChange(FileEvent event) {
            File currentFile = event.getCurrentTarget();
            FreeMarkerKeyValue keyVal = pdfResourceInfo.getKeyVal();
            if (ftlJsonDataFile.getAbsolutePath().equalsIgnoreCase(currentFile.getAbsolutePath())) {
                keyVal = new FreeMarkerKeyValue();
                Objects.requireNonNull(pdfResourceInfo.readJsonKeyValue()).forEach(keyVal::put);
                pdfResourceInfo.setKeyVal(keyVal);
            } else {
                createHTML(keyVal);
            }
            createPdf();
        }
    };

    @SuppressWarnings("unchecked")
    public void createPDFWhenFTLChange() {
        FileManager instance = FileManager.getInstance();
        instance.addListener(ftlFile, fileListener);
        instance.addListener(ftlJsonDataFile, fileListener);
        if (cssFile != null){
            instance.addListener(cssFile, fileListener);
        }
        FreeMarkerKeyValue keyVal = pdfResourceInfo.getKeyVal();
        createHTML(keyVal);
        createPdf();
    }

    /**
     * ftl生成html
     */
    private void createHTML(Map ftlKeyVal) {
        try {
            Template template = new FreeMarkerTemplate().getTemplate(pdfResourceInfo.getFtlDirPath(), pdfResourceInfo.getFtlFileName());
            Writer stringWriter = new FileWriter(htmlFile);
            template.process(ftlKeyVal, stringWriter);
            System.err.println("FTL to HTML 轉換完成!");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 創建pdf
     */
    private void createPdf() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(htmlFile), StandardCharsets.UTF_8))) {
            File dest = new File(pdfResourceInfo.getResultPdfPath() + Util.getFileNameWithoutExtension(ftlFile.getName()) + ".pdf");
            Document document = XMLResource.load(br).getDocument();
            ITextRenderer iTextRenderer = new ITextRenderer();
            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            fontResolver.addFont(pdfResourceInfo.getResourcesPath() + "font" + separator + "msjhbd.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            iTextRenderer.setDocument(document, null);
            iTextRenderer.layout();
            iTextRenderer.createPDF(new FileOutputStream(dest));
            System.err.println("PDF 產生完成!");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
