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

import org.ocsoft.flatlaf.extended.tab.DocumentData;
import org.ocsoft.flatlaf.extended.tab.DocumentListener;
import org.ocsoft.flatlaf.extended.tab.DocumentPaneListener;
import org.ocsoft.flatlaf.extended.tab.DocumentPaneState;
import org.ocsoft.flatlaf.extended.tab.PaneData;
import org.ocsoft.flatlaf.extended.tab.SplitData;
import org.ocsoft.flatlaf.extended.tab.StructureData;
import org.ocsoft.flatlaf.extended.tab.WebDocumentPane;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;

/**
 * Custom SettingsProcessor for WebDocumentPane component.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How
 *      to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class WebDocumentPaneSettingsProcessor extends
        SettingsProcessor<WebDocumentPane, DocumentPaneState> implements
        DocumentListener, DocumentPaneListener {
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public WebDocumentPaneSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final WebDocumentPane component) {
        component.addDocumentListener(this);
        component.addDocumentPaneListener(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final WebDocumentPane component) {
        component.removeDocumentPaneListener(this);
        component.removeDocumentListener(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void opened(final DocumentData document, final PaneData pane,
            final int index) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void selected(final DocumentData document, final PaneData pane,
            final int index) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean closing(final DocumentData document, final PaneData pane,
            final int index) {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void closed(final DocumentData document, final PaneData pane,
            final int index) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void splitted(final WebDocumentPane documentPane,
            final PaneData splittedPane, final SplitData newSplitData) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void merged(final WebDocumentPane documentPane,
            final SplitData mergedSplit, final StructureData newStructureData) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void orientationChanged(final WebDocumentPane documentPane,
            final SplitData splitData) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sidesSwapped(final WebDocumentPane documentPane,
            final SplitData splitData) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void dividerLocationChanged(final WebDocumentPane documentPane,
            final SplitData splitData) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final WebDocumentPane component) {
        final DocumentPaneState state = loadValue();
        if (state != null) {
            component.setDocumentPaneState(state);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final WebDocumentPane component) {
        saveValue(component.getDocumentPaneState());
    }
}