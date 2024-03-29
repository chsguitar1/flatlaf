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

package org.ocsoft.flatlaf.extended.tree;

import java.util.Comparator;

import org.ocsoft.flatlaf.utils.general.Filter;
import org.ocsoft.flatlaf.weblaf.tree.AsyncUniqueNode;

/**
 * Abstract data provider with implemented comparator and filter getters and
 * setters.
 *
 * @author Mikle Garin
 */

public abstract class AbstractAsyncTreeDataProvider<E extends AsyncUniqueNode>
        implements AsyncTreeDataProvider<E> {
    /**
     * Childs comparator.
     */
    protected Comparator<E> comparator = null;
    
    /**
     * Childs filter.
     */
    protected Filter<E> filter = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<E> getChildsComparator(final E node) {
        return comparator;
    }
    
    /**
     * Sets childs comparator for all nodes.
     *
     * @param comparator
     *            childs comparator for all nodes
     */
    public void setChildsComparator(final Comparator<E> comparator) {
        this.comparator = comparator;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Filter<E> getChildsFilter(final E node) {
        return filter;
    }
    
    /**
     * Sets childs filter for all nodes.
     *
     * @param filter
     *            childs filter for all nodes
     */
    public void setChildsFilter(final Filter<E> filter) {
        this.filter = filter;
    }
    
    /**
     * Returns false by default to allow childs load requests. It is recommended
     * to override this behavior if you can easily determine whether node is
     * leaf or not.
     *
     * @param node
     *            node
     * @return false
     */
    @Override
    public boolean isLeaf(final E node) {
        return false;
    }
}