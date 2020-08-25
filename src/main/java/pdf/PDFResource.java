package pdf;

import freemarker.FreeMarkerKeyValue;
import net.sf.json.JSONObject;
import util.Util;

import java.io.File;
import java.net.URISyntaxException;

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
    private        String                             resourcesPath;
    private        String htmlSourcePath;
    private        String resultHtmlDir;
    private        String resultPdfDir;
    private        String htmlSourceFileName;
    private        String resultFtlDir;
    private        String ftlFileName;
    private        String ftlJsonDataPath;
    private        String pdfFontName;
    private        boolean isUseChrome;
    private        FreeMarkerKeyValue<String, String> ftlKeyVal = new FreeMarkerKeyValue<>();
    
    private PDFResource() {
        String resPath = null;
        try {
            resPath = new File(ContinueCreatePDF.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + separator + "resources" + separator;
            if (!new File(resPath).exists()) {
                resPath = System.getProperty("user.home") + separator + "Desktop" + separator + "resources" + separator;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        resourcesPath = resPath;
        initResources();
    }
    
    public static PDFResource getInstance() {
        if (pdfResource == null) pdfResource = new PDFResource();
        return pdfResource;
    }
    
    public void initResources() {
        resultHtmlDir = resourcesPath + "result" + separator + "html" + separator;
        resultPdfDir = resourcesPath + "result" + separator + "pdf" + separator;
        resultFtlDir = resourcesPath + "result" + separator + "ftl" + separator;
        htmlSourcePath = resourcesPath + "source" + separator + "html" + separator;
        ftlJsonDataPath = resourcesPath + "data" + separator + "data.json";
        pdfFontName = "msjhbd.ttf";
        isUseChrome = false;
        JSONObject ftlJsonData = readFtlJsonData();
        setDefaultCssAndImagesPath(ftlJsonData);
        ftlJsonData.forEach((k, v) -> ftlKeyVal.put(String.valueOf(k), String.valueOf(v)));
        htmlSourceFileName = Util.getFirstFileNameInDirectory(new File(htmlSourcePath)).orElse("source.html");
        ftlFileName = Util.getFileNameWithoutExtension(htmlSourceFileName) + ".ftl";
    }
    
    public JSONObject readFtlJsonData() {
        File jsonFile = new File(ftlJsonDataPath);
        return jsonFile.exists()
               ? JSONObject.fromObject(Util.readeFile(jsonFile))
               : new JSONObject();
    }
    
    void setDefaultCssAndImagesPath(JSONObject jsonObj) {
        Object cssPath = jsonObj.get("cssPath");
        if (cssPath == null) {
            jsonObj.element("cssPath", "file:///" + htmlSourcePath.replaceAll("\\\\", "/") + "css");
        }
        Object imagePath = jsonObj.get("imgPath");
        if (imagePath == null) {
            jsonObj.element("imagePath", "file:///" + htmlSourcePath.replaceAll("\\\\", "/") + "images");
        }
    }
    
    public boolean cleanResources() {
        boolean resultPdfDir = Util.cleanDirectory(new File(this.resultPdfDir));
        boolean resultHtmlDir = Util.cleanDirectory(new File(this.resultHtmlDir));
        boolean ftlDir = Util.cleanDirectory(new File(resultFtlDir));
        return resultPdfDir && resultHtmlDir && ftlDir;
    }
    
    public String getResourcesPath() {
        return resourcesPath;
    }
    
    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }
    
    public String getResultHtmlDir() {
        return resultHtmlDir;
    }
    
    public void setResultHtmlDir(String resultHtmlDir) {
        this.resultHtmlDir = resultHtmlDir;
    }
    
    public String getResultPdfDir() {
        return resultPdfDir;
    }
    
    public void setResultPdfDir(String resultPdfDir) {
        this.resultPdfDir = resultPdfDir;
    }
    
    public String getResultFtlDir() {
        return resultFtlDir;
    }
    
    public void setResultFtlDir(String ftlPath) {
        this.resultFtlDir = ftlPath;
    }
    
    public String getFtlFileName() {
        return ftlFileName;
    }
    
    public void setFtlFileName(String ftlFileName) {
        this.ftlFileName = ftlFileName;
    }
    
    public String getHtmlSourcePath() {
        return htmlSourcePath;
    }
    
    public void setHtmlSourcePath(String htmlSourcePath) {
        this.htmlSourcePath = htmlSourcePath;
    }
    
    public String getFtlJsonDataPath() {
        return ftlJsonDataPath;
    }
    
    public void setFtlJsonDataPath(String ftlJsonDataPath) {
        this.ftlJsonDataPath = ftlJsonDataPath;
    }
    
    public FreeMarkerKeyValue<String, String> getFtlKeyVal() {
        return ftlKeyVal;
    }
    
    public void setFtlKeyVal(FreeMarkerKeyValue<String, String> ftlKeyVal) {
        this.ftlKeyVal = ftlKeyVal;
    }
    
    public String getHtmlSourceFileName() {
        return htmlSourceFileName;
    }
    
    public void setHtmlSourceFileName(String htmlSourceFileName) {
        this.htmlSourceFileName = htmlSourceFileName;
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
}
