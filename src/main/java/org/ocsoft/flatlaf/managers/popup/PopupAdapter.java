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

package org.ocsoft.flatlaf.managers.popup;

import org.ocsoft.flatlaf.utils.swing.PopupListener;

/**
 * User: mgarin Date: 04.06.12 Time: 12:39
 */

public abstract class PopupAdapter implements PopupListener {
    @Override
    public void popupWillBeOpened() {
        // Do nothing
    }
    
    @Override
    public void popupOpened() {
        // Do nothing
    }
    
    @Override
    public void popupWillBeClosed() {
        // Do nothing
    }
    
    @Override
    public void popupClosed() {
        // Do nothing
    }
}