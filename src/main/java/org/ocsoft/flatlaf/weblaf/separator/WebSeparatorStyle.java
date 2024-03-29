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

package org.ocsoft.flatlaf.weblaf.separator;

import java.awt.Color;
import java.awt.Insets;

import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * WebSeparator style class.
 *
 * @author Mikle Garin
 */

public final class WebSeparatorStyle {
    /**
     * Light upper separator line color.
     */
    public static Color separatorLightUpperColor = FlatLafStyleConstants.separatorLightUpperColor;
    
    /**
     * Light middle separator line color.
     */
    public static Color separatorLightColor = FlatLafStyleConstants.separatorLightColor;
    
    /**
     * Upper separator line color.
     */
    public static Color separatorUpperColor = FlatLafStyleConstants.separatorUpperColor;
    
    /**
     * Middle separator line color.
     */
    public static Color separatorColor = FlatLafStyleConstants.separatorColor;
    
    /**
     * Whether should use reversed colors or not.
     */
    public static boolean reversedColors = false;
    
    /**
     * Whether should draw additional leading light line or not.
     */
    public static boolean drawLeadingLine = false;
    
    /**
     * Whether should draw additional trailing light line or not.
     */
    public static boolean drawTrailingLine = true;
    
    /**
     * Default separator margin.
     */
    public static Insets margin = FlatLafStyleConstants.emptyMargin;
}