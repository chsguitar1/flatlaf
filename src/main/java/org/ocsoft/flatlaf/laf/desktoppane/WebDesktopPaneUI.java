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

package org.ocsoft.flatlaf.laf.desktoppane;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopPaneUI;

import org.ocsoft.flatlaf.global.FlatLafStyleConstants;
import org.ocsoft.flatlaf.laf.FlatLookAndFeel;
import org.ocsoft.flatlaf.utils.LafUtils;
import org.ocsoft.flatlaf.utils.SwingUtils;

/**
 * User: mgarin Date: 17.08.11 Time: 23:14
 */

public class WebDesktopPaneUI extends BasicDesktopPaneUI
{
    @SuppressWarnings ( "UnusedParameters" )
    public static ComponentUI createUI ( final JComponent c )
    {
        return new WebDesktopPaneUI ();
    }

    @Override
    public void installUI ( final JComponent c )
    {
        super.installUI ( c );

        // Default settings
        SwingUtils.setOrientation ( c );
        LookAndFeel.installProperty ( c, FlatLookAndFeel.OPAQUE_PROPERTY, Boolean.TRUE );
        c.setBorder ( LafUtils.createWebBorder ( 0, 0, 0, 0 ) );
        c.setBackground ( FlatLafStyleConstants.backgroundColor );
    }
}