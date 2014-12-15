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

package org.ocsoft.flatlaf.laf;

import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

/**
 * Factory object that can vend Borders appropriate for the Web L&amp;F.
 *
 * @author Mikle Garin
 */

public class WebBorders
{
    /**
     * Returns border instance for a JButton.
     *
     * @return border instance for a JButton
     */
    public static Border getButtonBorder ()
    {
        return new BorderUIResource.EmptyBorderUIResource ( 0, 0, 0, 0 );
    }

    /**
     * Returns border instance for a JProgressBar.
     *
     * @return border instance for a JProgressBar
     */
    public static Border getProgressBarBorder ()
    {
        return new BorderUIResource.EmptyBorderUIResource ( 0, 0, 0, 0 );
    }
}