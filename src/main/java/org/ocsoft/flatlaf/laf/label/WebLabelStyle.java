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

package org.ocsoft.flatlaf.laf.label;

import java.awt.*;

import org.ocsoft.flatlaf.extended.painter.Painter;
import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * WebLabel style class.
 *
 * @author Mikle Garin
 */

public final class WebLabelStyle {
    /**
     * Label margin.
     */
    public static Insets margin = FlatLafStyleConstants.emptyMargin;
    
    /**
     * Label background painter. If set it will override WebLaF styling.
     */
    public static Painter painter = null;
    
    /**
     * Label background color.
     */
    public static Color backgroundColor = FlatLafStyleConstants.backgroundColor;
    
    /**
     * Draw shade behind the text.
     */
    public static boolean drawShade = false;
    
    /**
     * Default label shade color.
     */
    public static Color shadeColor = new Color(230, 230, 230);
    
    /**
     * Default label transparency.
     */
    public static Float transparency = null;
    
    /**
     * Default label background painter.
     */
    public static Painter backgroundPainter = null;
}