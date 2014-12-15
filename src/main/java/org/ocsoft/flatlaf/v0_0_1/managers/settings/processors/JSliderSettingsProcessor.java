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

package org.ocsoft.flatlaf.v0_0_1.managers.settings.processors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsManager;
import org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsProcessorData;

/**
 * Custom SettingsProcessor for JSlider component.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsProcessor
 */

public class JSliderSettingsProcessor extends SettingsProcessor<JSlider, Integer>
{
    /**
     * Slider value change listener.
     */
    private ChangeListener changeListener;

    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data SettingsProcessorData
     */
    public JSliderSettingsProcessor ( final SettingsProcessorData data )
    {
        super ( data );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getDefaultValue ()
    {
        Integer defaultValue = super.getDefaultValue ();
        if ( defaultValue == null )
        {
            defaultValue = getComponent ().getMinimum ();
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit ( final JSlider slider )
    {
        changeListener = new ChangeListener ()
        {
            @Override
            public void stateChanged ( final ChangeEvent e )
            {
                save ();
            }
        };
        slider.addChangeListener ( changeListener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy ( final JSlider slider )
    {
        slider.removeChangeListener ( changeListener );
        changeListener = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad ( final JSlider slider )
    {
        slider.setValue ( loadValue () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave ( final JSlider slider )
    {
        SettingsManager.set ( getGroup (), getKey (), slider.getValue () );
    }
}