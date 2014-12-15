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

import org.ocsoft.flatlaf.laf.WebLookAndFeel;
import org.ocsoft.flatlaf.managers.log.Log;
import org.ocsoft.flatlaf.utils.ReflectUtils;

/**
 * User: mgarin Date: 24.08.11 Time: 17:46
 */

public class WebDesktopPane extends JDesktopPane
{
    public WebDesktopPane ()
    {
        super ();
    }

    public WebDesktopPaneUI getWebUI ()
    {
        return ( WebDesktopPaneUI ) getUI ();
    }

    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebDesktopPaneUI ) )
        {
            try
            {
                setUI ( ( WebDesktopPaneUI ) ReflectUtils.createInstance ( WebLookAndFeel.desktopPaneUI ) );
            }
            catch ( final Throwable e )
            {
                Log.error ( this, e );
                setUI ( new WebDesktopPaneUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }
}
