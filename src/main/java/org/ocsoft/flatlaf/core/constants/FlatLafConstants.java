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

import javax.swing.*;

import org.ocsoft.flatlaf.utils.file.FileComparator;
import org.ocsoft.flatlaf.utils.filefilter.*;

import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides various global constants.
 *
 * @author Mikle Garin
 */

public final class FlatLafConstants {
    
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