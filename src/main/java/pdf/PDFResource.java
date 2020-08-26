package pdf;

import freemarker.FreeMarkerKeyValue;
import net.sf.json.JSONObject;
import util.Util;

import java.io.File;

import static java.io.File.separator;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class PDFResource {
    private        File                               resourcesDir;
    private        File                               sourceHtmlDir;
    private        String                             sourceHtmlName;
    private        File                               resultHtmlDir;
    private        File                               resultPdfDir;
    private        File                               resultFtlDir;
    private        String                             ftlFileName;
    private        File                               ftlJsonDataFile;
    private        String                             pdfFontName;
    private        FreeMarkerKeyValue<String, String> ftlKeyVal = new FreeMarkerKeyValue<>();
    
    private PDFResource() {
        String resPath = System.getProperty("user.home") + separator + "Desktop" + separator + "resources" + separator;
        resourcesDir = new File(resPath);
        initResources();
    }
    
    public static PDFResource getInstance() {
        return Nested.INSTANCE;
    }
    
    private static final class Nested {
        private static final PDFResource INSTANCE = new PDFResource();
    }
    
    public void initResources() {
        initFileAndDirectory();
        pdfFontName = "msjhbd.ttf";
        JSONObject ftlJsonData = readFtlJsonData();
        ftlJsonData.forEach((k, v) -> ftlKeyVal.put(String.valueOf(k), String.valueOf(v)));
        sourceHtmlName = Util.getFirstFileNameInDirectory(sourceHtmlDir).orElse("source.html");
        ftlFileName = Util.getFileNameWithoutExtension(sourceHtmlName) + ".ftl";
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFileAndDirectory() {
        ftlJsonDataFile = new File(new File(resourcesDir, "data"), "data.json");
        sourceHtmlDir = new File(resourcesDir, "source_html");
        resultFtlDir = new File(resourcesDir, "ftl");
        resultHtmlDir = new File(resourcesDir, "html");
        resultPdfDir = new File(resourcesDir, "pdf");
        
        resourcesDir.mkdirs();
        sourceHtmlDir.mkdir();
        resultFtlDir.mkdir();
        resultHtmlDir.mkdir();
        resultPdfDir.mkdir();
        ftlJsonDataFile.getParentFile().mkdir();
    }
    
    public JSONObject readFtlJsonData() {
        return ftlJsonDataFile.exists()
               ? JSONObject.fromObject(Util.readeFile(ftlJsonDataFile))
               : new JSONObject();
    }
    
    public boolean cleanResources() {
        boolean resultPdfDir = Util.cleanDirectory(this.resultPdfDir);
        boolean resultHtmlDir = Util.cleanDirectory(this.resultHtmlDir);
        boolean ftlDir = Util.cleanDirectory(resultFtlDir);
        return resultPdfDir && resultHtmlDir && ftlDir;
    }
    
    public File getResourcesDir() {
        return resourcesDir;
    }
    
    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }
    
    public File getResultHtmlDir() {
        return resultHtmlDir;
    }
    
    public void setResultHtmlDir(File resultHtmlDir) {
        this.resultHtmlDir = resultHtmlDir;
    }
    
    public File getResultPdfDir() {
        return resultPdfDir;
    }
    
    public void setResultPdfDir(File resultPdfDir) {
        this.resultPdfDir = resultPdfDir;
    }
    
    public File getResultFtlDir() {
        return resultFtlDir;
    }
    
    public void setResultFtlDir(File ftlPath) {
        this.resultFtlDir = ftlPath;
    }
    
    public String getFtlFileName() {
        return ftlFileName;
    }
    
    public void setFtlFileName(String ftlFileName) {
        this.ftlFileName = ftlFileName;
    }
    
    public File getSourceHtmlDir() {
        return sourceHtmlDir;
    }
    
    public void setSourceHtmlDir(File sourceHtmlDir) {
        this.sourceHtmlDir = sourceHtmlDir;
    }
    
    public File getFtlJsonDataFile() {
        return ftlJsonDataFile;
    }
    
    public void setFtlJsonDataFile(File ftlJsonDataFile) {
        this.ftlJsonDataFile = ftlJsonDataFile;
    }
    
    public FreeMarkerKeyValue<String, String> getFtlKeyVal() {
        return ftlKeyVal;
    }
    
    public void setFtlKeyVal(FreeMarkerKeyValue<String, String> ftlKeyVal) {
        this.ftlKeyVal = ftlKeyVal;
    }
    
    public String getSourceHtmlName() {
        return sourceHtmlName;
    }
    
    public void setSourceHtmlName(String sourceHtmlName) {
        this.sourceHtmlName = sourceHtmlName;
    }
    
    public String getPdfFontName() {
        return pdfFontName;
    }
    
    public void setPdfFontName(String pdfFontName) {
        this.pdfFontName = pdfFontName;
    }
    
    public String getCssPath() {
        return "file:///" + Util.slashFilePath(sourceHtmlDir) + "/" + "css";
    }
    
    
    public String getImagePath() {
        return "file:///" + Util.slashFilePath(sourceHtmlDir) + "/" + "images";
    }
    
}
