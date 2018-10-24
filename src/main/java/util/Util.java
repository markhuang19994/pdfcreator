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

    private static List<File> getAllDirFiles(File dir, String fileName) {
        File[] files = dir.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        }
        return null;
    }

    private static Map<String, String> cacheFilePath = new HashMap<>(3000);

    public static File searchFileInDirectory(File dir, String fileName) {
        String cachePath = cacheFilePath.get(fileName);
        if (cachePath != null) return new File(cachePath);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Future<?>> futures = new ArrayList<Future<?>>();
        BlockingDeque<File> deque = new LinkedBlockingDeque<>();
        List<File> result = new ArrayList<>();
        deque.push(dir);
        while (result.size() == 0 && (!deque.isEmpty() || !isAllExecDone(futures))) {
            File innerDir = fileDeqPollLast(deque);
            if (innerDir == null) continue;
            Future<?> future = executor.submit(() -> {
                List<File> files = getAllDirFiles(innerDir, fileName);
                if (files == null) return;
                for (File file : files) {
                    if (!file.exists()) continue;
                    if (file.isFile() && file.getName().equals(fileName)) {
                        cacheFilePath.put(file.getName(), file.getAbsolutePath());
                        result.add(file);
                    } else if (file.isDirectory()) {
                        deque.push(file);
                    }
                }
            });
            futures.add(future);
        }
        executor.shutdown();
        return result.size() == 0 ? null : result.get(0);
    }

    private static boolean isAllExecDone(List<Future<?>> futures){
        for (Future<?> future : futures) {
            if (!future.isDone()) return false;
        }
        return true;
    }

    private static File fileDeqPollLast(BlockingDeque<File> deque) {
        try {
            return deque.pollLast(100,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
