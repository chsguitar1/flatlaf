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

package org.ocsoft.flatlaf.extended.panel;

import java.awt.Dimension;

import org.ocsoft.flatlaf.weblaf.panel.WebPanel;

/**
 * User: mgarin Date: 14.10.11 Time: 19:46
 */

public class EmptyPanel extends WebPanel {
    public EmptyPanel(int width, int height) {
        this(new Dimension(width, height));
    }
    
    public EmptyPanel(Dimension size) {
        super();
        setOpaque(false);
        setPreferredSize(size);
    }
}
