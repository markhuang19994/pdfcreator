package formate;

import pdf.PDFResourceInfo;
import util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.io.File.separator;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class HTMLFormatter {
    private PDFResourceInfo pdfResourceInfo;
    private String htmlSourcePath;
    private String cssFtlName;

    private HTMLFormatter(PDFResourceInfo pdfResourceInfo) {
        this.pdfResourceInfo = pdfResourceInfo;
        this.htmlSourcePath = pdfResourceInfo.getResourcesPath() + "result" + separator + "source_html" + separator;
        this.cssFtlName = Util.getFileNameWithoutExtension(pdfResourceInfo.getFtlFileName()).toLowerCase() + "_style";
    }

    public static HTMLFormatter getInstance(PDFResourceInfo pdfResourceInfo) {
        return new HTMLFormatter(pdfResourceInfo);
    }

    /**
     * 將html轉換為ftl格式
     */
    public void htmlToFtlFormat() {
        //讀取檔案
        String source = Util.readeFileToString(new File(htmlSourcePath + "source.html"));
        //移除註解
        source = source.replaceAll("<!--((.|\n|\r|\t)*?)-->", "");
        //移除script標籤
        source = source.replaceAll("<script(.*?)/?>(</script>)?", "");
        //將可能未關閉的欄位關閉
        source = source.replaceAll("<(input|img|br|meta)(.*?)>", "<$1$2/>").replaceAll("//>", "/>");
        //checkbox 換成 image
        source = source.replaceAll("<input(.*?)checkbox(.*?)/>", "<img style='width: 10px;' src='\\${imagePath}/ballot_box.png' />");
        //image裡面的src更換成${imagePath}
        source = source.replaceAll("<img(.*?)src(.*?)(['\"])(\\.?/images)/(.*?)(['\"])(.*)/>", "<img$1src$2'\\${imagePath}/$5'$7 />");
        //添加ftl的標頭
        source = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + source;
//        int i = source.indexOf("<html>");
//        source = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
//                + source.substring(i);

        //取得css指向的檔案名稱
        List<String> cssFileNameList = new ArrayList<>();
        Pattern stylePattern = Pattern.compile("<link(.*?)href(.*?)(['\"])(.*?)(['\"])(.*?)>");
        Matcher styleMatcher = stylePattern.matcher(source);
        while (styleMatcher.find()) {
            String[] split = styleMatcher.group(4).split("/");
            cssFileNameList.add(split[split.length - 1]);
        }

        source = source.replaceAll("<link(.*?)/?>", "");
        System.err.println("發現CSS: " + cssFileNameList);

        List<File> cssFileList = new ArrayList<>();
        cssFileNameList.forEach(cssFileName -> {
            File cssFile = Util.searchFileInDirectory(new File(htmlSourcePath), cssFileName);
            if (cssFile == null) {
                System.err.println("未找到css檔案: " + cssFileName);
            } else {
                cssFileList.add(cssFile);
            }
        });

        String css = generateCssFtlString(cssFileList);

        Util.writeStringToFile(new File(pdfResourceInfo.getFtlDirPath() + cssFtlName + ".ftl"), css);

        //在title後添加ftl的標籤
        StringBuilder sb = new StringBuilder(15000);
        int titleEndIndex = source.indexOf("</title>");
        int headEndIndex = source.indexOf("</head>");
        sb.append("\n\t<#include \"").append(cssFtlName).append(".ftl\">");
        sb.append("\n\t<#import \"FunctionUtil.ftl\" as func/>");
        sb.append("\n\t<style type=\"text/css\">");
        sb.append("\n\t\t@page {");
        sb.append("\n\t\t\tsize: 1080px 1510px;");
        sb.append("\n\t\t}");
        sb.append("\n\t</style>");
        sb.append("\n\t<@").append(cssFtlName).append(" />\n");
        source = source.substring(0, titleEndIndex + 8) + sb.toString() + source.substring(headEndIndex);

        //輸出檔案
        Util.writeStringToFile(new File(pdfResourceInfo.getFtlDirPath() + pdfResourceInfo.getFtlFileName()), source);
        System.err.println("HTML to FTL 轉換完成!");
    }

    private String generateCssFtlString(List<File> cssFileList) {
        StringBuilder sb = new StringBuilder(15000);
        sb.append("<#macro ").append(cssFtlName).append(">");
        sb.append("\n<#assign imagePath = imgPath!>");
        sb.append("\n<style type=\"text/css\">");
        for (File file : cssFileList) {
            sb.append("\n");
            sb.append(Util.readeFileToString(file));
        }
        sb.append("\n</style>");
        sb.append("\n</#macro>");
        return sb.toString();
    }
}
