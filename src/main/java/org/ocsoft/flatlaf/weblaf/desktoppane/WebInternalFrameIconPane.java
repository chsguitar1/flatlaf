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

package org.ocsoft.flatlaf.weblaf.desktoppane;

import javax.swing.JInternalFrame;

/**
 * User: mgarin Date: 22.09.11 Time: 14:44
 */

public class WebInternalFrameIconPane extends WebInternalFrameTitlePane {
    public WebInternalFrameIconPane(JInternalFrame f) {
        super(f);
    }
    
    @Override
    protected boolean isFrameTitle() {
        return false;
    }
}
