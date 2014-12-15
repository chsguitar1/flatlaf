package org.ocsoft.flatlaf;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.ocsoft.flatlaf.utils.ImageUtils;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

public class ResourceLoader {
    
    /**
     * Resource icons cache.
     */
    private static final Map<String, ImageIcon> resourceIconsCache = new HashMap<String, ImageIcon>();
    
    
    
    private ResourceLoader() {}
    
    /**
     * returns {ResourceLoader package} + "icons/" + filename as ImageIcon
     * @param filename
     * @return
     */
    public static ImageIcon loadIcon(String filename) {
        URL resource = ResourceLoader.class.getResource("icons/" + filename);
        return (resource == null) ? null : new ImageIcon(resource);
    }
    
    /**
     * returns {ResourceLoader package} + "icons/" + filename as ImageIcon
     * @param filename
     * @return
     */
    public static ImageIcon loadIcon(String filename, float transparency) {
        String key = filename
                + FlatLafStyleConstants.SEPARATOR + transparency;
        if (resourceIconsCache.containsKey(key)) {
            return resourceIconsCache.get(key);
        } else {
            ImageIcon icon = loadIcon(filename);
            if (transparency < 1f && icon != null) {
                icon = ImageUtils.createTransparentCopy(icon, transparency);
            }
            resourceIconsCache.put(key, icon);
            return icon;
        }
    }
    
}
