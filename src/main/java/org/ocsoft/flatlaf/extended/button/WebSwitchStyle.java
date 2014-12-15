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

package org.ocsoft.flatlaf.extended.button;

import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * User: mgarin Date: 04.01.13 Time: 17:09
 */

public final class WebSwitchStyle {
    /**
     * Decoration rounding
     */
    public static int round = FlatLafStyleConstants.bigRound;
    
    /**
     * Decoration rounding
     */
    public static int gripperRound = Math.max(0,
            FlatLafStyleConstants.bigRound - 3);
    
    /**
     * Decoration shade width
     */
    public static int shadeWidth = FlatLafStyleConstants.shadeWidth;
    
    /**
     * Animate component
     */
    public static boolean animate = FlatLafStyleConstants.animate;
}