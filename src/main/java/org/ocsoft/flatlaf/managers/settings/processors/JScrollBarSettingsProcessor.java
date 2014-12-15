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

import org.ocsoft.flatlaf.managers.settings.SettingsManager;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Custom SettingsProcessor for JScrollBar component.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How
 *      to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class JScrollBarSettingsProcessor extends
        SettingsProcessor<JScrollBar, Integer> {
    /**
     * Scroll bar value change listener.
     */
    private AdjustmentListener adjustmentListener;
    
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public JScrollBarSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getDefaultValue() {
        Integer defaultValue = super.getDefaultValue();
        if (defaultValue == null) {
            defaultValue = getComponent().getMinimum();
        }
        return defaultValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final JScrollBar scrollBar) {
        adjustmentListener = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(final AdjustmentEvent e) {
                save();
            }
        };
        scrollBar.addAdjustmentListener(adjustmentListener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final JScrollBar scrollBar) {
        scrollBar.removeAdjustmentListener(adjustmentListener);
        adjustmentListener = null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final JScrollBar scrollBar) {
        scrollBar.setValue(loadValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final JScrollBar scrollBar) {
        SettingsManager.set(getGroup(), getKey(), scrollBar.getValue());
    }
}