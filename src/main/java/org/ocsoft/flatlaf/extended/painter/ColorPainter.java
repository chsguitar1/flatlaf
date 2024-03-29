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

package org.ocsoft.flatlaf.extended.painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * Simple color painter. This painter simply fills component background with a
 * single color.
 *
 * @param <E>
 *            component type
 * @author Mikle Garin
 * @see AbstractPainter
 * @see Painter
 */

public class ColorPainter<E extends JComponent> extends AbstractPainter<E> {
    /**
     * Color to fill component with.
     */
    protected Color color;
    
    /**
     * Constructs color painter with specified fill color.
     *
     * @param color
     *            color to fill component with
     */
    public ColorPainter(final Color color) {
        super();
        this.color = color;
    }
    
    /**
     * Returns color to fill component with.
     *
     * @return color to fill component with
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets color to fill component with.
     *
     * @param color
     *            new color to fill component with
     */
    public void setColor(final Color color) {
        this.color = color;
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isOpaque(final E c) {
        return color != null && color.getAlpha() == 255;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(final Graphics2D g2d, final Rectangle bounds, final E c) {
        // Do not paint anything if color is not set
        if (color != null) {
            // Determining actual rect to be filled (we don't need to fill
            // invisible area)
            final Rectangle r = c.getVisibleRect().intersection(bounds);
            
            // If there is anything to fill we do it
            if (r.width > 0 && r.height > 0) {
                g2d.setColor(color);
                g2d.fillRect(r.x, r.y, r.width, r.height);
            }
        }
    }
}