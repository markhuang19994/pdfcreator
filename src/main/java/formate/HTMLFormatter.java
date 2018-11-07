package formate;

import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;
import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class HTMLFormatter {
    private PDFResourceInfo pdfResourceInfo;

    private HTMLFormatter(PDFResourceInfo pdfResourceInfo) {
        this.pdfResourceInfo = pdfResourceInfo;
    }

    public static HTMLFormatter getInstance(PDFResourceInfo pdfResourceInfo) {
        return new HTMLFormatter(pdfResourceInfo);
    }

    /**
     * 將html轉換為ftl格式
     */
    public void htmlToFtlFormat() {
        //讀取檔案
        String source = Util.readeFile(new File(pdfResourceInfo.getHtmlSourcePath() + pdfResourceInfo.getHtmlSourceFileName()));
        //移除註解
        source = source.replaceAll("<!--((.|\n|\r|\t)*?)-->", "");
        //移除script標籤
        source = source.replaceAll("<script(.*?)/?>(</script>)?", "");
        //將可能未關閉的欄位關閉
        source = source.replaceAll("<(input|img|br|meta|link)(.*?)>", "<$1$2/>").replaceAll("//>", "/>");
        //checkbox 換成 image
        source = source.replaceAll("<input(.*?)checkbox(.*?)/>", "<img style='width: 10px;' src='\\${imagePath}/ballot_box.png' />");
        //image裡面的src更換成${imagePath!} 注:image資源必須擺在images資料夾下，並在data.json宣告imagePath絕對路徑
        source = source.replaceAll("<img(.*?)src(.*?)(['\"])((\\./)?images)[/\\\\](.*?)(['\"])(.*?)/>", "<img$1src$2'\\${imagePath!}/$6'$8 />");
        //link裡面的href更換成${cssPath!}
        source = source.replaceAll("<link(.*?)href(.*?)(['\"]((\\./)?css)[/\\\\](.*?)['\"](.*?)>)", "<link rel=\"stylesheet\" href=\"\\${cssPath!}/$6\" />");
        //添加ftl的標頭
        source = source.replaceAll("<!DOCTYPE.*?/?>", "");
        source = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + source;

        //在title後添加ftl的標籤
        StringBuilder sb = new StringBuilder(15000);
        int titleEndIndex = source.indexOf("</title>");
        int headEndIndex = source.indexOf("</head>");

        //sb.append("\n\t<#import \"FunctionUtil.ftl\" as func/>");
        sb.append("\n\t<style type=\"text/css\">");
        sb.append("\n\t\t@page {");
        sb.append("\n\t\t\tsize: a4;");//另一個可以嘗試的預設值1080px * 1510px
        sb.append("\n\t\t\tmargin: 0;");//消除預設的margin
        sb.append("\n\t\t}");
        sb.append("\n\t</style>\n");
        source = source.substring(0, headEndIndex) + sb.toString() + source.substring(headEndIndex);
        //移除超過2行的連續空白
        source = source.replaceAll("(\n\\s*\n){2,}","\n");

        //輸出ftl檔案
        Util.writeFile(new File(pdfResourceInfo.getFtlDirPath() + pdfResourceInfo.getFtlFileName()), source);
        System.err.println("HTML to FTL 轉換完成!");
    }

    /**
     * 把所有的css內容合併，並產出合併ftl的字串
     *
     * @param cssFileList css檔案
     * @param cssFtlName  合併檔的名稱
     * @return String
     */
    @Deprecated
    private String generateCssFtlString(List<File> cssFileList, String cssFtlName) {
        StringBuilder sb = new StringBuilder(15000);
        sb.append("<#macro ").append(cssFtlName).append(">");
        sb.append("\n<#assign imagePath = imgPath!>");
        sb.append("\n<style type=\"text/css\">");
        for (File file : cssFileList) {
            sb.append("\n");
            sb.append(Util.readeFile(file));
        }
        sb.append("\n</style>");
        sb.append("\n</#macro>");
        return sb.toString();
    }
}
