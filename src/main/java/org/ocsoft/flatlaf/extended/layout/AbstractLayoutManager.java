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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

/**
 * This an abstract layout manager that hides some less frequently used layout
 * methods.
 *
 * @author Mikle Garin
 */

public abstract class AbstractLayoutManager implements LayoutManager2 {
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLayoutComponent(final Component comp,
            final Object constraints) {
        addComponent(comp, constraints);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLayoutComponent(final String name, final Component comp) {
        addComponent(comp, name);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLayoutComponent(final Component comp) {
        removeComponent(comp);
    }
    
    /**
     * Called when component added into container with this layout.
     *
     * @param component
     *            added component
     * @param constraints
     *            component constraints
     */
    public void addComponent(final Component component, final Object constraints) {
        // Do nothing
    }
    
    /**
     * Called when component removed from container with this layout.
     *
     * @param component
     *            removed component
     */
    public void removeComponent(final Component component) {
        // Do nothing
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension minimumLayoutSize(final Container parent) {
        return preferredLayoutSize(parent);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension maximumLayoutSize(final Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getLayoutAlignmentX(final Container target) {
        return 0.5f;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getLayoutAlignmentY(final Container target) {
        return 0.5f;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidateLayout(final Container target) {
        //
    }
}