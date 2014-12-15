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

package org.ocsoft.flatlaf.laf.menu;

import java.awt.*;

import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;

/**
 * User: mgarin Date: 31.01.12 Time: 16:39
 */

public final class WebMenuBarStyle
{
    /**
     * Decorate panel with Web-styled background or not
     */
    public static boolean undecorated = false;

    /**
     * Decoration rounding
     */
    public static int round = FlatLafStyleConstants.bigRound;

    /**
     * Decoration shade width
     */
    public static int shadeWidth = 0;

    /**
     * Decoration shade width
     */
    public static MenuBarStyle menuBarStyle = MenuBarStyle.attached;

    /**
     * Decoration shade width
     */
    public static Color borderColor = FlatLafStyleConstants.borderColor;
}