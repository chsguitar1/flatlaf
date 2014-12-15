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

package org.ocsoft.flatlaf.managers.tooltip;

import java.awt.*;

import org.ocsoft.flatlaf.extended.painter.AbstractPainter;
import org.ocsoft.flatlaf.laf.FlatLafStyleConstants;
import org.ocsoft.flatlaf.managers.style.skin.web.WebLabelPainter;
import org.ocsoft.flatlaf.utils.graphics.GraphicsUtils;

/**
 * Custom painter for HotkeyTipLabel component.
 *
 * @author Mikle Garin
 * @see AbstractPainter
 * @see org.ocsoft.flatlaf.extended.painter.Painter
 */

public class HotkeyTipPainter<T extends HotkeyTipLabel> extends
        WebLabelPainter<T> {
    /**
     * Style constants.
     */
    public static Color bg = new Color(255, 255, 255, 178);
    public static int round = FlatLafStyleConstants.smallRound;
    
    /**
     * Constructs new hotkey tip painter.
     */
    public HotkeyTipPainter() {
        super();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(final Graphics2D g2d, final Rectangle bounds, final T c) {
        final Object aa = GraphicsUtils.setupAntialias(g2d);
        g2d.setColor(bg);
        g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height,
                round * 2, round * 2);
        GraphicsUtils.restoreAntialias(g2d, aa);
        
        super.paint(g2d, bounds, c);
    }
}