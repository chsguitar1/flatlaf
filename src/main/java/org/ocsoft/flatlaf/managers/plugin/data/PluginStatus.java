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

package org.ocsoft.flatlaf.managers.plugin.data;

import javax.swing.*;

import org.ocsoft.flatlaf.utils.swing.EnumLazyIconProvider;

/**
 * Available plugin statuses.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-PluginManager">How to use PluginManager</a>
 * @see org.ocsoft.flatlaf.managers.plugin.PluginManager
 */

public enum PluginStatus
{
    /**
     * Plugin was detected and waiting for futher actions.
     */
    detected,

    /**
     * Plugin is being loaded.
     */
    loading,

    /**
     * Plugin successfuly loaded.
     */
    loaded,

    /**
     * Plugin failed to load due to plugin class initialization exception.
     */
    failed;

    /**
     * Plugin status icons folder.
     */
    private static final String iconsFolder = "icons/status/";

    /**
     * Returns plugin status icon.
     *
     * @return plugin status icon
     */
    public ImageIcon getIcon ()
    {
        return EnumLazyIconProvider.getIcon ( this, iconsFolder );
    }

    /**
     * Returns plugin status text.
     *
     * @return plugin status text
     */
    public String getText ()
    {
        switch ( this )
        {
            case loading:
                return "Loading...";
            case loaded:
                return "Loaded succesfully";
            case failed:
                return "Failed to load";
            default:
                return null;
        }
    }
}