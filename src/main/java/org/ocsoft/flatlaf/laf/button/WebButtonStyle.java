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

package org.ocsoft.flatlaf.laf.button;

import java.awt.*;

import org.ocsoft.flatlaf.extended.painter.Painter;
import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * WebButton style class.
 *
 * @author Mikle Garin
 */

public final class WebButtonStyle {
    /**
     * Top background color.
     */
    public static Color topBgColor = FlatLafStyleConstants.topBgColor;
    
    /**
     * Bottom background color.
     */
    public static Color bottomBgColor = FlatLafStyleConstants.bottomBgColor;
    
    /**
     * Top selected background color.
     */
    public static Color topSelectedBgColor = FlatLafStyleConstants.selectedBgColor;
    
    /**
     * Bottom selected background color.
     */
    public static Color bottomSelectedBgColor = new Color(210, 210, 210);
    
    /**
     * Selected button foreground.
     */
    public static Color selectedForeground = FlatLafStyleConstants.selectedTextColor;
    
    /**
     * Whether should draw rollover shine or not.
     */
    public static boolean rolloverShine = false;
    
    /**
     * Rollover shine color.
     */
    public static Color shineColor = FlatLafStyleConstants.shineColor;
    
    /**
     * Whether should draw dark border on rollover only or not.
     */
    public static boolean rolloverDarkBorderOnly = FlatLafStyleConstants.rolloverDarkBorderOnly;
    
    /**
     * Whether should draw shade or not.
     */
    public static boolean drawShade = FlatLafStyleConstants.drawShade;
    
    /**
     * Whether should draw shade on rollover only or not.
     */
    public static boolean rolloverShadeOnly = FlatLafStyleConstants.rolloverShadeOnly;
    
    /**
     * Whether should draw shade when button is disabled or not.
     */
    public static boolean showDisabledShade = FlatLafStyleConstants.showDisabledShade;
    
    /**
     * Button corners rounding.
     */
    public static int round = FlatLafStyleConstants.mediumRound;
    
    /**
     * Icon button corners rounding.
     */
    public static int iconRound = FlatLafStyleConstants.smallRound;
    
    /**
     * Button shade width.
     */
    public static int shadeWidth = FlatLafStyleConstants.shadeWidth;
    
    /**
     * Button margin.
     */
    public static Insets margin = FlatLafStyleConstants.emptyMargin;
    
    /**
     * Shade color.
     */
    public static Color shadeColor = FlatLafStyleConstants.shadeColor;
    
    /**
     * Inner shade width.
     */
    public static int innerShadeWidth = FlatLafStyleConstants.innerShadeWidth;
    
    /**
     * Inner shade color.
     */
    public static Color innerShadeColor = FlatLafStyleConstants.innerShadeColor;
    
    /**
     * Default button inner shade color.
     */
    public static Color defaultButtonShadeColor = new Color(180, 180, 180);
    
    /**
     * Left and right button content spacing.
     */
    public static int leftRightSpacing = FlatLafStyleConstants.leftRightSpacing;
    
    /**
     * Left and right icon button content spacing.
     */
    public static int iconLeftRightSpacing = 0;
    
    /**
     * Whether should make unselected toggle button icon semi-transparent or
     * not.
     */
    public static boolean shadeToggleIcon = FlatLafStyleConstants.shadeToggleIcon;
    
    /**
     * Unselected toggle button icon transparency.
     */
    public static float shadeToggleIconTransparency = FlatLafStyleConstants.shadeToggleIconTransparency;
    
    /**
     * Whether should draw button focus or not.
     */
    public static boolean drawFocus = FlatLafStyleConstants.drawFocus;
    
    /**
     * Whether should draw button decoration on rollover only or not.
     */
    public static boolean rolloverDecoratedOnly = FlatLafStyleConstants.rolloverDecoratedOnly;
    
    /**
     * Whether should animate button transitions.
     */
    public static boolean animate = FlatLafStyleConstants.animate;
    
    /**
     * Whether should undecorate button or not.
     */
    public static boolean undecorated = FlatLafStyleConstants.undecorated;
    
    /**
     * Button painter. If set it will override WebLaF styling.
     */
    public static Painter painter = null;
    
    /**
     * Whether should move button icon a bit when button is pressed or not.
     */
    public static boolean moveIconOnPress = true;
}