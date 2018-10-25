package freemarker;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 本類別主要用於紀錄ftl轉換html時使用過的key
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/2, MarkHuang,new
 * </ul>
 * @since 2018/3/2
 */
public class FreeMarkerKeyValue<K, V> extends HashMap<K, V> {
    private Map<K, V> usedProperties = new LinkedHashMap<>();

    @Override
    public V get(Object key) {
        usedProperties.put((K) key, super.get(key));
        return super.get(key);
    }

    public Map<K, V> getUsedProperties() {
        return usedProperties;
    }

    public void printUsedProperties(K key, V val) {
        System.err.println(key + " = " + val);
    }
}
