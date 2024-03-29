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

package org.ocsoft.flatlaf.weblaf.scroll;

import java.awt.Rectangle;

import javax.swing.JScrollBar;

import org.ocsoft.flatlaf.extended.painter.AdaptivePainter;
import org.ocsoft.flatlaf.extended.painter.Painter;

/**
 * Simple ScrollBarPainter adapter class. It is used to install simple
 * non-specific painters into WebScrollBarUI.
 *
 * @author Mikle Garin
 */

public class AdaptiveScrollBarPainter<E extends JScrollBar> extends
        AdaptivePainter<E> implements ScrollBarPainter<E> {
    /**
     * Constructs new AdaptiveScrollBarPainter for the specified painter.
     *
     * @param painter
     *            painter to adapt
     */
    public AdaptiveScrollBarPainter(final Painter painter) {
        super(painter);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaintButtons(final boolean paint) {
        // Ignore this method in adaptive class
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaintTrack(final boolean paint) {
        // Ignore this method in adaptive class
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setDragged(final boolean dragged) {
        // Ignore this method in adaptive class
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setTrackBounds(final Rectangle bounds) {
        // Ignore this method in adaptive class
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setThumbBounds(final Rectangle bounds) {
        // Ignore this method in adaptive class
    }
}