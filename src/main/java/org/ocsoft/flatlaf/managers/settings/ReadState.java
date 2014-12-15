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

package org.ocsoft.flatlaf.managers.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This enumeration represents SettingsGroup read states.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsGroupState
 * @see org.ocsoft.flatlaf.managers.settings.SettingsGroup
 */

@XStreamAlias ("ReadState")
public enum ReadState
{
    /**
     * Not read yet.
     */
    none,

    /**
     * New one was created.
     */
    created,

    /**
     * Read sucessfully.
     */
    ok,

    /**
     * Successfully restored from dump.
     */
    restored,

    /**
     * Failed to load.
     */
    failed
}