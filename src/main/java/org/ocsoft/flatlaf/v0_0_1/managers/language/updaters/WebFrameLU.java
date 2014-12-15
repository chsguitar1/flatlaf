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

package org.ocsoft.flatlaf.v0_0_1.managers.language.updaters;

import javax.swing.*;

import org.ocsoft.flatlaf.v0_0_1.laf.rootpane.WebRootPaneUI;
import org.ocsoft.flatlaf.v0_0_1.managers.language.data.Value;
import org.ocsoft.flatlaf.v0_0_1.managers.language.updaters.DefaultLanguageUpdater;

import java.awt.*;

/**
 * This class provides language default updates for Frame component.
 *
 * @author Mikle Garin
 */

public class WebFrameLU extends DefaultLanguageUpdater<Frame>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void update ( final Frame c, final String key, final Value value, final Object... data )
    {
        if ( c instanceof JFrame )
        {
            final JRootPane rootPane = ( ( JFrame ) c ).getRootPane ();
            if ( rootPane.getUI () instanceof WebRootPaneUI )
            {
                final JComponent titleComponent = ( ( WebRootPaneUI ) rootPane.getUI () ).getTitleComponent ();
                if ( titleComponent != null )
                {
                    titleComponent.repaint ();

                    // final JLabel title = SwingUtils.getFirst ( titleComponent, JLabel.class );
                    // title.setText ( getDefaultText ( value, data ) );
                }
            }
        }
        c.setTitle ( getDefaultText ( value, data ) );
    }
}