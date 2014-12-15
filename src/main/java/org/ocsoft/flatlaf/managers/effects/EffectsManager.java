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

package org.ocsoft.flatlaf.managers.effects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.ocsoft.flatlaf.global.FlatLafStyleConstants;
import org.ocsoft.flatlaf.managers.glasspane.GlassPaneManager;
import org.ocsoft.flatlaf.managers.glasspane.WebGlassPane;
import org.ocsoft.flatlaf.utils.SwingUtils;
import org.ocsoft.flatlaf.utils.swing.WebTimer;

/**
 * This manager provides easy-to-use effects (like "fade in" and "fade out"), applyable to any Swing component without additional efforts.
 * Some of methods relies on other WebLaF managers and requires that they are properly initialized (through installing WebLaF or manually
 * initializing them, one by one).
 *
 * @author Mikle Garin
 */

public class EffectsManager
{
    /**
     * Makes any Swing component fade out using glass pane trick
     */

    public static void fadeOut ( final JComponent component )
    {
        final BufferedImage bi = SwingUtils.createComponentSnapshot ( component );
        final Rectangle b = SwingUtils.getBoundsInWindow ( component );
        final WebGlassPane wgp = GlassPaneManager.getGlassPane ( component );

        // Initial image palcement
        wgp.setPaintedImage ( bi, b.getLocation (), 100 );

        // Hiding component
        component.setVisible ( false );

        // Animating fade 
        WebTimer.repeat ( "EffectsManager.fadeOut", FlatLafStyleConstants.avgAnimationDelay, new ActionListener ()
        {
            private int opacity = 100;

            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                if ( opacity > 0 )
                {
                    opacity -= 5;
                    final Point l = b.getLocation ();
                    l.y += ( 100 - opacity ) / 2;
                    wgp.setPaintedImage ( bi, l, opacity );
                }
                else
                {
                    wgp.clearPaintedImage ();
                    ( ( WebTimer ) e.getSource () ).stop ();
                }
            }
        } );
    }

}