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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;

/**
 * Custom SettingsProcessor for JTextComponent component.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How
 *      to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class JTextComponentSettingsProcessor extends
        SettingsProcessor<JTextComponent, String> {
    /**
     * Component action listener.
     */
    private ActionListener actionListener;
    
    /**
     * Component focus loss listener.
     */
    private FocusAdapter focusAdapter;
    
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public JTextComponentSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultValue() {
        String defaultValue = super.getDefaultValue();
        if (defaultValue == null) {
            defaultValue = "";
        }
        return defaultValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final JTextComponent textComponent) {
        focusAdapter = new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                save();
            }
        };
        textComponent.addFocusListener(focusAdapter);
        
        if (textComponent instanceof JTextField) {
            final JTextField textField = (JTextField) textComponent;
            actionListener = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    save();
                }
            };
            textField.addActionListener(actionListener);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final JTextComponent textComponent) {
        textComponent.setText(loadValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final JTextComponent textComponent) {
        saveValue(textComponent.getText());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final JTextComponent textComponent) {
        textComponent.removeFocusListener(focusAdapter);
        focusAdapter = null;
        
        if (textComponent instanceof JTextField) {
            final JTextField textField = (JTextField) textComponent;
            textField.removeActionListener(actionListener);
            actionListener = null;
        }
    }
}