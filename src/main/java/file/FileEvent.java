package file;

import java.io.File;
import java.util.EventObject;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/23, MarkHuang,new
 * </ul>
 * @since 2018/10/23
 */
public class FileEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public FileEvent(Object source) {
        super(source);
        if (!(source instanceof File)) {
            throw new IllegalArgumentException("Source type must be File or it's son");
        }
    }

    public File getCurrentTarget() {
        return (File) source;
    }
}
