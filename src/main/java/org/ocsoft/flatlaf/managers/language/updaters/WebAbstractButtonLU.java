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

package org.ocsoft.flatlaf.managers.language.updaters;

import javax.swing.AbstractButton;
import javax.swing.KeyStroke;

import org.ocsoft.flatlaf.managers.hotkey.HotkeyData;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyInfo;
import org.ocsoft.flatlaf.managers.hotkey.HotkeyManager;
import org.ocsoft.flatlaf.managers.language.data.Value;
import org.ocsoft.flatlaf.utils.SwingUtils;

/**
 * This class provides language default updates for AbstractButton component.
 *
 * @author Mikle Garin
 */

public class WebAbstractButtonLU extends WebLanguageUpdater<AbstractButton> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final AbstractButton c, final String key,
            final Value value, final Object... data) {
        // Updating text and mnemonic
        final String text = getDefaultText(value, data);
        c.setText(text != null ? text : null);
        c.setMnemonic(text != null && value.getMnemonic() != null ? value
                .getMnemonic() : 0);
        
        // Removing old cached hotkey and adding new one if needed
        if (isHotkeyCached(c)) {
            HotkeyManager.unregisterHotkey(getCachedHotkey(c));
        }
        if (value.getHotkey() != null) {
            final KeyStroke keyStroke = KeyStroke.getKeyStroke(value
                    .getHotkey());
            if (keyStroke != null) {
                final HotkeyData hotkeyData = SwingUtils
                        .getHotkeyData(keyStroke);
                final HotkeyInfo hotkeyInfo = HotkeyManager.registerHotkey(c,
                        hotkeyData);
                cacheHotkey(c, hotkeyInfo);
            }
        }
    }
}