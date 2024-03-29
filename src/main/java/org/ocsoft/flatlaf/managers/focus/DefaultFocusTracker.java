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

package org.ocsoft.flatlaf.managers.focus;

import java.awt.Component;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ocsoft.flatlaf.utils.SwingUtils;

/**
 * Small extension class for FocusTracker that simplifies its creation.
 *
 * @author Mikle Garin
 */

public abstract class DefaultFocusTracker implements FocusTracker {
    /**
     * Whether tracking is currently enabled or not.
     */
    private boolean enabled;
    
    /**
     * Whether component and its childs in components tree should be counted as
     * a single component or not.
     */
    private boolean uniteWithChilds;
    
    /**
     * Custom childs which should be tracked together with this component.
     */
    private List<WeakReference<Component>> customChildren;
    
    /**
     * Constructs new tracker with the specified tracked component.
     */
    public DefaultFocusTracker() {
        this(true);
    }
    
    /**
     * Constructs new tracker with the specified tracked component.
     *
     * @param uniteWithChilds
     *            whether component and its childs in components tree should be
     *            counted as a single component or not
     */
    public DefaultFocusTracker(final boolean uniteWithChilds) {
        super();
        this.enabled = true;
        this.uniteWithChilds = uniteWithChilds;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrackingEnabled() {
        return enabled;
    }
    
    /**
     * Sets whether tracking is currently enabled or not.
     *
     * @param enabled
     *            whether tracking is currently enabled or not
     */
    public void setTrackingEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvolved(final Component component, final Component tracked) {
        if (isUniteWithChilds()) {
            if (SwingUtils.isEqualOrChild(tracked, component)) {
                return true;
            }
            if (customChildren != null) {
                final Iterator<WeakReference<Component>> iterator = customChildren
                        .iterator();
                while (iterator.hasNext()) {
                    final WeakReference<Component> next = iterator.next();
                    final Component customChild = next.get();
                    if (customChild == null) {
                        iterator.remove();
                    } else {
                        if (SwingUtils.isEqualOrChild(customChild, component)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            if (tracked == component) {
                return true;
            }
            if (customChildren != null) {
                final Iterator<WeakReference<Component>> iterator = customChildren
                        .iterator();
                while (iterator.hasNext()) {
                    final WeakReference<Component> next = iterator.next();
                    final Component customChild = next.get();
                    if (customChild == null) {
                        iterator.remove();
                    } else {
                        if (customChild == component) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Returns whether component and its childs in components tree should be
     * counted as a single component or not. In case component and its childs
     * are counted as one focus changes within them will be ignored by tracker.
     *
     * @return true if component and its childs in components tree should be
     *         counted as a single component, false otherwise
     */
    public boolean isUniteWithChilds() {
        return uniteWithChilds;
    }
    
    /**
     * Sets whether component and its childs in components tree should be
     * counted as a single component or not.
     *
     * @param uniteWithChilds
     *            whether component and its childs in components tree should be
     *            counted as a single component or not
     */
    public void setUniteWithChilds(final boolean uniteWithChilds) {
        this.uniteWithChilds = uniteWithChilds;
    }
    
    /**
     * Returns custom childs which should be tracked together with this
     * component. Note that `isUniteWithChilds` value will also affect how these
     * childs focus is checked.
     *
     * @return custom childs which should be tracked together with this
     *         component
     */
    public List<Component> getCustomChildren() {
        final List<Component> children = new ArrayList<Component>(
                customChildren.size());
        final Iterator<WeakReference<Component>> iterator = customChildren
                .iterator();
        while (iterator.hasNext()) {
            final WeakReference<Component> next = iterator.next();
            final Component component = next.get();
            if (component == null) {
                iterator.remove();
            } else {
                children.add(component);
            }
        }
        return children;
    }
    
    /**
     * Returns weakly-referenced custom children.
     *
     * @return weakly-referenced custom children
     */
    public List<WeakReference<Component>> getWeakCustomChildren() {
        return customChildren;
    }
    
    /**
     * Sets custom childs which should be tracked together with this component.
     *
     * @param customChildren
     *            custom childs which should be tracked together with this
     *            component
     */
    public void setCustomChildren(final List<Component> customChildren) {
        for (final Component customChild : customChildren) {
            addCustomChild(customChild);
        }
    }
    
    /**
     * Adds new custom child.
     *
     * @param customChild
     *            custom child to add
     */
    public void addCustomChild(final Component customChild) {
        if (customChildren == null) {
            customChildren = new ArrayList<WeakReference<Component>>(1);
        }
        customChildren.add(new WeakReference<Component>(customChild));
    }
    
    /**
     * Removes custom child.
     *
     * @param customChild
     *            custom child to remove
     */
    public void removeCustomChild(final Component customChild) {
        if (customChildren != null) {
            final Iterator<WeakReference<Component>> iterator = customChildren
                    .iterator();
            while (iterator.hasNext()) {
                final WeakReference<Component> next = iterator.next();
                final Component component = next.get();
                if (component == null || component == customChild) {
                    iterator.remove();
                }
            }
        }
    }
}