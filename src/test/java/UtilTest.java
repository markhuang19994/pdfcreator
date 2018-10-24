import file.FileEvent;
import file.FileListener;
import file.FileManager;
import formate.HTMLFormatter;
import org.junit.Test;
import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class UtilTest {
    private PDFResourceInfo instance = PDFResourceInfo.getInstance();

//    @Test
//    public void readeFileToStringTest(){
//        String s = Util.readeFileToString(new File(instance.getHtmlSourcePath() + "source.html"));
//        System.out.println(System.getProperty("user.dir"));
//    }


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
