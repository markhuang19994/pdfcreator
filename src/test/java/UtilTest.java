import file.FileEvent;
import file.FileListener;
import file.FileManager;
import formate.HTMLFormatter;
import org.junit.Test;
import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class UtilTest {
    private PDFResourceInfo instance = PDFResourceInfo.getInstance();

    public static File searchFileInDirectory(File dir, String fileName) {
        List<File> fileList = new ArrayList<>();
        fileList.add(dir);
        return searchFileInDirectory(fileList, fileName);
    }

    private static File searchFileInDirectory(List<File> dirList, String fileName) {
        List<File> tempFileList = new ArrayList<>();
        boolean lastFile = true;
        for (File outerDir : dirList) {
            File[] innerDir = outerDir.listFiles();
            if (innerDir == null) continue;
            for (File innerFile : innerDir) {
                if (innerFile.isFile()) {
                    if (fileName.equals(innerFile.getName())) {
                        return innerFile;
                    }
                } else if (innerFile.isDirectory()) {
                    File[] inInnerFiles = innerFile.listFiles();
                    if (inInnerFiles != null && inInnerFiles.length != 0) lastFile = false;
                    tempFileList.add(innerFile);
                }
            }
        }
        dirList = tempFileList;
        if (lastFile) return null;
        return searchFileInDirectory(dirList, fileName);
    }

    @Test
    public void readeFileToStringTest() {
        long l = System.currentTimeMillis();
//        File file = searchFileInDirectory(new File("C:\\"), "aso.ini");
        List<File> file = Util.searchFileInDirectory(new File("C:\\"), "aso.ini");
        List<File> file2 = Util.searchFileInDirectory(new File("C:\\"), "Aspose.Pdf.dll");
        System.out.println(file2);
        System.out.println(System.currentTimeMillis() - l);
        Main.notStop();
    }


//    @Test
//    public void test2(){
//        FileManager.getInstance().addListener(new File("C:\\Users\\1710002NB01\\Documents\\SVN\\pdfcreator\\src\\main\\resources\\result\\ftl\\NT99.ftl"),new FileListener(){
//            @Override
//            public void onChange(FileEvent event) {
//                System.out.println("wow");
//                event.getCurrentTarget();
//            }
//        });
//        Main.notStop();
//    }





}
