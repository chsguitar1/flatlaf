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

package org.ocsoft.flatlaf.laf.viewport;

import javax.swing.JViewport;

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * This JViewport extension class provides a direct access to WebViewportUI methods.
 *
 * @author Mikle Garin
 */

public class WebViewport extends JViewport
{
    /**
     * Constructs new viewport component.
     */
    public WebViewport ()
    {
        super ();
    }

    /**
     * Returns Web-UI applied to this class.
     *
     * @return Web-UI applied to this class
     */
    public WebViewportUI getWebUI ()
    {
        return ( WebViewportUI ) getUI ();
    }

    /**
     * Installs a Web-UI into this component.
     */
    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebViewportUI ) )
        {
            try
            {
                setUI ( ( WebViewportUI ) ReflectUtils.createInstance ( FlatLafSettings.viewportUI ) );
            }
            catch ( final Throwable e )
            {
                FlatLafLogger.error ( this, e );
                setUI ( new WebViewportUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }
}