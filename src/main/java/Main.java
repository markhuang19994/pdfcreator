import analysis.ActionAnalysis;
import formate.HTMLFormatter;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import pdf.ContinueCreatePDF;
import pdf.PDFResource;
import util.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

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
    public static void main(String[] args) throws ZipException, IOException {
        ActionAnalysis actionAnalysis = ActionAnalysis.getInstance(args);
        Map<String, List<String>> actionMap = actionAnalysis.getActionMap();
        PDFResource pdfResource = PDFResource.getInstance();
        System.out.println(getVersion() + "\n");
    
        if (actionMap.size() == 0 || actionMap.containsKey("-help")) {
            System.out.println(getHelp());
            return;
        }
        
        actionAnalysis.getActionFirstParam("-r").ifPresent(param ->
                pdfResource.setResourcesDir(new File(Util.normalizeFilePath(param))));
    
        System.out.println("resource dir:" + pdfResource.getResourcesDir().getAbsolutePath());
    
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
    
            final InputStream resource = Main.class.getClassLoader().getResourceAsStream("res/res.zip");
            if (resource != null) {
                byte[] resourceBytes = Util.readAllBytes(resource);
                String fileName = UUID.randomUUID().toString().replace("-", "") + ".zip";
                File resZip = new File(System.getProperty("java.io.tmpdir"), fileName);
                resZip.deleteOnExit();
                Files.write(resZip.toPath(), resourceBytes, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
                Util.unZipFiles(resZip, initDir, null);
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
            for (File sourceHtmlFile : pdfResource.getSourceHtmlFiles()) {
                htmlFormatter.htmlToFtlFormat(sourceHtmlFile);
            }
        }
        
        ContinueCreatePDF continueCreatePDF = new ContinueCreatePDF(pdfResource);
        continueCreatePDF.createHtmlAndPdf();
        if (actionMap.containsKey("-c")) {
            continueCreatePDF.createPDFWhenResourceChange();
        }
    }
    
    private static String getHelp() {
        return "-g :generate ftl from {resource-root}/source_html or use \"-h\" to figure one" + "\n" +
                "-c :continue create pdf after {resource-root}/ftl changed or use \"-f\" to figure one" + "\n" +
                "-clean :clean ftl & html & temp directory" + "\n" +
                "-r :specific resources root path" + "\n" +
                "-f :specific ftl file path" + "\n" +
                "-h :specific html source directory" + "\n" +
                "-font :specific font which be used to gen pdf" + "\n";
    }
    
    private static String getVersion() {
        return String.format("PDF creator version:%s", "1.2.0");
    }
}
