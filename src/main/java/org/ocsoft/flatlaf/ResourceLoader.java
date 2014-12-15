package org.ocsoft.flatlaf;

import javax.swing.ImageIcon;

public class ResourceLoader {
    
    
    private ResourceLoader() {}
    
    /**
     * returns {ResourceLoader package} + "icons/" + filename as ImageIcon
     * @param filename
     * @return
     */
    public static ImageIcon loadIcon(String filename) {
        return new ImageIcon(ResourceLoader.class.getResource("icons/" + filename));
    }
    
}
