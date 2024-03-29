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

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.ocsoft.flatlaf.managers.settings.SettingsProcessor;
import org.ocsoft.flatlaf.managers.settings.SettingsProcessorData;
import org.ocsoft.flatlaf.weblaf.tree.TreeState;
import org.ocsoft.flatlaf.weblaf.tree.UniqueNode;
import org.ocsoft.flatlaf.weblaf.tree.WebTree;

/**
 * Custom SettingsProcessor for WebTree component.
 *
 * @author Mikle Garin
 * @see <a
 *      href="https://github.com/mgarin/weblaf/wiki/How-to-use-SettingsManager">How
 *      to use SettingsManager</a>
 * @see org.ocsoft.flatlaf.managers.settings.SettingsManager
 * @see org.ocsoft.flatlaf.managers.settings.SettingsProcessor
 */

public class WebTreeSettingsProcessor extends
        SettingsProcessor<WebTree<? extends UniqueNode>, TreeState> implements
        TreeSelectionListener, TreeExpansionListener {
    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data
     *            SettingsProcessorData
     */
    public WebTreeSettingsProcessor(final SettingsProcessorData data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit(final WebTree<? extends UniqueNode> component) {
        component.addTreeSelectionListener(this);
        component.addTreeExpansionListener(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy(final WebTree<? extends UniqueNode> component) {
        component.removeTreeExpansionListener(this);
        component.removeTreeSelectionListener(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void treeExpanded(final TreeExpansionEvent event) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void treeCollapsed(final TreeExpansionEvent event) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void valueChanged(final TreeSelectionEvent e) {
        save();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad(final WebTree<? extends UniqueNode> component) {
        if (component.getRootNode() instanceof UniqueNode) {
            final TreeState value = loadValue();
            if (value != null) {
                component.setTreeState(value);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave(final WebTree<? extends UniqueNode> component) {
        if (component.getRootNode() instanceof UniqueNode) {
            saveValue(component.getTreeState());
        }
    }
}