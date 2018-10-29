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
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;
import util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.io.File.separator;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/18, MarkHuang,new
 * </ul>
 * @since 2018/10/18
 */
public class ContinueCreatePDF {
    private File ftlJsonDataFile;
    private File ftlFile;
    private File htmlFile;
    private List<File> cssFileList;
    private PDFResourceInfo pdfResourceInfo;
    private String nowUseCssPath;

    public ContinueCreatePDF(PDFResourceInfo pdfResourceInfo) {
        this.pdfResourceInfo = pdfResourceInfo;
        ftlJsonDataFile = new File(pdfResourceInfo.getFtlKeyValJsonPath());
        ftlFile = new File(pdfResourceInfo.getFtlDirPath() + pdfResourceInfo.getFtlFileName());
        htmlFile = new File(pdfResourceInfo.getResultHtmlPath() + Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()) + ".html");
        if (updateCssPath()) {
            cssFileList = getCssFileList(nowUseCssPath);
        }
    }

    @SuppressWarnings("unchecked")
    private FileListener ftlResourceListener = new FileListener() {
        @Override
        public void onChange(FileEvent event) {
            File currentFile = event.getCurrentTarget();
            FreeMarkerKeyValue keyVal = pdfResourceInfo.getKeyVal();
            if (ftlJsonDataFile.getAbsolutePath().equalsIgnoreCase(currentFile.getAbsolutePath())) {
                keyVal = new FreeMarkerKeyValue();
                Objects.requireNonNull(pdfResourceInfo.readJsonKeyValue()).forEach(keyVal::put);
                pdfResourceInfo.setKeyVal(keyVal);
                if (updateCssPath()) {
                    List<File> newCssFileList = getCssFileList(nowUseCssPath);
                    FileManager manager = FileManager.getInstance();
                    manager.removeListener(cssFileList);
                    manager.addListener(newCssFileList, ftlResourceListener);
                    cssFileList = newCssFileList;
                }
            } else {
                createHTML(keyVal);
            }
            createPdf();
        }
    };

    @SuppressWarnings("unchecked")
    public void createPDFWhenFTLResourceChange() {
        FileManager manager = FileManager.getInstance();
        manager.addListener(ftlFile, ftlResourceListener);
        manager.addListener(ftlJsonDataFile, ftlResourceListener);
        if (cssFileList != null && cssFileList.size() != 0) {
            manager.addListener(cssFileList, ftlResourceListener);
        }
        FreeMarkerKeyValue keyVal = pdfResourceInfo.getKeyVal();
        createHTML(keyVal);
        createPdf();
    }

    /**
     * ftl生成html
     */
    private void createHTML(final Map<String, String> ftlKeyVal) {
        FreeMarkerTemplate freeMarkerTemplate = FreeMarkerTemplate.getInstance();
        String ftlDirPath = pdfResourceInfo.getFtlDirPath();
        String ftlFileName = pdfResourceInfo.getFtlFileName();
        freeMarkerTemplate.getTemplate(ftlDirPath, ftlFileName).ifPresent(template -> {
            try {
                Writer stringWriter = new FileWriter(htmlFile);
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

    private boolean updateCssPath() {
        Object cssPath = pdfResourceInfo.getKeyVal().get("cssPath");
        cssPath = cssPath == null ? pdfResourceInfo.getHtmlSourcePath() + separator + "css" : cssPath;
        if (!cssPath.equals(nowUseCssPath)) {
            nowUseCssPath = Util.getFileFormFileURI(cssPath.toString()).getAbsolutePath();
            return true;
        }
        return false;
    }

    private List<File> getCssFileList(String cssPath) {
        File[] cssFiles = new File(cssPath).listFiles(File::isFile);
        if (cssFiles != null) {
            return Arrays.asList(cssFiles);
        }
        return new ArrayList<>();
    }
}
