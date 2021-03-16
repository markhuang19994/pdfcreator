package pdf;

import freemarker.FreeMarkerKeyValue;
import net.sf.json.JSONObject;
import util.Util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.io.File.separator;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class PDFResource {
    private File                               resourcesDir;
    private File                               sourceHtmlDir;
    private File                               resultHtmlDir;
    private File                               resultPdfDir;
    private File                               resultFtlDir;
    private File                               ftlFile;
    private File                               ftlJsonDataFile;
    private String                             pdfFontName;
    private FreeMarkerKeyValue<String, String> ftlKeyVal = new FreeMarkerKeyValue<>();
    
    private PDFResource() {
        String resPath = System.getProperty("user.home") + separator + "Desktop" + separator + "resources" + separator;
        resourcesDir = new File(resPath);
    }
    
    public static PDFResource getInstance() {
        return Nested.INSTANCE;
    }
    
    private static final class Nested {
        private static final PDFResource INSTANCE = new PDFResource();
    }
    
    public void initResources() {
        initFileAndDirectory();
        pdfFontName = "MSJH.TTF";
        JSONObject ftlJsonData = readFtlJsonData();
        ftlJsonData.forEach((k, v) -> ftlKeyVal.put(String.valueOf(k), String.valueOf(v)));
        this.ftlFile = initFtlFile();
    }
    
    public List<File> getSourceHtmlFiles() {
        File[] htmlFiles = sourceHtmlDir.listFiles(f -> f.isFile() && f.getName().matches("^.*\\.html$"));
    
        if (htmlFiles == null || htmlFiles.length == 0) {
            return null;
        }
    
        List<File> chooseHtmlFiles = new ArrayList<>();
        if (htmlFiles.length > 1) {
            System.out.println("there are several html files...");
            for (int i = 0; i < htmlFiles.length; i++) {
                System.out.printf("\t%s) %s%n", (i + 1), htmlFiles[i].getName());
            }
            Scanner sca = new Scanner(System.in);
            System.out.print("please choose one or more (split by ','): ");
    
            for (String idx : sca.nextLine().split(",")) {
                int chooseIdx = Integer.parseInt(idx.trim());
                chooseHtmlFiles.add(htmlFiles[chooseIdx - 1]);
            }
        }
        return chooseHtmlFiles;
    }
    
    private File initFtlFile() {
        File[] ftlFiles = resultFtlDir.listFiles(f -> f.isFile() && f.getName().matches("^.*\\.ftl$"));
        
        if (ftlFiles == null || ftlFiles.length == 0) {
            throw new RuntimeException("Ftl file not found.");
        }
        
        File ftlFile = ftlFiles[0];
        if (ftlFiles.length > 1) {
            System.out.println("there are several ftl files...");
            for (int i = 0; i < ftlFiles.length; i++) {
                System.out.printf("\t%s) %s%n", (i + 1), ftlFiles[i].getName());
            }
            Scanner sca = new Scanner(System.in);
            System.out.print("please choose one: ");
            
            int chooseIdx = Integer.parseInt(sca.nextLine().trim());
            ftlFile = ftlFiles[chooseIdx - 1];
        }
        return ftlFile;
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFileAndDirectory() {
        sourceHtmlDir = new File(resourcesDir, "source_html");
        resultFtlDir = new File(resourcesDir, "ftl");
        resultHtmlDir = new File(resourcesDir, "html");
        resultPdfDir = new File(resourcesDir, "pdf");
        
        resourcesDir.mkdirs();
        sourceHtmlDir.mkdir();
        resultFtlDir.mkdir();
        resultHtmlDir.mkdir();
        resultPdfDir.mkdir();
        
        ftlJsonDataFile = new File(new File(resourcesDir, "data"), "data.json");
        ftlJsonDataFile.getParentFile().mkdir();
    }
    
    public JSONObject readFtlJsonData() {
        final JSONObject data = new JSONObject();
        if (ftlJsonDataFile.exists()) {
            String jsonFileContent = Util.readeFile(ftlJsonDataFile);
            JSONObject jsonObject = JSONObject.fromObject(jsonFileContent);
            jsonObject.forEach((k, v) -> data.put(k, v));
        }
        
        File dataJs = new File(ftlJsonDataFile.getParentFile(), "data.js");
        if (dataJs.exists()) {
            try {
                String jsonFileContent = Util.readeFile(dataJs);
                ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
                ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
                Object eval = nashorn.eval(jsonFileContent + "\n var json = JSON.stringify(data)\n json");
                JSONObject jsonObject = JSONObject.fromObject(eval);
                jsonObject.forEach((k, v) -> data.put(k, v));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data.size() > 0) {
            System.out.println("data:" + data);
        }
        return data;
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
    
    public File getFtlFile() {
        return ftlFile;
    }
    
    public void setFtlFile(File ftlFile) {
        this.ftlFile = ftlFile;
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
