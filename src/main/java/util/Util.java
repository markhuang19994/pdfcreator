package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class Util {
    public static String readeFileToString(File file) {
        StringBuilder sb = new StringBuilder(15000);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeStringToFile(File file, String str) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static String getFileNameWithoutExtension(String fileName) {
        return fileName.contains(".") ? fileName.split("\\.")[0] : fileName;
    }

    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
