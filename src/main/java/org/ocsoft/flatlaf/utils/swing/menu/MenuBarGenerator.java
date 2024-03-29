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

package org.ocsoft.flatlaf.utils.swing.menu;

import java.awt.event.ActionListener;

import org.ocsoft.flatlaf.managers.hotkey.HotkeyData;
import org.ocsoft.flatlaf.weblaf.menu.WebCheckBoxMenuItem;
import org.ocsoft.flatlaf.weblaf.menu.WebMenuBar;
import org.ocsoft.flatlaf.weblaf.menu.WebMenuItem;
import org.ocsoft.flatlaf.weblaf.menu.WebRadioButtonMenuItem;

/**
 * Special generator that simplifies and shortens menu bar creation code.
 *
 * @author Mikle Garin
 * @see org.ocsoft.flatlaf.utils.swing.menu.AbstractMenuGenerator
 */

public class MenuBarGenerator extends AbstractMenuGenerator<WebMenuBar> {
    /**
     * Constructs new menu bar generator using default menu bar.
     */
    public MenuBarGenerator() {
        super(new WebMenuBar());
    }
    
    /**
     * Constructs new menu bar generator using specified menu bar.
     *
     * @param menu
     *            menu bar
     */
    public MenuBarGenerator(final WebMenuBar menu) {
        super(menu);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addSeparator() {
        // todo Probably add some custom WebLaF separator later instead of error
        throw new RuntimeException(
                "Menu bar is not supposed to have any separators in it");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebMenuItem addItem(final Object icon, final String text,
            final HotkeyData hotkey, final boolean enabled,
            final ActionListener actionListener) {
        // Throw an exception here since this is not an intended Swing behavior
        throw new RuntimeException(
                "Menu bar is not supposed to have any menu items in it");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebCheckBoxMenuItem addCheckItem(final Object icon,
            final String text, final HotkeyData hotkey, final boolean enabled,
            final boolean selected, final ActionListener actionListener) {
        // Throw an exception here since this is not an intended Swing behavior
        throw new RuntimeException(
                "Menu bar is not supposed to have any checkbox menu items in it");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public WebRadioButtonMenuItem addRadioItem(final Object icon,
            final String text, final HotkeyData hotkey, final boolean enabled,
            final boolean selected, final ActionListener actionListener) {
        // Throw an exception here since this is not an intended Swing behavior
        throw new RuntimeException(
                "Menu bar is not supposed to have any radio button menu items in it");
    }
}