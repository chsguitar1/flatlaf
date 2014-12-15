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

package org.ocsoft.flatlaf.managers.settings.processors;

import javax.swing.*;

import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Custom SettingsProcessor for JComboBox component.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class JComboBoxSettingsProcessor extends SettingsProcessor<JComboBox, Integer>
{
    /**
     * Combobox value change listener.
     */
    private ActionListener actionListener;

    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data SettingsProcessorData
     */
    public JComboBoxSettingsProcessor ( final SettingsProcessorData data )
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
            defaultValue = -1;
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit ( final JComboBox comboBox )
    {
        actionListener = new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                save ();
            }
        };
        comboBox.addActionListener ( actionListener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy ( final JComboBox comboBox )
    {
        comboBox.removeActionListener ( actionListener );
        actionListener = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad ( final JComboBox comboBox )
    {
        final Integer index = loadValue ();
        if ( index != null && index >= 0 && comboBox.getModel ().getSize () > index && comboBox.getSelectedIndex () != index )
        {
            comboBox.setSelectedIndex ( index );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave ( final JComboBox comboBox )
    {
        saveValue ( comboBox.getSelectedIndex () );
    }
}