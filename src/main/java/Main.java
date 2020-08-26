import analysis.ActionAnalysis;
import formate.HTMLFormatter;
import pdf.ContinueCreatePDF;
import pdf.PDFResource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public static void main(String[] args) {
        ActionAnalysis actionAnalysis = ActionAnalysis.getInstance(args);
        Map<String, List<String>> actionMap = actionAnalysis.getActionMap();
        PDFResource pdfResource = PDFResource.getInstance();
        System.out.println(getVersion() + "\n");

        actionAnalysis.getActionFirstParam("-r").ifPresent(param -> {
            pdfResource.setResourcesDir(new File(param));
        });
    
        if (actionMap.containsKey("-init")) {
            final File initDir = new File("/init");
            if (initDir.exists()) {
                try {
                    Runtime.getRuntime().exec("rm -rf /resources").waitFor();
                    Runtime.getRuntime().exec("cp -r /init/. /resources", null, initDir).waitFor();
                    System.out.println("init success.");
                    return;
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("Init dir not exist:" + initDir.getAbsolutePath());
            }
        };
        
        HTMLFormatter htmlFormatter = HTMLFormatter.getInstance(pdfResource);
//        System.out.printf("資源目錄:%s\n", pdfResource.getResourcesDir());
//        System.out.printf("HTML源目錄:%s\n", pdfResource.getSourceHtmlDir());
//        System.out.printf("FTL源目錄:%s\n", pdfResource.getResultFtlDir());
//        System.out.printf("HTML轉FTL產出檔案:%s\n", pdfResource.getResultHtmlDir() + separator + Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".html");
//        System.out.printf("HTML轉PDF產出檔案:%s\n", pdfResource.getResultPdfDir() + separator + Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".pdf");

        actionAnalysis.getActionFirstParam("-font").ifPresent(fontName -> {
            pdfResource.setPdfFontName(fontName);
            System.out.printf("Create pdf use font: %s\n",fontName);
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
        return String.format("PDF製造機 版本:%s", "1.1.2");
    }
}
