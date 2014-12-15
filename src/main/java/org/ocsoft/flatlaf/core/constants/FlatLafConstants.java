/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ocsoft.flatlaf.core.constants;

import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

import org.ocsoft.flatlaf.utils.file.FileComparator;



public final class FlatLafConstants {
    
    /**
     * If this client property is set to {@link Boolean#TRUE} on a component, UI
     * delegates should follow the typical Swing behavior of not overriding a
     * user-defined border on it.
     */
    public static final String PROPERTY_HONOR_USER_BORDER = "WebLookAndFeel.honorUserBorder";
    
    /**
     * If this system property is set to {@code true}, UI delegates should
     * follow the typical Swing behavior of not overriding a user-defined border
     * if one is installed on components.
     */
    public static final String PROPERTY_HONOR_USER_BORDERS = "WebLookAndFeel.honorUserBorders";
    
    /**
     * Some known UI constants.
     */
    public static final String LOOK_AND_FEEL_PROPERTY = "lookAndFeel";
    public static final String ORIENTATION_PROPERTY = "componentOrientation";
    public static final String MARGIN_PROPERTY = "margin";
    public static final String ENABLED_PROPERTY = "enabled";
    public static final String MODEL_PROPERTY = "model";
    public static final String TOOLBAR_FLOATABLE_PROPERTY = "floatable";
    public static final String WINDOW_DECORATION_STYLE_PROPERTY = "windowDecorationStyle";
    public static final String WINDOW_RESIZABLE_PROPERTY = "resizable";
    public static final String WINDOW_ICON_PROPERTY = "iconImage";
    public static final String WINDOW_TITLE_PROPERTY = "title";
    public static final String VISIBLE_PROPERTY = "visible";
    public static final String DOCUMENT_PROPERTY = "document";
    public static final String OPAQUE_PROPERTY = "opaque";
    public static final String PAINTER_PROPERTY = "painter";
    public static final String RENDERER_PROPERTY = "renderer";
    public static final String DROP_LOCATION = "dropLocation";
    
    
    /**
     * Viewable image formats.
     */
    public static final List<String> IMAGE_FORMATS = Arrays.asList("png",
            "apng", "gif", "agif", "jpg", "jpeg", "jpeg2000", "bmp");
    
    /**
     * Comparators.
     */
    public static final FileComparator FILE_COMPARATOR = new FileComparator();
    
    /**
     * Drawing constants.
     */
    public static final AffineTransform moveX = new AffineTransform();
    public static final AffineTransform moveY = new AffineTransform();
    public static final AffineTransform moveXY = new AffineTransform();
    
    static {
        moveX.translate(1, 0);
        moveY.translate(0, 1);
        moveXY.translate(1, 1);
    }
    
    /**
     * Debugging mode mark.
     */
    public static boolean DEBUG = false;
}