import analysis.ActionAnalysis;
import formate.HTMLFormatter;
import pdf.ContinueCreatePDF;
import pdf.PDFResource;
import util.Util;

import java.io.File;
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
        if (actionMap.size() == 0) {
            System.out.println(getHelp());
            return;
        }
        if (actionMap.containsKey("-version")) {
            System.out.println(getVersion());
            return;
        }

        if (actionMap.containsKey("-help")) {
            System.out.println(getHelp());
            return;
        }

        actionAnalysis.getActionFirstParam("-r").ifPresent(param -> {
            pdfResource.setResourcesDir(new File(param));
            pdfResource.initResources();
        });

        actionAnalysis.getActionFirstParam("-h").ifPresent(param -> {
            pdfResource.setSourceHtmlDir(new File(param));
        });

        actionAnalysis.getActionFirstParam("-f").ifPresent(param -> {
            File ftlFile = new File(param);
            pdfResource.setResultFtlDir(ftlFile.getParentFile());
            pdfResource.setFtlFileName(ftlFile.getName());
        });

        actionAnalysis.getActionFirstParam("-html.name").ifPresent(fileName -> {
            pdfResource.setSourceHtmlName(fileName);
            System.out.printf("HTML 檔案名稱: %s\n",fileName);
        });

        ContinueCreatePDF continueCreatePDF = new ContinueCreatePDF(pdfResource);
        HTMLFormatter htmlFormatter = HTMLFormatter.getInstance(pdfResource);
        System.out.printf("資源目錄:%s\n", pdfResource.getResourcesDir());
        System.out.printf("HTML源目錄:%s\n", pdfResource.getSourceHtmlDir());
        System.out.printf("FTL源目錄:%s\n", pdfResource.getResultFtlDir());
        System.out.printf("HTML轉FTL產出檔案:%s\n", pdfResource.getResultHtmlDir() + separator + Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".html");
        System.out.printf("HTML轉PDF產出檔案:%s\n", pdfResource.getResultPdfDir() + separator + Util.getFileNameWithoutExtension(pdfResource.getFtlFileName()) + ".pdf");

        actionAnalysis.getActionFirstParam("-font").ifPresent(fontName -> {
            pdfResource.setPdfFontName(fontName);
            System.out.printf("PDF使用字體: %s\n",fontName);
        });

        actionAnalysis.getActionFirstParam("-c").ifPresent(isUseChrome -> {
            pdfResource.setUseChrome(isUseChrome.equalsIgnoreCase("use_chrome"));
            System.out.println("使用chrome產生PDF");
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

    private static String getHelp() {
        return "-g :generate ftl from html resource/result/source_html or use \"-h\" to figure one" + "\n" +
                "-c :continue create pdf when resource/result/ftl changed or use \"-f\" to figure one" + "\n" +
                "-clean :clean ftl & html & temp directory" + "\n" +
                "-r [dir path]:custom your resources root path" + "\n" +
                "-f [file path]:custom your ftl file path" + "\n" +
                "-h [dir path]:custom your html source directory" + "\n" +
                "-font [font name]:custom your pdf font" + "\n" +
                "[other]" + "\n" +
                "If you want put customer variable in ftl,you can edit data/data.json" + "\n";
    }

    private static String getVersion() {
        return String.format("PDF製造機 版本:%s", "1.0.0");
    }
}
