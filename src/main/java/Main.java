import formate.HTMLFormatter;
import pdf.ContinueCreatePDF;
import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java -jar "-Dfile.encoding=utf-8" -jar deploy -c
 *
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/19, MarkHuang,new
 * </ul>
 * @since 2018/10/19
 */
public class Main {
    public static void main(String[] args) {
        String argsStr = Arrays.asList(args).toString();
        if (args.length != 0) {
            if (argsStr.contains("-version")) {
                System.out.println(String.format("PDF製造機 版本:%s", "1.0.0"));
                return;
            } else if (argsStr.contains("-help")) {
                printHelp();
                return;
            }
        }
        PDFResourceInfo pdfResourceInfo = PDFResourceInfo.getInstance();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-r")) {
                if (isIndexNotOutOfBound(args, i + 1)) {
                    pdfResourceInfo.setResourcesPath(new File(args[i + 1]).getAbsolutePath() + File.separator);
                    pdfResourceInfo.initResources();
                } else {
                    return;
                }
            } else if (args[i].equalsIgnoreCase("-h")) {
                if (isIndexNotOutOfBound(args, i + 1)) {
                    pdfResourceInfo.setHtmlSourcePath(new File(args[i + 1]).getAbsolutePath() + File.separator);
                } else {
                    return;
                }
            } else if (args[i].equalsIgnoreCase("-f")) {
                if (isIndexNotOutOfBound(args, i + 1)) {
                    File ftlFile = new File(args[i + 1]);
                    pdfResourceInfo.setFtlDirPath(ftlFile.getParentFile().getAbsolutePath());
                    pdfResourceInfo.setFtlFileName(ftlFile.getName());
                } else {
                    return;
                }
            }
        }
        ContinueCreatePDF continueCreatePDF = new ContinueCreatePDF(pdfResourceInfo);
        HTMLFormatter htmlFormatter = HTMLFormatter.getInstance(pdfResourceInfo);
        System.out.println(String.format("資源目錄:%s", pdfResourceInfo.getResourcesPath()));
        System.out.println(String.format("HTML源目錄:%s", pdfResourceInfo.getHtmlSourcePath()));
        System.out.println(String.format("FTL源目錄:%s", pdfResourceInfo.getFtlDirPath()));
        System.out.println(String.format("HTML轉FTL產出檔案:%s", pdfResourceInfo.getResultHtmlPath() + Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()) + ".html"));
        System.out.println(String.format("HTML轉PDF產出檔案:%s", pdfResourceInfo.getResultPdfPath() + Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()) + ".pdf"));
        if (argsStr.contains("-clean")) {
            boolean b = pdfResourceInfo.cleanResources();
            if (b) System.err.println("resources資料清除成功");
        }
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-g")) {
                htmlFormatter.htmlToFtlFormat();
            } else if (arg.equalsIgnoreCase("-c")) {
                continueCreatePDF.createPDFWhenFTLChange();
            }
        }
    }

    private static boolean isIndexNotOutOfBound(String[] args, int index) {
        if (index >= args.length) {
            System.err.println(String.format("args not correct %s", Arrays.asList(args).toString()));
            return false;
        }
        return true;
    }

    private static void printHelp() {
        System.out.println("-g :generate ftl from html resource/result/source_html or use \"-h\" to figure one");
        System.out.println("-c :continue create pdf when resource/result/ftl changed or use \"-f\" to figure one");
        System.out.println("-r [dir path]:custom your resources root path");
        System.out.println("-f [file path]:custom your ftl file path");
        System.out.println("-h [dir path]:custom your html source directory");
        System.out.println("[other]");
        System.out.println("If you want put cutomer variable in ftl,you can edit data/data.json");
    }

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();

    private static void notStop(){
        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            System.out.println("service stopped, interrupted by other thread!");
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
