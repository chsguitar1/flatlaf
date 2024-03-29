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

import org.ocsoft.flatlaf.extended.panel.CollapsiblePaneAdapter;
import org.ocsoft.flatlaf.extended.panel.WebCollapsiblePane;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;

/**
 * Custom SettingsProcessor for WebCollapsiblePane component.
 *
 * @author Mikle Garin
 */

public class WebCollapsiblePaneSettingsProcessor extends
        SettingsProcessor<WebCollapsiblePane, Boolean> {
    /**
     * Expand and collapse events listener.
     */
    private CollapsiblePaneAdapter collapsiblePaneAdapter;
    
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public WebCollapsiblePaneSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getDefaultValue() {
        Boolean defaultValue = super.getDefaultValue();
        if (defaultValue == null) {
            defaultValue = true;
        }
        return defaultValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final WebCollapsiblePane collapsiblePane) {
        collapsiblePaneAdapter = new CollapsiblePaneAdapter() {
            @Override
            public void expanding(final WebCollapsiblePane pane) {
                save();
            }
            
            @Override
            public void collapsing(final WebCollapsiblePane pane) {
                save();
            }
        };
        collapsiblePane.addCollapsiblePaneListener(collapsiblePaneAdapter);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final WebCollapsiblePane collapsiblePane) {
        collapsiblePane.removeCollapsiblePaneListener(collapsiblePaneAdapter);
        collapsiblePaneAdapter = null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final WebCollapsiblePane collapsiblePane) {
        collapsiblePane.setExpanded(loadValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final WebCollapsiblePane collapsiblePane) {
        saveValue(collapsiblePane.isExpanded());
    }
}