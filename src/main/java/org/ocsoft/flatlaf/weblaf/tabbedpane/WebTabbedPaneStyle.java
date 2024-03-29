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

package org.ocsoft.flatlaf.weblaf.tabbedpane;

import java.awt.Color;
import java.awt.Insets;

import org.ocsoft.flatlaf.extended.painter.Painter;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * WebTabbedPane style class.
 *
 * @author Mikle Garin
 */

public final class WebTabbedPaneStyle {
    /**
     * Tab content margin.
     */
    public static TabbedPaneStyle tabbedPaneStyle = TabbedPaneStyle.standalone;
    
    /**
     * Top tab color.
     */
    public static Color topBg = new Color(227, 227, 227);
    
    /**
     * Bottom tab color.
     */
    public static Color bottomBg = new Color(208, 208, 208);
    
    /**
     * Top selected tab color.
     */
    public static Color selectedTopBg = Color.WHITE;
    
    /**
     * Bottom selected tab color.
     */
    public static Color selectedBottomBg = FlatLafStyleConstants.backgroundColor;
    
    /**
     * Decoration rounding.
     */
    public static int round = FlatLafStyleConstants.smallRound;
    
    /**
     * Decoration shade width.
     */
    public static int shadeWidth = FlatLafStyleConstants.shadeWidth;
    
    /**
     * Whether to rotate tab insets for different tab positions or not.
     */
    public static boolean rotateTabInsets = false;
    
    /**
     * Tab content margin.
     */
    public static Insets contentInsets = new Insets(0, 0, 0, 0);
    
    /**
     * Tab title margin.
     */
    public static Insets tabInsets = new Insets(3, 4, 3, 4);
    
    /**
     * Empty pane Painter. Used when there are no available tabs.
     */
    public static Painter painter = null;
    
    /**
     * Left tab area spacing.
     */
    public static int tabRunIndent = 0;
    
    /**
     * Tab runs overlay in pixels.
     */
    public static int tabOverlay = 1;
    
    /**
     * Tabs stretch type.
     */
    public static TabStretchType tabStretchType = TabStretchType.multiline;
}