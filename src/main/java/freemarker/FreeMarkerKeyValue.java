package freemarker;

import pdf.PDFResource;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 本類別主要用於紀錄ftl轉換html時使用過的key
 *
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/2, MarkHuang,new
 * </ul>
 * @since 2018/3/2
 */
public class FreeMarkerKeyValue<K, V> extends HashMap<K, V> {
    private              Map<K, V>   usedProperties = new LinkedHashMap<>();
    
    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        V result = (V) super.get(key);
        final PDFResource pdfResource = PDFResource.getInstance();
        if ("cssPath".equals(key)) {
            result = (V) pdfResource.getCssPath();
        } else if ("imagePath".equals(key)) {
            result = (V) pdfResource.getImagePath();
        }
        
        usedProperties.put((K) key, result);
        return result;
    }
    
    public Map<K, V> getUsedProperties() {
        return usedProperties;
    }
    
    public void clearUsedProperties() {
        usedProperties.clear();
    }
}
