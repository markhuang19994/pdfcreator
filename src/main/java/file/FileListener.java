package file;

import java.util.EventListener;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/10/23, MarkHuang,new
 * </ul>
 * @since 2018/10/23
 */
public interface FileListener extends EventListener {
    default void onChange(FileEvent event) {

    }

    default void afterDeleted(FileEvent event) {

    }
}
