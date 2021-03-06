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
    private static FileManager fileManager = new FileManager();
    private static FileEventSource fileEventSource = FileEventSource.getInstance();

    private FileManager() {
    }

    public static FileManager getInstance() {
        return fileManager;
    }

    public void addListener(File file, FileListener listener) {
        fileEventSource.addFileListener(file, listener);
    }

    public void addListener(List<File> files, FileListener listener) {
        fileEventSource.addFileListener(files, listener);
    }

    public void removeListener(File file) {
        fileEventSource.removeFileListener(file);
    }

    public void removeListener(List<File> files) {
        fileEventSource.removeFileListener(files);
    }

    public void getFileInProject(){}
}
