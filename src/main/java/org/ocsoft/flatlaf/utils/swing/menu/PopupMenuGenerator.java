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

import org.ocsoft.flatlaf.weblaf.menu.WebPopupMenu;

/**
 * Special generator that simplifies and shortens popup menu creation code.
 *
 * @author Mikle Garin
 * @see org.ocsoft.flatlaf.utils.swing.menu.AbstractMenuGenerator
 */

public class PopupMenuGenerator extends AbstractMenuGenerator<WebPopupMenu> {
    /**
     * Constructs new popup menu generator using default popup menu.
     */
    public PopupMenuGenerator() {
        super(new WebPopupMenu());
    }
    
    /**
     * Constructs new popup menu generator using default popup menu using the
     * specified style ID.
     *
     * @param styleId
     *            popup menu style ID
     */
    public PopupMenuGenerator(final String styleId) {
        super(new WebPopupMenu(styleId));
    }
    
    /**
     * Constructs new popup menu generator using the specified popup menu.
     *
     * @param menu
     *            popup menu
     */
    public PopupMenuGenerator(final WebPopupMenu menu) {
        super(menu);
    }
}