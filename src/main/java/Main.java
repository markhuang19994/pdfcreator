import analysis.ActionAnalysis;
import formate.HTMLFormatter;
import pdf.ContinueCreatePDF;
import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;
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
        PDFResourceInfo pdfResourceInfo = PDFResourceInfo.getInstance();
        if (actionMap.size() == 0) return;
        if (actionMap.containsKey("-version")) {
            System.out.println(getVersion());
            return;
        }

        if (actionMap.containsKey("-help")) {
            System.out.println(getHelp());
            return;
        }

        actionAnalysis.getActionFirstParam("-r").ifPresent(param -> {
            pdfResourceInfo.setResourcesPath(new File(param).getAbsolutePath() + File.separator);
            pdfResourceInfo.initResources();
        });

        actionAnalysis.getActionFirstParam("-h").ifPresent(param -> {
            pdfResourceInfo.setHtmlSourcePath(new File(param).getAbsolutePath() + File.separator);
        });

        actionAnalysis.getActionFirstParam("-f").ifPresent(param -> {
            File ftlFile = new File(param);
            pdfResourceInfo.setFtlDirPath(ftlFile.getParentFile().getAbsolutePath());
            pdfResourceInfo.setFtlFileName(ftlFile.getName());
        });

        ContinueCreatePDF continueCreatePDF = new ContinueCreatePDF(pdfResourceInfo);
        HTMLFormatter htmlFormatter = HTMLFormatter.getInstance(pdfResourceInfo);
        System.out.printf("資源目錄:%s\n", pdfResourceInfo.getResourcesPath());
        System.out.printf("HTML源目錄:%s\n", pdfResourceInfo.getHtmlSourcePath());
        System.out.printf("FTL源目錄:%s\n", pdfResourceInfo.getFtlDirPath());
        System.out.printf("HTML轉FTL產出檔案:%s\n", pdfResourceInfo.getResultHtmlPath() + Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()) + ".html");
        System.out.printf("HTML轉PDF產出檔案:%s\n", pdfResourceInfo.getResultPdfPath() + Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()) + ".pdf");

        actionAnalysis.getActionFirstParam("-font").ifPresent(fontName -> {
            pdfResourceInfo.setFontName(fontName);
            System.out.printf("PDF使用字體: %s\n",fontName);
        });

        if (actionMap.containsKey("-clean")) {
            boolean b = pdfResourceInfo.cleanResources();
            if (b) System.err.println("resources資料清除成功");
        }

        if (actionMap.containsKey("-g")) {
            htmlFormatter.htmlToFtlFormat();
        }

        if (actionMap.containsKey("-c")) {
            continueCreatePDF.createPDFWhenFTLResourceChange();
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
