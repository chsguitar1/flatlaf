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

package org.ocsoft.flatlaf.laf.tooltip;

import java.awt.Color;
import java.awt.Insets;

import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * WebTooltip style class.
 *
 * @author Mikle Garin
 */

public final class WebTooltipStyle {
    /**
     * Tooltip text color.
     */
    public static Color textColor = FlatLafStyleConstants.tooltipTextColor;
    
    /**
     * Tooltip background color.
     */
    public static Color backgroundColor = Color.BLACK;
    
    /**
     * Tooltip background trasparency.
     */
    public static float trasparency = FlatLafStyleConstants.mediumTransparent;
    
    /**
     * Tooltip content margin.
     */
    public static Insets contentMargin = new Insets(3, 3, 3, 3);
    
    /**
     * Decoration rounding.
     */
    public static int round = FlatLafStyleConstants.smallRound;
}