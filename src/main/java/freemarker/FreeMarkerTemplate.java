package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/15, MarkHuang,new
 * </ul>
 * @since 2018/2/15
 */
public class FreeMarkerTemplate {
    public Template getTemplate(String dir, String name) {
        return getTemplate(dir, name, 0);
    }

    private Template getTemplate(String dir, String name, int count) {
        try {
            Configuration cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File(dir));
            return cfg.getTemplate(name);
        } catch (IOException e) {
            if (count <= 20 && e instanceof FileNotFoundException) {
                Util.sleep(150);
                return getTemplate(dir, name, ++count);
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }
}

