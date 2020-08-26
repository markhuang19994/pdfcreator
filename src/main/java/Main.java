import analysis.ActionAnalysis;
import formate.HTMLFormatter;
import pdf.ContinueCreatePDF;
import pdf.PDFResource;
import util.Util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static java.io.File.separator;

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
        System.err.println(getVersion() + "\n");

        actionAnalysis.getActionFirstParam("-r").ifPresent(param -> {
            pdfResource.setResourcesDir(new File(param));
            pdfResource.initResources();
        });
        
        ContinueCreatePDF continueCreatePDF = new ContinueCreatePDF(pdfResource);
        HTMLFormatter htmlFormatter = HTMLFormatter.getInstance(pdfResource);
//        System.out.printf("資源目錄:%s\n", pdfResource.getResourcesDir());
//        System.out.printf("HTML源目錄:%s\n", pdfResource.getSourceHtmlDir());
//        System.out.printf("FTL源目錄:%s\n", pdfResource.getResultFtlDir());
//        System.out.printf("HTML轉FTL產出檔案:%s\n", pdfResource.getResultHtmlDir() + separator + Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".html");
//        System.out.printf("HTML轉PDF產出檔案:%s\n", pdfResource.getResultPdfDir() + separator + Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".pdf");

        actionAnalysis.getActionFirstParam("-font").ifPresent(fontName -> {
            pdfResource.setPdfFontName(fontName);
            System.out.printf("PDF使用字體: %s\n",fontName);
        });

        if (actionMap.containsKey("-clean")) {
            boolean b = pdfResource.cleanResources();
            if (b) System.err.println("resources資料清除成功");
        }

        if (actionMap.containsKey("-g")) {
            htmlFormatter.htmlToFtlFormat();
        }

        continueCreatePDF.createHtmlAndPdf();
        if (actionMap.containsKey("-c")) {
            continueCreatePDF.createPDFWhenResourceChange();
        }
    }
    
    private static String getVersion() {
        return String.format("PDF製造機 版本:%s", "1.1.2");
    }
}
