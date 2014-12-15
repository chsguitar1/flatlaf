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

package org.ocsoft.flatlaf.v0_0_1.laf.checkbox;

import javax.swing.*;

import org.ocsoft.flatlaf.v0_0_1.extended.checkbox.CheckState;
import org.ocsoft.flatlaf.v0_0_1.utils.ImageUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Check icon for simple checkbox.
 *
 * @author Mikle Garin
 */

public class SimpleCheckIcon extends CheckIcon
{
    /**
     * Check icons for all states.
     */
    public static List<ImageIcon> CHECK_STATES = new ArrayList<ImageIcon> ();
    public static List<ImageIcon> DISABLED_CHECK_STATES = new ArrayList<ImageIcon> ();

    /**
     * Check icons initialization.
     */
    static
    {
        for ( int i = 1; i <= 4; i++ )
        {
            final ImageIcon icon = new ImageIcon ( WebCheckBoxUI.class.getResource ( "icons/c" + i + ".png" ) );
            CHECK_STATES.add ( icon );
            DISABLED_CHECK_STATES.add ( ImageUtils.getDisabledCopy ( "WebCheckBox.disabled.check." + i, icon ) );
        }
    }

    /**
     * Current step.
     */
    protected int step = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doStep ()
    {
        step = nextState == CheckState.checked ? step + 1 : step - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetStep ()
    {
        step = state == CheckState.checked ? 3 : -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTransitionCompleted ()
    {
        return nextState == CheckState.unchecked && step == -1 || nextState == CheckState.checked && step == 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishTransition ()
    {
        this.state = nextState;
        this.nextState = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconWidth ()
    {
        return 16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconHeight ()
    {
        return 16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon ( final Component c, final Graphics2D g2d, final int x, final int y, final int w, final int h )
    {
        if ( step > -1 )
        {
            final ImageIcon icon = enabled ? CHECK_STATES.get ( step ) : DISABLED_CHECK_STATES.get ( step );
            g2d.drawImage ( icon.getImage (), x + w / 2 - getIconWidth () / 2, y + h / 2 - getIconHeight () / 2, null );
        }
    }
}