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

package org.ocsoft.flatlaf.weblaf.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.SwingConstants;

import org.ocsoft.flatlaf.extended.painter.Painter;
import org.ocsoft.flatlaf.weblaf.FlatLafStyleConstants;

/**
 * WebTextField style class.
 *
 * @author Mikle Garin
 */

public final class WebTextFieldStyle {
    /**
     * Whether should draw border or not.
     */
    public static boolean drawBorder = FlatLafStyleConstants.drawBorder;
    
    /**
     * Whether should draw focus or not.
     */
    public static boolean drawFocus = FlatLafStyleConstants.drawFocus;
    
    /**
     * Field corners rounding.
     */
    public static int round = FlatLafStyleConstants.smallRound;
    
    /**
     * Whether should draw shade or not.
     */
    public static boolean drawShade = FlatLafStyleConstants.drawShade;
    
    /**
     * Field shade width.
     */
    public static int shadeWidth = FlatLafStyleConstants.shadeWidth;
    
    /**
     * Whether should draw background or not.
     */
    public static boolean drawBackground = true;
    
    /**
     * Whether should draw web-styled background or not.
     */
    public static boolean webColored = false;
    
    /**
     * Text field painter. If set it will override WebLaF styling.
     */
    public static Painter painter = null;
    
    /**
     * Field content spacing.
     */
    public static int componentSpacing = FlatLafStyleConstants.contentSpacing;
    
    /**
     * Field margin.
     */
    public static Insets margin = new Insets(0, 0, 0, 0);
    
    /**
     * Inner field margin.
     */
    public static Insets fieldMargin = new Insets(2, 2, 2, 2);
    
    /**
     * Input prompt text. Set to null to disable input prompt.
     */
    public static String inputPrompt = null;
    
    /**
     * Input prompt text font. Set to null to use the same font field uses.
     */
    public static Font inputPromptFont = null;
    
    /**
     * Input prompt text foreground. Set to null to use the same foreground
     * field uses.
     */
    public static Color inputPromptForeground = new Color(160, 160, 160);
    
    /**
     * Input prompt text position.
     */
    public static int inputPromptPosition = SwingConstants.LEADING;
    
    /**
     * Whether should hide input prompt when field is focused or not.
     */
    public static boolean hideInputPromptOnFocus = true;
}