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

import org.ocsoft.flatlaf.core.FlatLafSettings;
import org.ocsoft.flatlaf.managers.language.LanguageManager;
import org.ocsoft.flatlaf.managers.language.LanguageMethods;
import org.ocsoft.flatlaf.managers.language.updaters.LanguageUpdater;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

import java.beans.PropertyVetoException;

/**
 * User: mgarin Date: 24.08.11 Time: 18:11
 */

public class WebInternalFrame extends JInternalFrame implements LanguageMethods
{
    public WebInternalFrame ()
    {
        super ();
    }

    public WebInternalFrame ( final String title )
    {
        super ( title );
    }

    public WebInternalFrame ( final String title, final boolean resizable )
    {
        super ( title, resizable );
    }

    public WebInternalFrame ( final String title, final boolean resizable, final boolean closable )
    {
        super ( title, resizable, closable );
    }

    public WebInternalFrame ( final String title, final boolean resizable, final boolean closable, final boolean maximizable )
    {
        super ( title, resizable, closable, maximizable );
    }

    public WebInternalFrame ( final String title, final boolean resizable, final boolean closable, final boolean maximizable,
                              final boolean iconifiable )
    {
        super ( title, resizable, closable, maximizable, iconifiable );
    }

    public WebInternalFrameUI getWebUI ()
    {
        return ( WebInternalFrameUI ) getUI ();
    }

    @Override
    public void setIcon ( final boolean b )
    {
        try
        {
            super.setIcon ( b );
        }
        catch ( final PropertyVetoException e )
        {
            FlatLafLogger.error ( this, e );
        }
    }

    public void close ()
    {
        try
        {
            setClosed ( true );
        }
        catch ( final PropertyVetoException e )
        {
            FlatLafLogger.error ( this, e );
        }
    }

    public void open ()
    {
        try
        {
            setClosed ( false );
            setVisible ( true );
        }
        catch ( final PropertyVetoException e )
        {
            FlatLafLogger.error ( this, e );
        }
    }

    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebInternalFrameUI ) )
        {
            try
            {
                setUI ( ( WebInternalFrameUI ) ReflectUtils.createInstance ( FlatLafSettings.internalFrameUI, this ) );
            }
            catch ( final Throwable e )
            {
                FlatLafLogger.error ( this, e );
                setUI ( new WebInternalFrameUI ( this ) );
            }
        }
        else
        {
            setUI ( getUI () );
        }
        invalidate ();
    }

    /**
     * Language methods
     */

    @Override
    public void setLanguage ( final String key, final Object... data )
    {
        LanguageManager.registerComponent ( this, key, data );
    }

    @Override
    public void updateLanguage ( final Object... data )
    {
        LanguageManager.updateComponent ( this, data );
    }

    @Override
    public void updateLanguage ( final String key, final Object... data )
    {
        LanguageManager.updateComponent ( this, key, data );
    }

    @Override
    public void removeLanguage ()
    {
        LanguageManager.unregisterComponent ( this );
    }

    @Override
    public boolean isLanguageSet ()
    {
        return LanguageManager.isRegisteredComponent ( this );
    }

    @Override
    public void setLanguageUpdater ( final LanguageUpdater updater )
    {
        LanguageManager.registerLanguageUpdater ( this, updater );
    }

    @Override
    public void removeLanguageUpdater ()
    {
        LanguageManager.unregisterLanguageUpdater ( this );
    }
}