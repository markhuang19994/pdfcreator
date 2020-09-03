package freemarker;

import pdf.PDFResource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<K> unUseKey = new HashSet<>();
    
    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        V result = (V) super.get(key);
        
        final PDFResource pdfResource = PDFResource.getInstance();
        if (result == null) {
            if ("cssPath".equals(key)) {
                result = (V) pdfResource.getCssPath();
            } else if ("imagePath".equals(key) || "imgPath".equals(key)) {
                result = (V) pdfResource.getImagePath();
            }
        }
        
        unUseKey.remove((K) key);
        return result;
    }
    
    public Set<K> getUnUseKey() {
        return unUseKey;
    }
    
    public void resetUnUseKey() {
        unUseKey.clear();
        unUseKey.addAll(this.keySet());
    }
}
