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

package org.ocsoft.flatlaf.weblaf.filechooser;

/**
 * This enumeration represents default file chooser approve button types and
 * provides language keys for them.
 *
 * @author Mikle Garin
 */

public enum FileApproveText {
    /**
     * Save text.
     */
    save,
    
    /**
     * Open text.
     */
    open,
    
    /**
     * Choose text.
     */
    choose;
    
    /**
     * Returns approve text language key.
     *
     * @return approve text language key
     */
    public String getLanguageKey() {
        return "weblaf.filechooser." + this;
    }
}