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

package org.ocsoft.flatlaf.managers.settings;

import org.ocsoft.flatlaf.extended.colorchooser.WebGradientColorChooser;
import org.ocsoft.flatlaf.extended.date.WebDateField;
import org.ocsoft.flatlaf.extended.panel.WebAccordion;
import org.ocsoft.flatlaf.extended.panel.WebCollapsiblePane;
import org.ocsoft.flatlaf.extended.tab.WebDocumentPane;
import org.ocsoft.flatlaf.managers.settings.processors.WebAccordionSettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.processors.WebCollapsiblePaneSettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.processors.WebDateFieldSettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.processors.WebDocumentPaneSettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.processors.WebGradientColorChooserSettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.processors.WebTreeSettingsProcessor;
import org.ocsoft.flatlaf.weblaf.tree.WebTree;

/**
 * Minor additions over core SettingsManager.
 *
 * @author Mikle Garin
 */

public class WebSettingsManager {
    /**
     * Whether SettingsManager is initialized or not.
     */
    protected static boolean initialized = false;
    
    /**
     * Initializes SettingsManager.
     */
    public static synchronized void initialize() {
        if (!initialized) {
            initialized = true;
            
            // Ensure SettingsManager is initialized
            SettingsManager.initialize();
            
            // Register additional component settings processors
            ComponentSettingsManager.registerSettingsProcessor(
                    WebDocumentPane.class,
                    WebDocumentPaneSettingsProcessor.class);
            ComponentSettingsManager.registerSettingsProcessor(WebTree.class,
                    WebTreeSettingsProcessor.class);
            ComponentSettingsManager.registerSettingsProcessor(
                    WebDateField.class, WebDateFieldSettingsProcessor.class);
            ComponentSettingsManager.registerSettingsProcessor(
                    WebCollapsiblePane.class,
                    WebCollapsiblePaneSettingsProcessor.class);
            ComponentSettingsManager.registerSettingsProcessor(
                    WebAccordion.class, WebAccordionSettingsProcessor.class);
            ComponentSettingsManager.registerSettingsProcessor(
                    WebGradientColorChooser.class,
                    WebGradientColorChooserSettingsProcessor.class);
        }
    }
}