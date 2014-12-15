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

package org.ocsoft.flatlaf.v0_0_1.laf.label;

import javax.swing.*;

import org.ocsoft.flatlaf.v0_0_1.extended.painter.Painter;
import org.ocsoft.flatlaf.v0_0_1.extended.painter.SpecificPainter;

/**
 * Base interface for JLabel component painters.
 *
 * @author Mikle Garin
 */

public interface LabelPainter<E extends JLabel> extends Painter<E>, SpecificPainter
{
    /**
     * Sets whether text shade should be displayed or not.
     *
     * @param drawShade whether text shade should be displayed or not
     */
    public void setDrawShade ( final boolean drawShade );
}