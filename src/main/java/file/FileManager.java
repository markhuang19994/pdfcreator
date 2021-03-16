package file;

import java.io.File;
import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/23, MarkHuang,new
 * </ul>
 * @since 2018/10/23
 */
public class FileManager {
    private static final FileManager     FILE_MANAGER      = new FileManager();
    private static final FileEventSource FILE_EVENT_SOURCE = FileEventSource.getInstance();

    private FileManager() {
    }

    public static FileManager getInstance() {
        return FILE_MANAGER;
    }

    public void addListener(File file, FileListener listener) {
        FILE_EVENT_SOURCE.addFileListener(file, listener);
    }

    public void addListener(List<File> files, FileListener listener) {
        FILE_EVENT_SOURCE.addFileListener(files, listener);
    }

    public void removeListener(File file) {
        FILE_EVENT_SOURCE.removeFileListener(file);
    }

    public void removeListener(List<File> files) {
        FILE_EVENT_SOURCE.removeFileListener(files);
    }

    public void getFileInProject(){}
}
