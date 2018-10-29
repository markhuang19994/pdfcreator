import org.junit.Test;
import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println(file);
        System.out.println(System.currentTimeMillis() - l);
    }


    @Test
    public void test2(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }





}
