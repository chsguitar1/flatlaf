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

package org.ocsoft.flatlaf.weblaf.checkbox;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Stroke;

import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * WebCheckBox style class.
 *
 * @author Mikle Garin
 */

public final class WebCheckBoxStyle {
    /**
     * Border color.
     */
    public static Color borderColor = FlatLafStyleConstants.borderColor;
    
    /**
     * Dark border color.
     */
    public static Color darkBorderColor = FlatLafStyleConstants.darkBorderColor;
    
    /**
     * Disabled border color.
     */
    public static Color disabledBorderColor = FlatLafStyleConstants.disabledBorderColor;
    
    /**
     * Top background gradient color.
     */
    public static Color topBgColor = FlatLafStyleConstants.topBgColor;
    
    /**
     * Bottom background gradient color.
     */
    public static Color bottomBgColor = FlatLafStyleConstants.bottomBgColor;
    
    /**
     * Top background gradient color on selection.
     */
    public static Color topSelectedBgColor = FlatLafStyleConstants.topSelectedBgColor;
    
    /**
     * Bottom background gradient color on selection.
     */
    public static Color bottomSelectedBgColor = FlatLafStyleConstants.bottomSelectedBgColor;
    
    /**
     * Border rounding.
     */
    public static int round = FlatLafStyleConstants.smallRound;
    
    /**
     * Decoration shade width.
     */
    public static int shadeWidth = FlatLafStyleConstants.shadeWidth;
    
    /**
     * Default checkbox margin.
     */
    public static Insets margin = FlatLafStyleConstants.emptyMargin;
    
    /**
     * Whether should animate selection changes or not.
     */
    public static boolean animated = FlatLafStyleConstants.animate;
    
    /**
     * Whether should display dark border only on rollover or not.
     */
    public static boolean rolloverDarkBorderOnly = FlatLafStyleConstants.rolloverDarkBorderOnly;
    
    /**
     * Icon background border stroke.
     */
    public static Stroke borderStroke = new BasicStroke(1.5f);
    
    /**
     * Icon width.
     */
    public static int iconWidth = 16;
    
    /**
     * Icon height.
     */
    public static int iconHeight = 16;
}