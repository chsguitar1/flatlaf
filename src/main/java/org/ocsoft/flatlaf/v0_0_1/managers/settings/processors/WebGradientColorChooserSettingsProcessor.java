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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ocsoft.flatlaf.v0_0_1.extended.colorchooser.GradientData;
import org.ocsoft.flatlaf.v0_0_1.extended.colorchooser.WebGradientColorChooser;
import org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.v0_0_1.managers.settings.SettingsProcessorData;

/**
 * Custom SettingsProcessor for WebGradientColorChooser component.
 *
 * @author Mikle Garin
 */

public class WebGradientColorChooserSettingsProcessor extends SettingsProcessor<WebGradientColorChooser, GradientData>
{
    /**
     * Gradient change listener.
     */
    private ChangeListener changeListener;

    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data SettingsProcessorData
     */
    public WebGradientColorChooserSettingsProcessor ( final SettingsProcessorData data )
    {
        super ( data );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit ( final WebGradientColorChooser gradientColorChooser )
    {
        changeListener = new ChangeListener ()
        {
            @Override
            public void stateChanged ( final ChangeEvent e )
            {
                save ();
            }
        };
        gradientColorChooser.addChangeListener ( changeListener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy ( final WebGradientColorChooser gradientColorChooser )
    {
        gradientColorChooser.removeChangeListener ( changeListener );
        changeListener = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad ( final WebGradientColorChooser gradientColorChooser )
    {
        gradientColorChooser.setGradientData ( loadValue () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave ( final WebGradientColorChooser gradientColorChooser )
    {
        saveValue ( gradientColorChooser.getGradientData () );
    }
}