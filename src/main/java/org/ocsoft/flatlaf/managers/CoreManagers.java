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

package org.ocsoft.flatlaf.managers;

import org.ocsoft.flatlaf.managers.language.LanguageManager;
import org.ocsoft.flatlaf.managers.proxy.ProxyManager;
import org.ocsoft.flatlaf.managers.settings.SettingsManager;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * Core managers simple initialization class.
 * This might be useful in case you are using WebLaF core separately.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-WebLaF">How to use WebLaF</a>
 * @see org.ocsoft.flatlaf.utils.system.FlatLafLogger
 * @see org.ocsoft.flatlaf.managers.language.LanguageManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.proxy.ProxyManager
 */

public class CoreManagers
{
    /**
     * Initializes core managers.
     * Managers initialization order does matter!
     */
    public static synchronized void initialize ()
    {
        FlatLafLogger.initialize ();
        LanguageManager.initialize ();
        SettingsManager.initialize ();
        ProxyManager.initialize ();
    }
}