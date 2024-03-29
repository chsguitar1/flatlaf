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

package org.ocsoft.flatlaf.extended.tab;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.ocsoft.flatlaf.weblaf.button.WebButton;
import org.ocsoft.flatlaf.weblaf.label.WebLabel;
import org.ocsoft.flatlaf.weblaf.panel.WebPanel;

/**
 * Default document tab title provider. It is used in all WebDocumentPanes by
 * default but can be easily replaced.
 *
 * @author Mikle Garin
 * @see org.ocsoft.flatlaf.extended.tab.TabTitleComponentProvider
 */

public class DefaultTabTitleComponentProvider<T extends DocumentData>
        implements TabTitleComponentProvider<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent createTabTitleComponent(final PaneData<T> paneData,
            final T document) {
        final WebPanel tabTitleComponent = new WebPanel(new BorderLayout(2, 0));
        tabTitleComponent.setOpaque(false);
        
        // Creating title label
        tabTitleComponent.add(createTitleLabel(paneData, document),
                BorderLayout.CENTER);
        
        // Creating close button
        if (paneData.getDocumentPane().isCloseable() && document.isCloseable()) {
            tabTitleComponent.add(createCloseButton(paneData, document),
                    BorderLayout.LINE_END);
        }
        
        return tabTitleComponent;
    }
    
    /**
     * Returns newly created tab title label.
     *
     * @param paneData
     *            PaneData containing document
     * @param document
     *            document to create tab title component for
     * @return newly created tab title label
     */
    
    protected JComponent createTitleLabel(final PaneData<T> paneData,
            final T document) {
        final WebLabel titleLabel = new WebLabel(document.getTitle(),
                document.getIcon());
        titleLabel.setForeground(document.getForeground());
        return titleLabel;
    }
    
    /**
     * Returns newly created tab close button.
     *
     * @param paneData
     *            PaneData containing document
     * @param document
     *            document to create tab title component for
     * @return newly created tab close button
     */
    protected JComponent createCloseButton(final PaneData<T> paneData,
            final T document) {
        final WebButton closeButton = new WebButton(
                WebDocumentPane.closeTabIcon,
                WebDocumentPane.closeTabRolloverIcon);
        closeButton.setUndecorated(true);
        closeButton.setFocusable(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                paneData.close(document);
            }
        });
        return closeButton;
    }
}