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

import javax.swing.*;

import org.ocsoft.flatlaf.extended.layout.AlignLayout;
import org.ocsoft.flatlaf.laf.panel.WebPanel;

import java.awt.*;

/**
 * Custom panel that aligns contained component.
 *
 * @author Mikle Garin
 */

public class AlignPanel extends WebPanel implements SwingConstants
{
    /**
     * Constructs new align panel.
     *
     * @param component component to align
     * @param halign    horizontal alignment
     * @param valign    vertical alignment
     */
    public AlignPanel ( final Component component, final int halign, final int valign )
    {
        super ( new AlignLayout () );
        setOpaque ( false );
        add ( component, halign + AlignLayout.SEPARATOR + valign );
    }
}