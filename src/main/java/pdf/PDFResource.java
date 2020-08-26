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
    private static PDFResource                        pdfResource;
    private        File                               resourcesDir;
    private        File                               sourceHtmlDir;
    private        String                             sourceHtmlName;
    private        String                             cssPath;
    private        String                             imagePath;
    private        File                               resultHtmlDir;
    private        File                               resultPdfDir;
    private        File                               resultFtlDir;
    private        String                             ftlFileName;
    private        File                               ftlJsonDataFile;
    private        String                             pdfFontName;
    private        boolean                            isUseChrome;
    private        FreeMarkerKeyValue<String, String> ftlKeyVal = new FreeMarkerKeyValue<>();
    
    private PDFResource() {
        String resPath = System.getProperty("user.home") + separator + "Desktop" + separator + "resources" + separator;
        resourcesDir = new File(resPath);
        initResources();
    }
    
    public static PDFResource getInstance() {
        if (pdfResource == null) pdfResource = new PDFResource();
        return pdfResource;
    }
    
    public void initResources() {
        initFileAndDirectory();
        pdfFontName = "msjhbd.ttf";
        isUseChrome = false;
        JSONObject ftlJsonData = readFtlJsonData();
        setDefaultCssPath(ftlJsonData);
        setDefaultImagePath(ftlJsonData);
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
    
    void setDefaultCssPath(JSONObject jsonObj) {
        String cssPath = (String) jsonObj.get("cssPath");
        if (cssPath == null) {
            this.cssPath = "file:///" + Util.slashFilePath(sourceHtmlDir) + "/" + "css";
        }
        jsonObj.element("cssPath", this.cssPath);
    }
    
    void setDefaultImagePath(JSONObject jsonObj) {
        String imagePath = (String) jsonObj.get("imgPath");
        if (imagePath == null) {
            this.imagePath = "file:///" + Util.slashFilePath(sourceHtmlDir) + "/" + "images";
        }
        jsonObj.element("imagePath", this.imagePath);
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
    
    public void setUseChrome(boolean useChrome) {
        isUseChrome = useChrome;
    }
    
    public boolean isUseChrome() {
        return isUseChrome;
    }
    
    public String getCssPath() {
        return cssPath;
    }
    
    public PDFResource setCssPath(String cssPath) {
        this.cssPath = cssPath;
        return this;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public PDFResource setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
}
