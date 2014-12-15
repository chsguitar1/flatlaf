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

package org.ocsoft.flatlaf.laf.label;

import javax.swing.*;

import org.ocsoft.flatlaf.extended.painter.AdaptivePainter;
import org.ocsoft.flatlaf.extended.painter.Painter;

/**
 * Simple LabelPainter adapter class. It is used to install simple non-specific
 * painters into WebLabelUI.
 *
 * @author Mikle Garin
 */

public class AdaptiveLabelPainter<E extends JLabel> extends AdaptivePainter<E>
        implements LabelPainter<E> {
    /**
     * Constructs new AdaptiveLabelPainter for the specified painter.
     *
     * @param painter
     *            painter to adapt
     */
    public AdaptiveLabelPainter(final Painter painter) {
        super(painter);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setDrawShade(final boolean drawShade) {
        // Ignore this method in adaptive class
    }
}