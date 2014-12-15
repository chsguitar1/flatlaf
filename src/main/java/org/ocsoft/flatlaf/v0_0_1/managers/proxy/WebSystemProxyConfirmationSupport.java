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

package org.ocsoft.flatlaf.v0_0_1.managers.proxy;

import org.ocsoft.flatlaf.v0_0_1.extended.optionpane.WebExtendedOptionPane;
import org.ocsoft.flatlaf.v0_0_1.laf.checkbox.WebCheckBox;
import org.ocsoft.flatlaf.v0_0_1.laf.optionpane.WebOptionPane;
import org.ocsoft.flatlaf.v0_0_1.managers.language.LanguageManager;
import org.ocsoft.flatlaf.v0_0_1.managers.proxy.SystemProxyConfirmationSupport;
import org.ocsoft.flatlaf.v0_0_1.utils.SwingUtils;

/**
 * Default WebLaF system proxy detection confirm dialog support.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-ProxyManager">How to use ProxyManager</a>
 * @see org.ocsoft.flatlaf.v0_0_1.managers.proxy.ProxyManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.proxy.SystemProxyConfirmationSupport
 */

public class WebSystemProxyConfirmationSupport implements SystemProxyConfirmationSupport
{
    /**
     * UI elements.
     */
    private WebCheckBox alwaysDoTheSame;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldUseSystemProxy ()
    {
        // Whether should save the choice or not
        alwaysDoTheSame = new WebCheckBox ();
        alwaysDoTheSame.setLanguage ( "weblaf.proxy.use.system.save" );
        alwaysDoTheSame.setSelected ( false );
        alwaysDoTheSame.setFocusable ( false );

        // Ask for settings replacement with system ones
        final String message = LanguageManager.get ( "weblaf.proxy.use.system.text" );
        final String title = LanguageManager.get ( "weblaf.proxy.use.system.title" );
        final int options = WebExtendedOptionPane.YES_NO_OPTION;
        final int type = WebExtendedOptionPane.QUESTION_MESSAGE;
        final WebExtendedOptionPane dialog =
                WebExtendedOptionPane.showConfirmDialog ( SwingUtils.getActiveWindow (), message, alwaysDoTheSame, title, options, type );

        return dialog.getResult () == WebOptionPane.YES_OPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean alwaysDoTheSame ()
    {
        final boolean selected = alwaysDoTheSame.isSelected ();
        alwaysDoTheSame = null;
        return selected;
    }
}