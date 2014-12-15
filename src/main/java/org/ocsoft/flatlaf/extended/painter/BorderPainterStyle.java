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

package org.ocsoft.flatlaf.extended.painter;

import java.awt.Color;
import java.awt.Stroke;

import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * BorderPainter style class.
 *
 * @author Mikle Garin
 */

public final class BorderPainterStyle {
    /**
     * Border width.
     */
    public static int width = FlatLafStyleConstants.borderWidth;
    
    /**
     * Border round.
     */
    public static int round = FlatLafStyleConstants.bigRound;
    
    /**
     * Border color.
     */
    public static Color color = FlatLafStyleConstants.borderColor;
    
    /**
     * Border stroke.
     */
    public static Stroke stroke = null;
}