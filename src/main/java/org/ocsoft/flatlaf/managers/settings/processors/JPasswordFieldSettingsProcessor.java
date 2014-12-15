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
import org.ocsoft.flatlaf.utils.EncryptionUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Custom SettingsProcessor for JPasswordField component.
 *
 * @author Mikle Garin
 * @see <a href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class JPasswordFieldSettingsProcessor extends SettingsProcessor<JPasswordField, String>
{
    /**
     * Field action listener.
     */
    private ActionListener actionListener;

    /**
     * Field focus loss listener.
     */
    private FocusAdapter focusAdapter;

    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data SettingsProcessorData
     */
    public JPasswordFieldSettingsProcessor ( final SettingsProcessorData data )
    {
        super ( data );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultValue ()
    {
        String defaultValue = super.getDefaultValue ();
        if ( defaultValue == null )
        {
            defaultValue = "";
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit ( final JPasswordField passwordField )
    {
        actionListener = new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                save ();
            }
        };
        passwordField.addActionListener ( actionListener );

        focusAdapter = new FocusAdapter ()
        {
            @Override
            public void focusLost ( final FocusEvent e )
            {
                save ();
            }
        };
        passwordField.addFocusListener ( focusAdapter );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy ( final JPasswordField passwordField )
    {
        passwordField.removeActionListener ( actionListener );
        actionListener = null;

        passwordField.removeFocusListener ( focusAdapter );
        focusAdapter = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad ( final JPasswordField passwordField )
    {
        passwordField.setText ( EncryptionUtils.decrypt ( loadValue () ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave ( final JPasswordField passwordField )
    {
        saveValue ( EncryptionUtils.encrypt ( new String ( passwordField.getPassword () ) ) );
    }
}