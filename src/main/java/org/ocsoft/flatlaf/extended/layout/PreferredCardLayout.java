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

package org.ocsoft.flatlaf.extended.layout;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

/**
 * @author Mikle Garin
 */

public class PreferredCardLayout extends CardLayout {
    
    /**
     * Determines the preferred size of the container argument using this card
     * layout.
     *
     * @param parent
     *            the parent container in which to do the layout
     * @return the preferred dimensions to lay out the subcomponents of the
     *         specified container
     * @see java.awt.Container#getPreferredSize
     * @see java.awt.CardLayout#minimumLayoutSize
     */
    @Override
    public Dimension preferredLayoutSize(final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Insets insets = parent.getInsets();
            final int ncomponents = parent.getComponentCount();
            int w = 0;
            int h = 0;
            
            for (int i = 0; i < ncomponents; i++) {
                final Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    final Dimension d = comp.getPreferredSize();
                    if (d.width > w) {
                        w = d.width;
                    }
                    if (d.height > h) {
                        h = d.height;
                    }
                }
            }
            return new Dimension(
                    insets.left + insets.right + w + getHgap() * 2, insets.top
                            + insets.bottom + h + getVgap() * 2);
        }
    }
    
    /**
     * Calculates the minimum size for the specified panel.
     *
     * @param parent
     *            the parent container in which to do the layout
     * @return the minimum dimensions required to lay out the subcomponents of
     *         the specified container
     * @see java.awt.Container#doLayout
     * @see java.awt.CardLayout#preferredLayoutSize
     */
    @Override
    public Dimension minimumLayoutSize(final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Insets insets = parent.getInsets();
            final int ncomponents = parent.getComponentCount();
            int w = 0;
            int h = 0;
            
            for (int i = 0; i < ncomponents; i++) {
                final Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    final Dimension d = comp.getMinimumSize();
                    if (d.width > w) {
                        w = d.width;
                    }
                    if (d.height > h) {
                        h = d.height;
                    }
                }
            }
            return new Dimension(
                    insets.left + insets.right + w + getHgap() * 2, insets.top
                            + insets.bottom + h + getVgap() * 2);
        }
    }
}