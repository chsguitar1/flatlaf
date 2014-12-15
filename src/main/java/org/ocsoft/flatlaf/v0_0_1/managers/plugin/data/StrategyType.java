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

package org.ocsoft.flatlaf.v0_0_1.managers.plugin.data;

import javax.swing.*;

import org.ocsoft.flatlaf.v0_0_1.utils.swing.EnumLazyIconProvider;

/**
 * Plugin initialization strategy type.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-PluginManager">How to use PluginManager</a>
 * @see org.ocsoft.flatlaf.v0_0_1.managers.plugin.PluginManager
 */

public enum StrategyType
{
    /**
     * Plugin doesn't define any specific strategy.
     */
    any,

    /**
     * Plugin must be initialized strictly before some other plugin.
     */
    before,

    /**
     * Plugin must be initialized strictly after some other plugin.
     */
    after;

    /**
     * Plugin strategy type icons folder.
     */
    private static final String iconsFolder = "icons/strategy/";

    /**
     * Returns plugin strategy type icon.
     *
     * @return plugin strategy type icon
     */
    public ImageIcon getIcon ()
    {
        return EnumLazyIconProvider.getIcon ( this, iconsFolder );
    }

    /**
     * Returns plugin strategy type description.
     *
     * @return plugin strategy type description
     */
    public String getDescription ()
    {
        switch ( this )
        {
            case any:
                return "Plugin doesn't define any specific strategy";
            case before:
                return "Plugin must be initialized strictly before some other plugin";
            case after:
                return "Plugin must be initialized strictly after some other plugin";
            default:
                return null;
        }
    }
}