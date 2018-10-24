package file;

import util.Util;

import java.io.File;
import java.util.*;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/23, MarkHuang,new
 * </ul>
 * @since 2018/10/23
 */
class FileEventSource {

    private Map<File, FileListener> listenerMap = new HashMap<>();
    private Map<File, Long> lastModifyTimeMap = new HashMap<>();
    private static FileEventSource fileEventSource = new FileEventSource();

    private FileEventSource() {
        new Thread(this::monitorFile).start();
    }

    static FileEventSource getInstance() {
        return fileEventSource;
    }

    void addFileListener(File file, FileListener fileListener) {
        listenerMap.put(file, fileListener);
        lastModifyTimeMap.put(file, file.lastModified());
    }

    private void notifyChangeEvent(File file) {
        FileEvent fileEvent = new FileEvent(file);
        listenerMap.get(file).onChange(fileEvent);
    }

    private void notifyDestroyEvent(File file) {
        FileEvent fileEvent = new FileEvent(file);
        listenerMap.get(file).onDestroy(fileEvent);
    }

    public void monitorFile() {
        while (true) {
            Util.sleep(50);
            if (listenerMap.size() == 0) continue;
            Set<File> files = listenerMap.keySet();
            for (File file : files) {
                long lastModified = file.lastModified();
                if (lastModified != lastModifyTimeMap.get(file)) {
                    notifyChangeEvent(file);
                    lastModifyTimeMap.put(file, lastModified);
                }
                if (!file.exists()) {
                    notifyDestroyEvent(file);
                }
            }
        }
    }

}
