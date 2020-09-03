import analysis.ActionAnalysis;
import formate.HTMLFormatter;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import pdf.ContinueCreatePDF;
import pdf.PDFResource;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * args解析順序 version -> help -> r -> h -> f -> clean -> g -> c
 *
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/19, MarkHuang,new
 * </ul>
 * @since 2018/10/19
 */
public class Main {
    public static void main(String[] args) throws ZipException {
        ActionAnalysis actionAnalysis = ActionAnalysis.getInstance(args);
        Map<String, List<String>> actionMap = actionAnalysis.getActionMap();
        PDFResource pdfResource = PDFResource.getInstance();
        System.out.println(getVersion() + "\n");
        
        actionAnalysis.getActionFirstParam("-r").ifPresent(param -> {
            pdfResource.setResourcesDir(new File(param));
        });
    
        boolean isNeedInit = false;
        if (!pdfResource.getResourcesDir().exists()) {
            System.out.println("pdf resources dir is not exist, do you want init? [Y (default)/N]: ");
            final Scanner sca = new Scanner(System.in);
            if (sca.nextLine().equalsIgnoreCase("Y")) {
                isNeedInit = true;
            }
        }
        
        if (actionMap.containsKey("-init") || isNeedInit) {
            final File initDir = pdfResource.getResourcesDir();
            if (!initDir.exists() && !initDir.mkdirs()) {
                throw new RuntimeException("init dir not found.");
            }
            
            final URL resource = Main.class.getClassLoader().getResource("res/res.zip");
            if (resource != null) {
                final File resZip = new File(resource.getFile());
                if (resZip.exists() && resZip.isFile()) {
                    unZipFiles(resZip, initDir, null);
                } else {
                    throw new RuntimeException("res.zip is not exist or not a file.");
                }
            } else {
                throw new RuntimeException("res/res.zip not found in classpath.");
            }
            System.out.println("init success.");
            return;
        }
        
        HTMLFormatter htmlFormatter = HTMLFormatter.getInstance(pdfResource);
        
        actionAnalysis.getActionFirstParam("-font").ifPresent(fontName -> {
            pdfResource.setPdfFontName(fontName);
            System.out.printf("Create pdf use font: %s\n", fontName);
        });
        
        if (actionMap.containsKey("-clean")) {
            boolean b = pdfResource.cleanResources();
            if (b) System.out.println("resources dir clear success");
        }
        
        pdfResource.initResources();
        if (actionMap.containsKey("-g")) {
            htmlFormatter.htmlToFtlFormat();
        }
        
        ContinueCreatePDF continueCreatePDF = new ContinueCreatePDF(pdfResource);
        continueCreatePDF.createHtmlAndPdf();
        if (actionMap.containsKey("-c")) {
            continueCreatePDF.createPDFWhenResourceChange();
        }
    }
    
    private static String getVersion() {
        return String.format("PDF creator version:%s", "1.1.5");
    }
    
    public static File unZipFiles(File sourceFile, File destFile, String password) throws ZipException {
        final ZipFile zipFile = new ZipFile(sourceFile);
        if (password != null && zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(destFile.getAbsolutePath());
        return destFile;
    }
}
