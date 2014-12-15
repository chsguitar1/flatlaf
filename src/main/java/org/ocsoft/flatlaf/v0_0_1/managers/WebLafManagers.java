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

package org.ocsoft.flatlaf.v0_0_1.managers;

import org.ocsoft.flatlaf.v0_0_1.managers.drag.DragManager;
import org.ocsoft.flatlaf.v0_0_1.managers.focus.FocusManager;
import org.ocsoft.flatlaf.v0_0_1.managers.hotkey.HotkeyManager;
import org.ocsoft.flatlaf.v0_0_1.managers.language.WebLanguageManager;
import org.ocsoft.flatlaf.v0_0_1.managers.log.Log;
import org.ocsoft.flatlaf.v0_0_1.managers.proxy.WebProxyManager;
import org.ocsoft.flatlaf.v0_0_1.managers.settings.WebSettingsManager;
import org.ocsoft.flatlaf.v0_0_1.managers.style.StyleManager;
import org.ocsoft.flatlaf.v0_0_1.managers.tooltip.TooltipManager;
import org.ocsoft.flatlaf.v0_0_1.managers.version.VersionManager;

/**
 * WebLaF managers simple initialization class.
 * Used by WebLookAndFeel to initialize managers together with or separately from the L&F.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-WebLaF">How to use WebLaF</a>
 * @see org.ocsoft.flatlaf.v0_0_1.managers.log.Log
 * @see org.ocsoft.flatlaf.v0_0_1.managers.version.VersionManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.language.WebLanguageManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.settings.WebSettingsManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.hotkey.HotkeyManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.focus.FocusManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.tooltip.TooltipManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.style.StyleManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.proxy.WebProxyManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.drag.DragManager
 */

public class WebLafManagers
{
    /**
     * Initializes WebLaF managers.
     * Managers initialization order does matter!
     */
    public static synchronized void initialize ()
    {
        Log.initialize ();
        VersionManager.initialize ();
        WebLanguageManager.initialize ();
        WebSettingsManager.initialize ();
        HotkeyManager.initialize ();
        FocusManager.initialize ();
        TooltipManager.initialize ();
        StyleManager.initialize ();
        WebProxyManager.initialize ();
        DragManager.initialize ();
    }
}