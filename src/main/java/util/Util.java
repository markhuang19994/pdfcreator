package util;

import java.io.*;
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
    public static String readeFile(File file) {
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

    public static void writeFile(File file, String str) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileNameWithoutExtension(String fileName) {
        return fileName.contains(".") ? fileName.split("\\.")[0] : fileName;
    }

    public static File getFileFormFileURI(String uri) {
        return new File(uri.replaceFirst("file:///", ""));
    }

    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<File> getAllFilesInDirectory(File dir, String fileName) {
        File[] files = dir.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        }
        return null;
    }

    private static Map<String, List<File>> cacheFile = new ConcurrentHashMap<>(3000);

    public static List<File> searchFileInDirectory(File dir, String fileName) {
        return searchFileInDirectory(dir, fileName, true);
    }

    public static List<File> searchFileInDirectory(File dir, String fileName, boolean findFirst) {
        List<File> fileInCache = getFileInCache(dir, fileName);
        if (fileInCache != null) return fileInCache;
        ExecutorService executor = Executors.newFixedThreadPool(8);
        File emptyFile = new File("$$emptyFile$$");
        List<Future<?>> futures = new ArrayList<Future<?>>();
        BlockingDeque<File> deque = new LinkedBlockingDeque<>();
        List<File> result = new ArrayList<>();
        deque.push(dir);
        while ((!deque.isEmpty() || !isAllExecutionDone(futures))) {
            if (findFirst && result.size() > 0) break;
            File innerDir = fileDeqPollFirst(deque);
            if (innerDir == null || emptyFile.getName().equals(innerDir.getName())) continue;
            Future<?> future = executor.submit(() -> {
                List<File> files = getAllFilesInDirectory(innerDir, fileName);
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
        return result.size() == 0 ? null : result;
    }

    private static List<File> getFileInCache(File dir, String fileName) {
        List<File> cacheList = cacheFile.get(fileName);
        List<File> resultList = new ArrayList<>();
        if (cacheList == null) return null;
        for (File file : cacheList) {
            if (file.exists() && file.isFile() && file.getAbsolutePath().contains(dir.getAbsolutePath())) {
                resultList.add(file);
            }
        }
        return resultList.size() > 0 ? resultList : null;
    }

    private static void putFileInCache(File file) {
        String fileName = file.getName();
        List<File> cacheList = cacheFile.get(fileName);
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }
        cacheList.add(file);
        cacheFile.put(fileName, cacheList);
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
}
