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
public class PDFResourceInfo {
    private static PDFResourceInfo pdfResourceInfo;
    private String resourcesPath;
    private String htmlSourcePath;
    private String resultHtmlPath;
    private String resultPdfPath;
    private String htmlSourceFileName;
    private String ftlDirPath;
    private String ftlFileName;
    private String ftlKeyValJsonPath;
    private String fontName;
    private boolean isUseChrome;
    private FreeMarkerKeyValue keyVal = new FreeMarkerKeyValue();

    private PDFResourceInfo() {
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

    public static PDFResourceInfo getInstance() {
        if (pdfResourceInfo == null) pdfResourceInfo = new PDFResourceInfo();
        return pdfResourceInfo;
    }

    public JSONObject readJsonKeyValue() {
        File jsonFile = new File(ftlKeyValJsonPath);
        JSONObject jsonObj = jsonFile.exists() ? JSONObject.fromObject(Util.readeFile(jsonFile)) : new JSONObject();
        return setDefaultCssAndImagesPath(jsonObj);
    }

    private JSONObject setDefaultCssAndImagesPath(JSONObject jsonObj) {
        Object cssPath = jsonObj.get("cssPath");
        if (cssPath == null) {
            jsonObj.element("cssPath", "file:///" + htmlSourcePath.replaceAll("\\\\", "/") + "css");
        }
        Object imagePath = jsonObj.get("imgPath");
        if (imagePath == null) {
            jsonObj.element("imagePath", "file:///" + htmlSourcePath.replaceAll("\\\\", "/") + "images");
        }
        return jsonObj;
    }

    public void initResources() {
        resultHtmlPath = resourcesPath + "result" + separator + "html" + separator;
        resultPdfPath = resourcesPath + "result" + separator + "temp" + separator;
        ftlDirPath = resourcesPath + "result" + separator + "ftl" + separator;
        htmlSourcePath = resourcesPath + "result" + separator + "source_html" + separator;
        ftlKeyValJsonPath = resourcesPath + "data" + separator + "data.json";
        fontName = "msjhbd.ttf";
        isUseChrome = false;
        JSONObject jsonKeyValue = readJsonKeyValue();
        if (jsonKeyValue != null) {
            jsonKeyValue.forEach(keyVal::put);
        }
        htmlSourceFileName = Util.getFirstFileNameInDirectory(new File(htmlSourcePath)).orElse("source.html");
        ftlFileName = Util.getFileNameWithoutExtension(htmlSourceFileName) + ".ftl";
    }

    public boolean cleanResources() {
        boolean delete = Util.cleanDirectory(new File(resultPdfPath));
        boolean delete1 = Util.cleanDirectory(new File(resultHtmlPath));
        boolean delete2 = Util.cleanDirectory(new File(ftlDirPath));
        return delete && delete1 && delete2;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public String getResultHtmlPath() {
        return resultHtmlPath;
    }

    public void setResultHtmlPath(String resultHtmlPath) {
        this.resultHtmlPath = resultHtmlPath;
    }

    public String getResultPdfPath() {
        return resultPdfPath;
    }

    public void setResultPdfPath(String resultPdfPath) {
        this.resultPdfPath = resultPdfPath;
    }

    public String getFtlDirPath() {
        return ftlDirPath;
    }

    public void setFtlDirPath(String ftlPath) {
        this.ftlDirPath = ftlPath;
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

    public String getFtlKeyValJsonPath() {
        return ftlKeyValJsonPath;
    }

    public void setFtlKeyValJsonPath(String ftlKeyValJsonPath) {
        this.ftlKeyValJsonPath = ftlKeyValJsonPath;
    }

    public FreeMarkerKeyValue getKeyVal() {
        return keyVal;
    }

    public void setKeyVal(FreeMarkerKeyValue keyVal) {
        this.keyVal = keyVal;
    }

    public String getHtmlSourceFileName() {
        return htmlSourceFileName;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setUseChrome(boolean useChrome) {
        isUseChrome = useChrome;
    }

    public boolean isUseChrome() {
        return isUseChrome;
    }
}
