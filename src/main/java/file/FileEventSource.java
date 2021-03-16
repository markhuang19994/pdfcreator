package file;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/23, MarkHuang,new
 * </ul>
 * @since 2018/10/23
 */
class FileEventSource {
    
    private static final FileEventSource         FILE_EVENT_SOURCE = new FileEventSource();
    private final        Map<File, FileListener> listenerMap       = new ConcurrentHashMap<>();
    private final        Map<File, Long>         lastModifyTimeMap = new ConcurrentHashMap<>();
    
    private FileEventSource() {
        new Thread(this::monitorFile).start();
    }
    
    static FileEventSource getInstance() {
        return FILE_EVENT_SOURCE;
    }
    
    void addFileListener(File file, FileListener fileListener) {
        listenerMap.put(file, fileListener);
        lastModifyTimeMap.put(file, file.lastModified());
    }
    
    void addFileListener(List<File> files, FileListener fileListener) {
        if (files == null) return;
        files.forEach(file -> addFileListener(file, fileListener));
    }
    
    void removeFileListener(File file) {
        listenerMap.remove(file);
        lastModifyTimeMap.remove(file);
    }
    
    void removeFileListener(List<File> files) {
        if (files == null) return;
        files.forEach(this::removeFileListener);
    }
    
    private void notifyChangeEvent(File file) {
        FileEvent fileEvent = new FileEvent(file);
        listenerMap.get(file).onChange(fileEvent);
    }
    
    private void notifyDeleteEvent(File file) {
        FileEvent fileEvent = new FileEvent(file);
        listenerMap.get(file).afterDeleted(fileEvent);
    }
    
    /**
     * 每20毫秒監控檔案的變化
     */
    private void monitorFile() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            try {
                if (listenerMap.size() == 0) return;
                Set<File> files = listenerMap.keySet();
                for (File file : files) {
                    long lastModified = file.lastModified();
                    if (lastModified != lastModifyTimeMap.get(file)) {
                        lastModifyTimeMap.put(file, lastModified);
                        notifyChangeEvent(file);
                    }
                    if (!file.exists()) {
                        notifyDeleteEvent(file);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 20, TimeUnit.MILLISECONDS);
    }
    
}
