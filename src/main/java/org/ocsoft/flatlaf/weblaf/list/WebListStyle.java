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

package org.ocsoft.flatlaf.weblaf.list;

import java.awt.Color;

import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * WebList style class.
 *
 * @author Mikle Garin
 */

public final class WebListStyle {
    /**
     * Whether should decorate selected and rollover cells or not.
     */
    public static boolean decorateSelection = true;
    
    /**
     * Whether should highlight rollover cell or not.
     */
    public static boolean highlightRolloverCell = true;
    
    /**
     * Cells selection rounding.
     */
    public static int selectionRound = FlatLafStyleConstants.smallRound;
    
    /**
     * Cells selection shade width.
     */
    public static int selectionShadeWidth = FlatLafStyleConstants.shadeWidth;
    
    /**
     * Whether selection should be web-colored or not. In case it is not
     * web-colored selectionBackgroundColor value will be used as background
     * color.
     */
    public static boolean webColoredSelection = true;
    
    /**
     * Selection border color.
     */
    public static Color selectionBorderColor = Color.GRAY;
    
    /**
     * Selection background color. It is used only when webColoredSelection is
     * set to false.
     */
    public static Color selectionBackgroundColor = FlatLafStyleConstants.bottomBgColor;
    
    /**
     * Whether should scroll list down to selection automatically or not.
     */
    public static boolean autoScrollToSelection = true;
    
    /**
     * List selection background.
     */
    public static Color foreground = Color.BLACK;
    
    /**
     * List selection background.
     */
    public static Color background = Color.WHITE;
}