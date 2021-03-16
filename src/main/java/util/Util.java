package util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/22, MarkHuang,new
 * </ul>
 * @since 2018/10/22
 */
public class Util {
    public static String slashFilePath(File file) {
        return file.getAbsolutePath().replace("\\", "/");
    }
    
    public static String rmFileProtocol(String filePath) {
        return filePath.replace("file:///", "");
    }
    
    public static String readeFile(File file) {
        return readeFile(file, 0);
    }
    
    public static String readeFile(File file, int count) {
        StringBuilder sb = new StringBuilder(15000);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp).append("\n");
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException && count <= 20) {
                sleep(50);
                return readeFile(file, ++count);
            } else {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static void writeFile(File file, String str) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean cleanDirectory(File dir) {
        File[] files = dir.listFiles();
        boolean isAllDelete = true;
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    isAllDelete = isAllDelete && cleanDirectory(f);
                } else {
                    isAllDelete = isAllDelete && f.delete();
                }
            }
        }
        return isAllDelete;
    }
    
    public static String getFileNameWithoutExtension(String fileName) {
        return fileName.contains(".")
               ? fileName.split("\\.")[0]
               : fileName;
    }
    
    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static List<File> getAllFilesInDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        }
        return null;
    }
    
    private static final Map<String, List<File>> CACHE_FILE = new ConcurrentHashMap<>(3000);
    
    public static List<File> searchFileInDirectory(File dir, String fileName) {
        return searchFileInDirectory(dir, fileName, true);
    }
    
    public static List<File> searchFileInDirectory(File dir, String fileName, boolean findFirst) {
        List<File> fileInCache = getFileInCache(dir, fileName);
        if (fileInCache != null) return fileInCache;
        ExecutorService executor = Executors.newFixedThreadPool(8);
        File emptyFile = new File("$$emptyFile$$");
        List<Future<?>> futures = new ArrayList<>();
        BlockingDeque<File> deque = new LinkedBlockingDeque<>();
        List<File> result = new ArrayList<>();
        deque.push(dir);
        while ((!deque.isEmpty() || !isAllExecutionDone(futures)) && (!findFirst || result.size() <= 0)) {
            File innerDir = fileDeqPollFirst(deque);
            if (innerDir == null || emptyFile.getName().equals(innerDir.getName())) continue;
            Future<?> future = executor.submit(() -> {
                List<File> files = getAllFilesInDirectory(innerDir);
                List<File> dirFiles = new ArrayList<>();
                if (files == null || files.isEmpty()) {
                    deque.addLast(emptyFile);
                    return;
                }
                for (File file : files) {
                    if (file.isFile()) {
                        putFileInCache(file);
                        if (file.getName().equals(fileName)) {
                            result.add(file);
                        }
                    } else if (file.isDirectory()) {
                        dirFiles.add(file);
                    } else {
                        deque.addLast(emptyFile);
                    }
                }
                deque.addAll(dirFiles);
            });
            futures.add(future);
        }
        executor.shutdown();
        return result.size() == 0
               ? null
               : result;
    }
    
    private static List<File> getFileInCache(File dir, String fileName) {
        List<File> cacheList = CACHE_FILE.get(fileName);
        List<File> resultList = new ArrayList<>();
        if (cacheList == null) return null;
        for (File file : cacheList) {
            if (file.exists() && file.isFile() && file.getAbsolutePath().contains(dir.getAbsolutePath())) {
                resultList.add(file);
            }
        }
        return resultList.size() > 0
               ? resultList
               : null;
    }
    
    private static void putFileInCache(File file) {
        String fileName = file.getName();
        List<File> cacheList = CACHE_FILE.get(fileName);
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }
        cacheList.add(file);
        CACHE_FILE.put(fileName, cacheList);
    }
    
    private static boolean isAllExecutionDone(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            if (!future.isDone()) return false;
        }
        return true;
    }
    
    private static File fileDeqPollFirst(BlockingDeque<File> deque) {
        try {
            return deque.pollFirst(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 4 * 0x400; // 4KB
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;
        
        try {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                    outputStream.write(buf, 0, readLen);
                
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }
    
    public static String normalizeFilePath(String filePath) {
        return filePath.startsWith("~")
               ? filePath.replaceFirst("~", System.getProperty("user.home"))
               : filePath;
    }
    
    public static void unZipFiles(File sourceFile, File destFile, String password) throws ZipException {
        final ZipFile zipFile = new ZipFile(sourceFile);
        if (password != null && zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(destFile.getAbsolutePath());
    }
}
