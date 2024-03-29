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

import java.util.List;

import org.ocsoft.flatlaf.extended.panel.AccordionListener;
import org.ocsoft.flatlaf.extended.panel.WebAccordion;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;
import org.ocsoft.flatlaf.utils.TextUtils;

/**
 * Custom SettingsProcessor for WebAccordion component.
 *
 * @author Mikle Garin
 */

// todo Change save scheme from indices to IDs
// todo Add IDs into accordion panes
public class WebAccordionSettingsProcessor extends
        SettingsProcessor<WebAccordion, String> {
    /**
     * Accordion selection listener.
     */
    private AccordionListener accordionListener;
    
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public WebAccordionSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final WebAccordion accordion) {
        accordionListener = new AccordionListener() {
            @Override
            public void selectionChanged() {
                save();
            }
        };
        accordion.addAccordionListener(accordionListener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final WebAccordion accordion) {
        accordion.removeAccordionListener(accordionListener);
        accordionListener = null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final WebAccordion accordion) {
        // Empty string identifies empty selection
        final String string = loadValue();
        final List<Integer> indices = string == null
                || string.trim().equals("") ? null : TextUtils.stringToIntList(
                string, ",");
        accordion.setSelectedIndices(indices);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final WebAccordion accordion) {
        // For empty selection empty string is used to avoid having null as
        // value as this will call for default value on load
        final String value = TextUtils.listToString(
                accordion.getSelectedIndices(), ",");
        saveValue(value != null ? value : "");
    }
}