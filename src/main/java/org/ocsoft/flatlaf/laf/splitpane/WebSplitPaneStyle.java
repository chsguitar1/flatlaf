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

package org.ocsoft.flatlaf.laf.splitpane;

import java.awt.Color;
import java.awt.Insets;

import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * WebSplitPane style class.
 *
 * @author Mikle Garin
 */

public final class WebSplitPaneStyle {
    /**
     * Default split pane margin.
     */
    public static Insets margin = FlatLafStyleConstants.emptyMargin;
    
    /**
     * Split pane divider color when dragged.
     */
    public static Color dragDividerColor = Color.LIGHT_GRAY;
    
    /**
     * Whether should draw divider border or not.
     */
    public static boolean drawDividerBorder = false;
    
    /**
     * Border color.
     */
    public static Color dividerBorderColor = FlatLafStyleConstants.darkBorderColor;
}