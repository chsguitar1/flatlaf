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

package org.ocsoft.flatlaf.extended.breadcrumb;

import java.awt.*;

import org.ocsoft.flatlaf.global.FlatLafStyleConstants;
import org.ocsoft.flatlaf.utils.laf.ShadeType;

/**
 * User: mgarin Date: 01.03.12 Time: 19:11
 */

public final class WebBreadcrumbStyle
{
    /**
     * Breadcrumb element overlap width
     */
    public static int elementOverlap = 8;

    /**
     * Decoration shade type
     */
    public static ShadeType shadeType = ShadeType.gradient;

    /**
     * Breadcrumb element overlap width
     */
    public static int shadeWidth = 4;

    /**
     * Breadcrumb element border color
     */
    public static Color borderColor = FlatLafStyleConstants.darkBorderColor;

    /**
     * Breadcrumb element border color
     */
    public static Color disabledBorderColor = FlatLafStyleConstants.disabledBorderColor;

    /**
     * Breadcrumb top color
     */
    public static Color bgTop = FlatLafStyleConstants.topBgColor;

    /**
     * Breadcrumb bottom color
     */
    public static Color bgBottom = FlatLafStyleConstants.bottomBgColor;

    /**
     * Breadcrumb bottom selected color
     */
    public static Color selectedBgColor = FlatLafStyleConstants.selectedBgColor;

    /**
     * Default breadcrumb element margin
     */
    public static Insets elementMargin = new Insets ( 2, 5, 2, 5 );

    /**
     * Enclose last breadcrumb element by default
     */
    public static boolean encloseLastElement = false;
}